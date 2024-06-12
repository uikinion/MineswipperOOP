import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundStorage {
    private AudioInputStream bombSoundStream;
    public Clip getBombSound(){
        try {
            File soundFile = new File("bomb.wav");
            bombSoundStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Clip bombSound = null;
        try {
            bombSound = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            bombSound.open(bombSoundStream);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bombSound;
    }

    public SoundStorage() {

    }
}
