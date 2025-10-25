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

public class PanelNoBorder extends JPanel {

    private int cornerRadius = 10;
    private Color backgroundColor = new Color(242,247,246);

    public PanelNoBorder() {
        setOpaque(false);
        setBackground(backgroundColor);
    }

    public PanelNoBorder(int radius) {
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


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2.dispose();
        super.paintComponent(g);
    }

}
