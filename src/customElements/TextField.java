/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jobelle
 */
package customElements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TextField extends JTextField {

    private String placeholder = "Enter UserID";
    private final Color placeholderColor = new Color(150, 150, 150);
    private Color borderColor = new Color(180, 180, 180);
    private int cornerRadius = 20;
    private boolean showingPlaceholder = true;

    public TextField() {
        setOpaque(false);
        setFont(new Font("Poppins", Font.PLAIN, 12));
        setForeground(placeholderColor);
        setCaretColor(Color.BLACK);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        setPreferredSize(new Dimension(200, 35));
        setText(placeholder);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (showingPlaceholder) {
                    setText("");
                    setForeground(Color.BLACK);
                    showingPlaceholder = false;
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(placeholderColor);
                    showingPlaceholder = true;
                }
            }
        });
    }

    @Override
    public Insets getInsets() {
        return new Insets(10, 15, 10, 15);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        if (showingPlaceholder) {
            g2.setFont(getFont());
            g2.setColor(placeholderColor);
            FontMetrics fm = g2.getFontMetrics();
            int x = getInsets().left;
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            g2.drawString(placeholder, x, y);
        } else {
            super.paintComponent(g);
        }
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, cornerRadius, cornerRadius);
        g2.dispose();
    }

    public void setBorderColor(Color c) {
        this.borderColor = c;
        repaint();
    }

    public void setCornerRadius(int r) {
        this.cornerRadius = r;
        repaint();
    }

    public void setPlaceholder(String text) {
        this.placeholder = text;
        if (getText().isEmpty() || showingPlaceholder) {
            setText(text);
            setForeground(placeholderColor);
            showingPlaceholder = true;
        }
        repaint();
    }

    @Override
    public String getText() {
        return showingPlaceholder ? "" : super.getText();
    }
}