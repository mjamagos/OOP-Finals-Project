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

public class Panel2 extends JPanel {

    private int cornerRadius = 0;
    private Color backgroundColor = new Color(242,247,246);
    private Color borderColor = new Color(180, 180, 180);

    public Panel2() {
        setOpaque(false);
        setBackground(backgroundColor);
    }

    public Panel2(int radius) {
        this();
        this.cornerRadius = radius;
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

    public void setBorderColor(Color c) {
        this.borderColor = c;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2.dispose();
        super.paintComponent(g);
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
}
