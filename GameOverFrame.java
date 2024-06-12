import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameOverFrame extends JFrame {
    public GameOverFrame(BufferedImage bombImage) {
        setAlwaysOnTop(true);
        add(new GameOverPanel(bombImage));
        setMinimumSize(new Dimension(bombImage.getWidth(), bombImage.getHeight()));
        setPreferredSize(new Dimension(bombImage.getWidth(), bombImage.getHeight()));
        setVisible(true);
    }
}
