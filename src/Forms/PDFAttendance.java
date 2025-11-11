package Forms;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * PDFAttendance — exports a JTable to a PDF using PDFBox.
 *
 * Supports:
 * - Optional header/footer images
 * - Title (multi-line): first line bold/larger; subsequent line(s) smaller and centered
 * - Pagination, measured column widths, wrapping/truncation
 * - Footer text: Printed: YYYY-MM-DD HH:mm (left) and Page X / Y (right)
 *
 * Note: header/footer images are measured/scaled using the page's unrotated
 * (portrait) printable width. When exporting to landscape the images are NOT
 * stretched to the wider width; instead they are drawn at the base width and
 * centered within the printable area.
 */
public final class PDFAttendance {
    private PDFAttendance() {}

    // Configuration
    private static final float BASE_FONT_SIZE = 10f;
    private static final float MIN_FONT_SIZE = 6f;
    private static final float ROW_PADDING = 2f;
    private static final float HEADER_SPACING = 0f; // keep header contiguous with rows
    private static final float TITLE_SPACING = 6f;  // spacing between title block and header row
    private static final float FOOTER_TEXT_HEIGHT = 12f; // text footer height reserve
    private static final float MIN_COLUMN_WIDTH = 30f;   // don't allow columns narrower than this (points)

    // Outline thickness (stroke width) - tweak these
    private static final float DEFAULT_STROKE_WIDTH = 0.5f; // normal row cell border
    private static final float HEADER_STROKE_WIDTH = 0.5f;  // header row border (typically thicker)

    public static void export(JTable table,
                              File outputFile,
                              String pageSize,
                              boolean landscape,
                              float marginLeft,
                              float marginTop,
                              float marginRight,
                              float marginBottom,
                              BufferedImage headerImage,
                              BufferedImage footerImage,
                              String title) throws IOException {

        if (table == null || outputFile == null) return;

        try (PDDocument doc = new PDDocument()) {

            PDRectangle basePage = getPageSize(pageSize);
            PDRectangle orientedPage = landscape ? new PDRectangle(basePage.getHeight(), basePage.getWidth()) : basePage;

            PDFont font = PDType1Font.HELVETICA;

            float baseAvailableWidth = basePage.getWidth() - marginLeft - marginRight;
            float usableWidth = orientedPage.getWidth() - marginLeft - marginRight;

            float fontSize = computeAdjustedFontSize(font, table, usableWidth, BASE_FONT_SIZE, MIN_FONT_SIZE);

            PDImageXObject headerPD = null;
            PDImageXObject footerPD = null;
            float headerImageHeightPts = 0f;
            float footerImageHeightPts = 0f;

            if (headerImage != null) {
                headerPD = LosslessFactory.createFromImage(doc, headerImage);
                float w = baseAvailableWidth;
                float h = (headerImage.getHeight() / (float) headerImage.getWidth()) * w;
                headerImageHeightPts = h;
            }
            if (footerImage != null) {
                footerPD = LosslessFactory.createFromImage(doc, footerImage);
                float w = baseAvailableWidth;
                float h = (footerImage.getHeight() / (float) footerImage.getWidth()) * w;
                footerImageHeightPts = h;
            }

            drawTablePaged(doc, table, font, fontSize, orientedPage, marginLeft, marginTop, marginRight, marginBottom,
                           headerPD, footerPD, headerImageHeightPts, footerImageHeightPts, title, baseAvailableWidth);

            addFooterTextToAllPages(doc, font, fontSize, marginLeft, marginBottom, marginRight);

            doc.save(outputFile);
        }
    }

    // Backwards-compatible overload (no images, no title)
    public static void export(JTable table,
                              File outputFile,
                              String pageSize,
                              boolean landscape,
                              float marginLeft,
                              float marginTop,
                              float marginRight,
                              float marginBottom) throws IOException {
        export(table, outputFile, pageSize, landscape, marginLeft, marginTop, marginRight, marginBottom, null, null, null);
    }

    private static PDRectangle getPageSize(String size) {
        switch (size) {
            case "Letter": return PDRectangle.LETTER;
            case "Legal":  return PDRectangle.LEGAL;
            default:       return PDRectangle.A4;
        }
    }

    private static float computeAdjustedFontSize(PDFont font, JTable table, float availableWidth, float baseSize, float minSize) {
        try {
            int cols = table.getColumnCount();
            if (cols <= 0) return baseSize;

            float[] maxWidthAtBase = new float[cols];
            for (int c = 0; c < cols; c++) {
                String header = safeString(table.getColumnName(c));
                float w = font.getStringWidth(header) / 1000f * baseSize;
                maxWidthAtBase[c] = w;
                for (int r = 0; r < table.getRowCount(); r++) {
                    String cell = safeString(table.getValueAt(r, c));
                    float cw = font.getStringWidth(cell) / 1000f * baseSize;
                    if (cw > maxWidthAtBase[c]) maxWidthAtBase[c] = cw;
                }
                maxWidthAtBase[c] += 2f * ROW_PADDING;
            }

            float total = 0f;
            for (float v : maxWidthAtBase) total += v;
            if (total <= availableWidth) return baseSize;

            float scale = availableWidth / total;
            float newSize = Math.max(minSize, (float)Math.floor(baseSize * scale));
            return newSize;
        } catch (IOException e) {
            e.printStackTrace();
            return baseSize;
        }
    }

    private static void drawTablePaged(PDDocument doc,
                                      JTable table,
                                      PDFont font,
                                      float fontSize,
                                      PDRectangle pageSize,
                                      float marginLeft,
                                      float marginTop,
                                      float marginRight,
                                      float marginBottom,
                                      PDImageXObject headerPD,
                                      PDImageXObject footerPD,
                                      float headerImageHeightPts,
                                      float footerImageHeightPts,
                                      String title,
                                      float baseImageDrawWidth) throws IOException {

        final float usableWidth = pageSize.getWidth() - marginLeft - marginRight;
        final float usableHeight = pageSize.getHeight() - marginTop - marginBottom - footerImageHeightPts - FOOTER_TEXT_HEIGHT;

        int cols = table.getColumnCount();
        if (cols <= 0) return;

        float[] colWidths = measureAndDistributeColumnWidths(font, fontSize, table, cols, usableWidth);

        List<List<String>> headerLines = new ArrayList<>(cols);
        for (int c = 0; c < cols; c++) {
            headerLines.add(wrapText(collapseWhitespace(safeString(table.getColumnName(c))), Math.max(1, (int)(colWidths[c] - 2f*ROW_PADDING)), font, fontSize));
        }

        PDFont boldFont = PDType1Font.HELVETICA_BOLD;

        PDPage page = new PDPage(pageSize);
        doc.addPage(page);
        PDPageContentStream cs = new PDPageContentStream(doc, page);
        float y = pageSize.getHeight() - marginTop;

        if (headerPD != null && headerImageHeightPts > 0f) {
            float drawW = baseImageDrawWidth;
            float xOffset = marginLeft + Math.max(0, (usableWidth - drawW) / 2f);
            float drawH = headerImageHeightPts;
            cs.drawImage(headerPD, xOffset, y - drawH, drawW, drawH);
            y -= drawH;
        }

        float titleBlockHeight = 0f;
        if (title != null && !title.isBlank()) {
            String[] lines = title.split("\\r?\\n");
            float titleFontSize = Math.max(fontSize + 2f, 12f);
            float subtitleFontSize = Math.max(fontSize, 9f);

            titleBlockHeight = titleFontSize;
            if (lines.length > 1) {
                titleBlockHeight += TITLE_SPACING / 2f;
                titleBlockHeight += (lines.length - 1) * (subtitleFontSize + 2f);
            }

            float cursorY = y;
            cs.setNonStrokingColor(Color.BLACK);

            String first = lines[0];
            float wFirst = boldFont.getStringWidth(first) / 1000f * titleFontSize;
            float xFirst = marginLeft + (usableWidth - wFirst) / 2f;
            float yFirst = cursorY - titleFontSize;
            cs.beginText();
            cs.setFont(boldFont, titleFontSize);
            cs.newLineAtOffset(xFirst, yFirst);
            cs.showText(first);
            cs.endText();

            cursorY -= titleFontSize;
            if (lines.length > 1) cursorY -= TITLE_SPACING / 2f;

            for (int li = 1; li < lines.length; li++) {
                String s = lines[li];
                float w = font.getStringWidth(s) / 1000f * subtitleFontSize;
                float x = marginLeft + (usableWidth - w) / 2f;
                float yLine = cursorY - subtitleFontSize;
                cs.beginText();
                cs.setFont(font, subtitleFontSize);
                cs.newLineAtOffset(x, yLine);
                cs.showText(s);
                cs.endText();
                cursorY -= (subtitleFontSize + 2f);
            }

            y -= titleBlockHeight;
            y -= TITLE_SPACING;
        }

        // set stroke color and header line width before drawing header row
        cs.setStrokingColor(Color.BLACK);
        cs.setLineWidth(HEADER_STROKE_WIDTH);
        float headerRowHeight = drawRow(cs, headerLines, boldFont, fontSize, marginLeft, y, colWidths);
        y -= headerRowHeight;

        // switch to default stroke width for data rows
        cs.setLineWidth(DEFAULT_STROKE_WIDTH);

        for (int r = 0; r < table.getRowCount(); r++) {
            List<List<String>> rowLines = new ArrayList<>(cols);
            for (int c = 0; c < cols; c++) {
                String text = collapseWhitespace(safeString(table.getValueAt(r, c)));
                List<String> wrapped = wrapText(text, Math.max(1, (int)(colWidths[c] - 2f*ROW_PADDING)), font, fontSize);
                for (int i = 0; i < wrapped.size(); i++) {
                    String line = wrapped.get(i);
                    float w = font.getStringWidth(line) / 1000f * fontSize;
                    if (w > colWidths[c]) {
                        int approx = Math.max(3, (int)((colWidths[c] / (font.getStringWidth("W") / 1000f * fontSize)) - 3));
                        if (approx < line.length()) wrapped.set(i, line.substring(0, approx) + "...");
                    }
                }
                rowLines.add(wrapped);
            }
            float rowHeight = computeBlockHeight(rowLines, fontSize);

            if (y - rowHeight < marginBottom + footerImageHeightPts + FOOTER_TEXT_HEIGHT) {
                cs.close();
                page = new PDPage(pageSize);
                doc.addPage(page);
                cs = new PDPageContentStream(doc, page);
                y = pageSize.getHeight() - marginTop;

                if (headerPD != null && headerImageHeightPts > 0f) {
                    float drawW = baseImageDrawWidth;
                    float xOffset = marginLeft + Math.max(0, (usableWidth - drawW) / 2f);
                    float drawH = headerImageHeightPts;
                    cs.drawImage(headerPD, xOffset, y - drawH, drawW, drawH);
                    y -= drawH;
                }

                if (title != null && !title.isBlank()) {
                    String[] lines = title.split("\\r?\\n");
                    float titleFontSize = Math.max(fontSize + 2f, 12f);
                    float subtitleFontSize = Math.max(fontSize, 9f);
                    float cursorY = y;
                    cs.setNonStrokingColor(Color.BLACK);

                    String first = lines[0];
                    float wFirst = boldFont.getStringWidth(first) / 1000f * titleFontSize;
                    float xFirst = marginLeft + (usableWidth - wFirst) / 2f;
                    float yFirst = cursorY - titleFontSize;
                    cs.beginText();
                    cs.setFont(boldFont, titleFontSize);
                    cs.newLineAtOffset(xFirst, yFirst);
                    cs.showText(first);
                    cs.endText();

                    cursorY -= titleFontSize;
                    if (lines.length > 1) cursorY -= TITLE_SPACING / 2f;

                    for (int li = 1; li < lines.length; li++) {
                        String s = lines[li];
                        float w = font.getStringWidth(s) / 1000f * subtitleFontSize;
                        float x = marginLeft + (usableWidth - w) / 2f;
                        float yLine = cursorY - subtitleFontSize;
                        cs.beginText();
                        cs.setFont(font, subtitleFontSize);
                        cs.newLineAtOffset(x, yLine);
                        cs.showText(s);
                        cs.endText();
                        cursorY -= (subtitleFontSize + 2f);
                    }

                    float titleBlockHeightOnPage = titleFontSize + (lines.length > 1 ? (TITLE_SPACING / 2f + (lines.length - 1)*(subtitleFontSize + 2f)) : 0f);
                    y -= titleBlockHeightOnPage;
                    y -= TITLE_SPACING;
                }

                // header on new page (use header stroke width)
                cs.setStrokingColor(Color.BLACK);
                cs.setLineWidth(HEADER_STROKE_WIDTH);
                headerRowHeight = drawRow(cs, headerLines, boldFont, fontSize, marginLeft, y, colWidths);
                y -= headerRowHeight;
                cs.setLineWidth(DEFAULT_STROKE_WIDTH);
            }

            float drawnRowHeight = drawRow(cs, rowLines, font, fontSize, marginLeft, y, colWidths);
            y -= drawnRowHeight;
        }

        if (footerPD != null && footerImageHeightPts > 0f) {
            float drawW = baseImageDrawWidth;
            float drawH = footerImageHeightPts;
            float xOffset = marginLeft + Math.max(0, (usableWidth - drawW) / 2f);
            cs.drawImage(footerPD, xOffset, marginBottom, drawW, drawH);
        }

        cs.close();
    }

    private static float[] measureAndDistributeColumnWidths(PDFont font, float fontSize, JTable table, int cols, float usableWidth) throws IOException {
        float[] measured = new float[cols];
        float totalMeasured = 0f;

        for (int c = 0; c < cols; c++) {
            float maxW = 0f;
            String header = safeString(table.getColumnName(c));
            float hw = font.getStringWidth(header) / 1000f * fontSize;
            if (hw > maxW) maxW = hw;

            for (int r = 0; r < table.getRowCount(); r++) {
                String cell = safeString(table.getValueAt(r, c));
                float cw = font.getStringWidth(cell) / 1000f * fontSize;
                if (cw > maxW) maxW = cw;
            }

            maxW += 2f * ROW_PADDING;
            if (maxW < MIN_COLUMN_WIDTH) maxW = MIN_COLUMN_WIDTH;

            measured[c] = maxW;
            totalMeasured += maxW;
        }

        if (totalMeasured <= usableWidth) {
            float extra = usableWidth - totalMeasured;
            if (extra > 1f) {
                for (int c = 0; c < cols; c++) {
                    float add = (measured[c] / totalMeasured) * extra;
                    measured[c] += add;
                }
            }
            return measured;
        }

        float[] scaled = new float[cols];
        float scale = usableWidth / totalMeasured;
        float remainingWidth = usableWidth;
        boolean[] fixed = new boolean[cols];

        for (int c = 0; c < cols; c++) {
            float s = measured[c] * scale;
            if (s < MIN_COLUMN_WIDTH) {
                scaled[c] = MIN_COLUMN_WIDTH;
                fixed[c] = true;
                remainingWidth -= scaled[c];
            } else {
                scaled[c] = s;
            }
        }

        float sumUnfixedMeasured = 0f;
        for (int c = 0; c < cols; c++) if (!fixed[c]) sumUnfixedMeasured += measured[c];

        if (sumUnfixedMeasured > 0f) {
            for (int c = 0; c < cols; c++) {
                if (!fixed[c]) {
                    float share = (measured[c] / sumUnfixedMeasured) * remainingWidth;
                    scaled[c] = share;
                }
            }
        } else {
            float per = usableWidth / cols;
            for (int c = 0; c < cols; c++) scaled[c] = per;
        }

        return scaled;
    }

    private static float drawRow(PDPageContentStream cs, List<List<String>> rowLines, PDFont font, float fontSize, float startX, float yTop, float[] colWidths) throws IOException {
        float rowHeight = computeBlockHeight(rowLines, fontSize);

        float x = startX;
        for (int col = 0; col < rowLines.size(); col++) {
            List<String> lines = rowLines.get(col);
            float colW = colWidths[col];
            float textY = yTop - ROW_PADDING;
            for (String line : lines) {
                cs.beginText();
                cs.setFont(font, fontSize);
                cs.newLineAtOffset(x + ROW_PADDING, textY - fontSize);
                cs.showText(line);
                cs.endText();
                textY -= fontSize + 1f;
            }
            cs.addRect(x, yTop - rowHeight, colW, rowHeight);
            cs.stroke();
            x += colW;
        }

        return rowHeight;
    }

    private static void addFooterTextToAllPages(PDDocument doc, PDFont font, float fontSize, float marginLeft, float marginBottom, float marginRight) throws IOException {
        int total = doc.getNumberOfPages();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateStr = now.format(fmt);

        float footerFontSize = Math.max(8f, fontSize - 2f);

        for (int i = 0; i < total; i++) {
            PDPage page = doc.getPage(i);
            PDRectangle media = page.getMediaBox();
            float y = marginBottom;
            try (PDPageContentStream cs = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                cs.setNonStrokingColor(Color.BLACK);
                cs.beginText();
                cs.setFont(font, footerFontSize);
                cs.newLineAtOffset(marginLeft, y);
                cs.showText("Printed: " + dateStr);
                cs.endText();

                String p = "Page " + (i + 1) + " / " + total;
                float pw = font.getStringWidth(p) / 1000f * footerFontSize;
                float x = media.getWidth() - marginRight - pw;
                cs.beginText();
                cs.setFont(font, footerFontSize);
                cs.newLineAtOffset(x, y);
                cs.showText(p);
                cs.endText();
            }
        }
    }

    private static float computeBlockHeight(List<List<String>> columnLines, float fontSize) {
        int maxLines = 0;
        for (List<String> lines : columnLines) if (lines != null && lines.size() > maxLines) maxLines = lines.size();
        return maxLines * (fontSize + 1f) + 2f * ROW_PADDING;
    }

    private static List<String> wrapText(String text, int maxWidthPoints, PDFont font, float fontSize) throws IOException {
        List<String> out = new ArrayList<>();
        if (text == null) { out.add(""); return out; }
        text = text.trim();
        if (text.isEmpty()) { out.add(""); return out; }

        String[] words = text.split("\\s+");
        StringBuilder line = new StringBuilder();

        for (String w : words) {
            String candidate = line.length() == 0 ? w : line + " " + w;
            float width = font.getStringWidth(candidate) / 1000f * fontSize;
            if (width <= maxWidthPoints) {
                line = new StringBuilder(candidate);
            } else {
                if (line.length() > 0) {
                    out.add(line.toString());
                }
                float wordWidth = font.getStringWidth(w) / 1000f * fontSize;
                if (wordWidth <= maxWidthPoints) {
                    line = new StringBuilder(w);
                } else {
                    StringBuilder chunk = new StringBuilder();
                    for (char ch : w.toCharArray()) {
                        chunk.append(ch);
                        float cw = font.getStringWidth(chunk.toString()) / 1000f * fontSize;
                        if (cw > maxWidthPoints) {
                            if (chunk.length() > 1) {
                                chunk.setLength(chunk.length() - 1);
                                out.add(chunk.toString() + "-");
                                chunk = new StringBuilder();
                                chunk.append(ch);
                            } else {
                                out.add(String.valueOf(ch));
                                chunk = new StringBuilder();
                            }
                        }
                    }
                    if (chunk.length() > 0) line = new StringBuilder(chunk.toString());
                    else line = new StringBuilder();
                }
            }
        }
        if (line.length() > 0) out.add(line.toString());
        if (out.isEmpty()) out.add("");
        return out;
    }

    private static String collapseWhitespace(String s) {
        if (s == null) return "";
        return s.trim().replaceAll("\\s+", " ");
    }

    private static String safeString(Object o) {
        if (o == null) return "";
        String s = o.toString();
        return s == null ? "" : s;
    }

    public static float[] convertMargins(String name) {
        switch (name) {
            case "Narrow": return new float[]{20f, 20f, 20f, 20f};
            case "Moderate": return new float[]{36f, 36f, 36f, 36f};
            case "Wide": return new float[]{60f, 60f, 60f, 60f};
            default: return new float[]{36f, 36f, 36f, 36f};
        }
    }
}