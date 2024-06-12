import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameOverPanel extends JPanel {
    private final BufferedImage bombImage;

    public GameOverPanel(BufferedImage bombImage) {
        this.bombImage = bombImage;
        setMinimumSize(new Dimension(bombImage.getWidth(), bombImage.getHeight()));
        setPreferredSize(new Dimension(bombImage.getWidth(), bombImage.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(bombImage, 0, 0, null);
    }
}
