import javax.sound.sampled.*;
import java.io.File;

public class Song {
    private String title;
    private String artist;
    private int year;
    private String genre;
    private String filePath;

    public Song(String title, String artist, int year, String genre, String filePath) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.genre = genre;
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getFilePath() {
        return filePath;
    }

    // Play the song using Java's sound system
    public void play() {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            System.out.println("Playing: " + title + " by " + artist);
            // Keep the clip playing until it's finished
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (Exception e) {
            System.out.println("Error playing the song: " + e.getMessage());
        }
    }
}
