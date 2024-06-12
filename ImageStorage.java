import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageStorage {
    private final BufferedImage bombImage;
    private final ImageIcon flagImage;
    public ImageStorage(){
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = (int) screenSize.getWidth();
            int height = (int) screenSize.getHeight();
            BufferedImage image = ImageIO.read(new FileInputStream("bomb.jpg"));
            bombImage = resizeImage(image, width, height);
            image = ImageIO.read(new FileInputStream("flag.png"));
            flagImage = new ImageIcon(resizeImage(image, 45, 45));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    public BufferedImage getBombImage() {
        return bombImage;
    }

    public ImageIcon getFlagImage() {
        return flagImage;
    }
}
