/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customElements;

/**
 *
 * @author Jobelle
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckBox extends JCheckBox {

    private int cornerRadius = 12;
    private Color backgroundColor = new Color (255,255,255);
    private Color boxColor = new Color(180, 180, 180);    // <-- GRAY checkbox
    private Color checkColor = new Color(80, 180, 80);    // Checkmark color
    private Color textColor = Color.BLACK;

    public CheckBox(String text) {
        super(text);
        setOpaque(false);
        setFont(new Font("Poppins", Font.PLAIN, 12));
        setForeground(textColor);
        setBackground(backgroundColor);
        setFocusPainted(false);
        setBorderPainted(false);
        setPreferredSize(new Dimension(120, 24));
        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setSelected(!isSelected());
            }
        });
    }

    public CheckBox() {
        this("");
    }

    public void setCornerRadius(int r) {
        this.cornerRadius = r;
        repaint();
    }

    public void setBackgroundColor(Color c) {
        this.backgroundColor = c;
        setBackground(c);
        repaint();
    }

    public void setBoxColor(Color c) {
        this.boxColor = c;
        repaint();
    }

    public void setCheckColor(Color c) {
        this.checkColor = c;
        repaint();
    }

    public void setTextColor(Color c) {
        this.textColor = c;
        setForeground(c);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw rounded panel background
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Draw the gray checkbox square
        int boxSize = 16;
        int x = 8;
        int y = (getHeight() - boxSize) / 2;
        g2.setColor(boxColor); // <-- gray color here
        g2.fillRoundRect(x, y, boxSize, boxSize, 6, 6);

        // Draw checkmark if selected
        if (isSelected()) {
            g2.setColor(checkColor);
            g2.setStroke(new BasicStroke(2.2f));
            // Draw a modern checkmark
            g2.drawLine(x + 4, y + boxSize / 2, x + boxSize / 2, y + boxSize - 4);
            g2.drawLine(x + boxSize / 2, y + boxSize - 4, x + boxSize - 4, y + 4);
        }

        // Draw text
        g2.setColor(textColor);
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        String text = getText();
        int textX = x + boxSize + 8;
        int textY = (getHeight() + fm.getAscent()) / 2 - 2;
        g2.drawString(text, textX, textY);

        g2.dispose();
    }
}