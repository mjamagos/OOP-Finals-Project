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
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class RoundedComboBox<E> extends JComboBox<E> {

    private int radius = 10;

    public RoundedComboBox() {
        super();
        init();
    }

    public RoundedComboBox(E[] items) {
        super(items);
        init();
    }

    private void init() {
        setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton arrow = new JButton("\u25BC");
                arrow.setBorder(null);
                arrow.setContentAreaFilled(false);
                arrow.setFocusable(false);
                return arrow;
            }

            // Prevent painting the editor background overlay
            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                // do nothing
            }

            // Don't override paintCurrentValue; leave it as default
        });

        setBorder(BorderFactory.createEmptyBorder());
        setOpaque(false);
        setEditable(false);

        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return label;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // skip default border painting
    }
}
