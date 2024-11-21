import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
public class RoundedButton extends JButton {
    private Color bgColor;
    private Color textColor;
    private int arcWidth;
    private int arcHeight;

    public RoundedButton(String text) {
        super(text);
        this.bgColor = Color.BLUE; // Default background color
        this.textColor = Color.WHITE; // Default text color
        this.arcWidth = 20; // Default arc width
        this.arcHeight = 20; // Default arc height
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setForeground(textColor);
        setFont(new Font("Arial", Font.BOLD, 14)); // Set font for the text
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
                0, 0, width - 1, height - 1, arcWidth, arcHeight);
        g2.setColor(bgColor);
        g2.fill(roundedRectangle);
        g2.setColor(textColor);
        FontMetrics metrics = g2.getFontMetrics(getFont());
        int x = (width - metrics.stringWidth(getText())) / 2;
        int y = ((height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2.drawString(getText(), x, y);
        g2.dispose();
    }
    // Optional: Customize colors and dimensions
    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }
    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }
    public void setArcWidth(int arcWidth) {
        this.arcWidth = arcWidth;
    }
    public void setArcHeight(int arcHeight) {
        this.arcHeight = arcHeight;
    }
}