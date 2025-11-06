package Forms;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.*;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * PDFAttendance — exports a JTable to a PDF using PDFBox.
 *
 * This version draws optional header/footer images (BufferedImage) on every page,
 * reserves space for them, and still provides pagination, wrapping, truncation and adaptive font sizing.
 * It also stamps "Printed: YYYY-MM-DD HH:mm" (left) and "Page X / Y" (right) on every page footer.
 *
 * New signature:
 *   export(table, outputFile, pageSize, landscape, marginLeft, marginTop, marginRight, marginBottom, headerImage, footerImage)
 *
 * Pass null for headerImage/footerImage to skip images.
 */
public final class PDFAttendance {
    private PDFAttendance() {}

    // Configuration
    private static final float BASE_FONT_SIZE = 10f;
    private static final float MIN_FONT_SIZE = 6f;
    private static final float ROW_PADDING = 2f;
    private static final float HEADER_SPACING = 8f;
    private static final float FOOTER_TEXT_HEIGHT = 12f; // text footer height reserve

    public static void export(JTable table,
                              File outputFile,
                              String pageSize,
                              boolean landscape,
                              float marginLeft,
                              float marginTop,
                              float marginRight,
                              float marginBottom,
                              BufferedImage headerImage,
                              BufferedImage footerImage) throws IOException {

        if (table == null || outputFile == null) return;

        try (PDDocument doc = new PDDocument()) {

            PDRectangle size = getPageSize(pageSize);
            if (landscape) size = new PDRectangle(size.getHeight(), size.getWidth());

            PDFont font = PDType1Font.HELVETICA;

            // Compute an adjusted font size so columns attempt to fit horizontally
            float availableWidth = size.getWidth() - marginLeft - marginRight;
            float fontSize = computeAdjustedFontSize(font, table, availableWidth, BASE_FONT_SIZE, MIN_FONT_SIZE);

            // Convert header/footer images to PDImageXObject if provided
            PDImageXObject headerPD = null;
            PDImageXObject footerPD = null;
            float headerImageHeightPts = 0f;
            float footerImageHeightPts = 0f;

            if (headerImage != null) {
                headerPD = LosslessFactory.createFromImage(doc, headerImage);
                // scale to available width (preserve aspect)
                float w = availableWidth;
                float h = (headerImage.getHeight() / (float) headerImage.getWidth()) * w;
                headerImageHeightPts = h;
            }
            if (footerImage != null) {
                footerPD = LosslessFactory.createFromImage(doc, footerImage);
                float w = availableWidth;
                float h = (footerImage.getHeight() / (float) footerImage.getWidth()) * w;
                footerImageHeightPts = h;
            }

            // Paged drawing that reserves header and footer image heights
            drawTablePaged(doc, table, font, fontSize, size, marginLeft, marginTop, marginRight, marginBottom, headerPD, footerPD, headerImageHeightPts, footerImageHeightPts);

            // After table pages created, append textual footers (printed date + page X/Y).
            // Text footer uses marginBottom + a small offset (so it sits above bottom edge or on top of footer image if present).
            addFooterTextToAllPages(doc, font, fontSize, marginLeft, marginBottom, marginRight);

            doc.save(outputFile);
        }
    }

    // Convenience overload that keeps previous calling convention (no images)
    public static void export(JTable table,
                              File outputFile,
                              String pageSize,
                              boolean landscape,
                              float marginLeft,
                              float marginTop,
                              float marginRight,
                              float marginBottom) throws IOException {
        export(table, outputFile, pageSize, landscape, marginLeft, marginTop, marginRight, marginBottom, null, null);
    }

    // Determine page rectangle from name
    private static PDRectangle getPageSize(String size) {
        switch (size) {
            case "Letter": return PDRectangle.LETTER;
            case "Legal":  return PDRectangle.LEGAL;
            default:       return PDRectangle.A4;
        }
    }

    // Try to reduce font size so the widest contents per column fit available width
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

    // Paged table drawer: handles pages, header repetition, row wrapping and borders
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
                                      float footerImageHeightPts) throws IOException {

        final float usableWidth = pageSize.getWidth() - marginLeft - marginRight;
        // Reserve enough vertical space for top/bottom images + footer text height
        final float usableHeight = pageSize.getHeight() - marginTop - marginBottom - footerImageHeightPts - FOOTER_TEXT_HEIGHT;

        int cols = table.getColumnCount();
        if (cols <= 0) return;

        // Equal column widths by default
        float colWidth = usableWidth / cols;

        // Prepare header wrapped lines
        List<List<String>> headerLines = new ArrayList<>(cols);
        for (int c = 0; c < cols; c++) {
            headerLines.add(wrapText(collapseWhitespace(safeString(table.getColumnName(c))), (int)colWidth, font, fontSize));
        }
        float headerHeight = computeBlockHeight(headerLines, fontSize);

        // Start first page
        PDPage page = new PDPage(pageSize);
        doc.addPage(page);
        PDPageContentStream cs = new PDPageContentStream(doc, page);
        float y = pageSize.getHeight() - marginTop;

        // Draw header image if present (top-most), and shift y down accordingly
        if (headerPD != null && headerImageHeightPts > 0f) {
            float drawW = usableWidth;
            float drawH = headerImageHeightPts;
            // draw image at left margin across usable width, anchored to the top margin
            cs.drawImage(headerPD, marginLeft, y - drawH, drawW, drawH);
            y -= drawH;
        }

        // Draw header titles below header image (or immediately under margin)
        y = drawHeaderLines(cs, headerLines, font, fontSize, marginLeft, y, colWidth);
        y -= HEADER_SPACING;

        for (int r = 0; r < table.getRowCount(); r++) {
            // build wrapped cells and compute row height
            List<List<String>> rowLines = new ArrayList<>(cols);
            for (int c = 0; c < cols; c++) {
                String text = collapseWhitespace(safeString(table.getValueAt(r, c)));
                List<String> wrapped = wrapText(text, (int)colWidth, font, fontSize);
                // if any resulting line still wider than col, truncate with ellipsis
                for (int i = 0; i < wrapped.size(); i++) {
                    String line = wrapped.get(i);
                    float w = font.getStringWidth(line) / 1000f * fontSize;
                    if (w > colWidth) {
                        int approx = Math.max(3, (int)((colWidth / (font.getStringWidth("W") / 1000f * fontSize)) - 3));
                        if (approx < line.length()) wrapped.set(i, line.substring(0, approx) + "...");
                    }
                }
                rowLines.add(wrapped);
            }
            float rowHeight = computeBlockHeight(rowLines, fontSize);

            // If row won't fit on current page (consider footer image and footer text), create a new page
            if (y - rowHeight < marginBottom + footerImageHeightPts + FOOTER_TEXT_HEIGHT) {
                cs.close();
                page = new PDPage(pageSize);
                doc.addPage(page);
                cs = new PDPageContentStream(doc, page);
                y = pageSize.getHeight() - marginTop;

                // header image on new page if present
                if (headerPD != null && headerImageHeightPts > 0f) {
                    float drawW = usableWidth;
                    float drawH = headerImageHeightPts;
                    cs.drawImage(headerPD, marginLeft, y - drawH, drawW, drawH);
                    y -= drawH;
                }

                y = drawHeaderLines(cs, headerLines, font, fontSize, marginLeft, y, colWidth);
                y -= HEADER_SPACING;
            }

            // draw row
            drawRow(cs, rowLines, font, fontSize, marginLeft, y, colWidth);
            y -= rowHeight;
        }

        // After rows drawn: draw footer image if present on last page, then close stream.
        if (footerPD != null && footerImageHeightPts > 0f) {
            // footer should be anchored above marginBottom
            float drawW = usableWidth;
            float drawH = footerImageHeightPts;
            float yPos = marginBottom + FOOTER_TEXT_HEIGHT; // ensure footer text area not overlapped (we place image slightly above bottom)
            // But we must draw at absolute position y = (drawH height above bottom), so:
            float yImage = yPos;
            // For safety we draw at marginBottom (y = marginBottom)
            cs.drawImage(footerPD, marginLeft, marginBottom, drawW, drawH);
        }

        cs.close();
    }

    // Draw wrapped header lines and cell borders; returns new Y
    private static float drawHeaderLines(PDPageContentStream cs, List<List<String>> headerLines, PDFont font, float fontSize, float startX, float yTop, float colWidth) throws IOException {
        float y = yTop;
        float blockHeight = computeBlockHeight(headerLines, fontSize);

        for (int col = 0; col < headerLines.size(); col++) {
            List<String> lines = headerLines.get(col);
            float textY = y - ROW_PADDING;
            for (String line : lines) {
                cs.beginText();
                cs.setFont(font, fontSize);
                cs.newLineAtOffset(startX + col * colWidth + ROW_PADDING, textY - fontSize);
                cs.showText(line);
                cs.endText();
                textY -= fontSize + 1f;
            }
            cs.addRect(startX + col * colWidth, y - blockHeight, colWidth, blockHeight);
            cs.stroke();
        }
        return y - blockHeight;
    }

    private static void drawRow(PDPageContentStream cs, List<List<String>> rowLines, PDFont font, float fontSize, float startX, float yTop, float colWidth) throws IOException {
        float rowHeight = computeBlockHeight(rowLines, fontSize);

        for (int col = 0; col < rowLines.size(); col++) {
            List<String> lines = rowLines.get(col);
            float textY = yTop - ROW_PADDING;
            for (String line : lines) {
                cs.beginText();
                cs.setFont(font, fontSize);
                cs.newLineAtOffset(startX + col * colWidth + ROW_PADDING, textY - fontSize);
                cs.showText(line);
                cs.endText();
                textY -= fontSize + 1f;
            }
            cs.addRect(startX + col * colWidth, yTop - rowHeight, colWidth, rowHeight);
            cs.stroke();
        }
    }

    // Add textual footer (printed date/time left and page X/Y right) to all pages
    private static void addFooterTextToAllPages(PDDocument doc, PDFont font, float fontSize, float marginLeft, float marginBottom, float marginRight) throws IOException {
        int total = doc.getNumberOfPages();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateStr = now.format(fmt);

        float footerFontSize = Math.max(8f, fontSize - 2f);

        for (int i = 0; i < total; i++) {
            PDPage page = doc.getPage(i);
            PDRectangle media = page.getMediaBox();
            float y = marginBottom; // put footer text at marginBottom (above true edge)
            // Append footer content
            try (PDPageContentStream cs = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                // left: printed date/time
                cs.beginText();
                cs.setFont(font, footerFontSize);
                cs.newLineAtOffset(marginLeft, y);
                cs.showText("Printed: " + dateStr);
                cs.endText();

                // right: page X / Y
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

    // Compute max block height (number of lines in tallest column) * (fontSize + lineSpacing) + paddings
    private static float computeBlockHeight(List<List<String>> columnLines, float fontSize) {
        int maxLines = 0;
        for (List<String> lines : columnLines) if (lines != null && lines.size() > maxLines) maxLines = lines.size();
        return maxLines * (fontSize + 1f) + 2f * ROW_PADDING;
    }

    // Wrap text into lines so each line's width <= maxWidthPoints (approx)
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
                // if word itself exceeds width, break it into smaller chunks
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

    // Collapse multiple whitespace to single spaces
    private static String collapseWhitespace(String s) {
        if (s == null) return "";
        return s.trim().replaceAll("\\s+", " ");
    }

    private static String safeString(Object o) {
        if (o == null) return "";
        String s = o.toString();
        return s == null ? "" : s;
    }

    // Margin conversion kept here for convenience (points)
    public static float[] convertMargins(String name) {
        switch (name) {
            case "Narrow": return new float[]{20f, 20f, 20f, 20f};
            case "Moderate": return new float[]{36f, 36f, 36f, 36f};
            case "Wide": return new float[]{60f, 60f, 60f, 60f};
            default: return new float[]{36f, 36f, 36f, 36f};
        }
    }
}