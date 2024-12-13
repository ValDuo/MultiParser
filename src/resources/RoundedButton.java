package resources;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;

public class RoundedButton extends JButton {
    private Color bgColor;
    private Color textColor;
    private Color borderColor;  // Цвет бордера
    private int arcWidth;
    private int arcHeight;
    private Font customFont;  // Шрифт кнопки

    public RoundedButton(String text) {
        super(text);
        this.bgColor = Color.BLUE; // Default background color
        this.textColor = Color.WHITE; // Default text color
        this.borderColor = Color.BLACK; // Default border color
        this.arcWidth = 20; // Default arc width
        this.arcHeight = 20; // Default arc height
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("other/Montserrat-Bold.ttf")).deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            customFont = new Font("Arial", Font.BOLD, 16); // В случае ошибки используем стандартный шрифт
        }

        setFont(customFont);  // Устанавливаем шрифт
        setForeground(textColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();

        // Рисуем закругленный фон
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
                0, 0, width - 1, height - 1, arcWidth, arcHeight);
        g2.setColor(bgColor);
        g2.fill(roundedRectangle);

        // Рисуем текст по центру кнопки
        g2.setColor(textColor);
        FontMetrics metrics = g2.getFontMetrics(getFont());
        int x = (width - metrics.stringWidth(getText())) / 2;
        int y = ((height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2.drawString(getText(), x, y);

        // Рисуем бордер
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(2));  // Толщина бордера
        g2.draw(roundedRectangle);  // Рисуем бордер по тому же кругу

        g2.dispose();
    }

    // Optional: Customize colors and dimensions
    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setArcWidth(int arcWidth) {
        this.arcWidth = arcWidth;
    }

    public void setArcHeight(int arcHeight) {
        this.arcHeight = arcHeight;
    }
}
