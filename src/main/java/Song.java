import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Song {
    private String title;
    private String artist;
    private int year;
    private String genre;
    private String filePath;

    // Constructor
    public Song(String title, String artist, int year, String genre, String filePath) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.genre = genre;
        this.filePath = filePath;
    }

    // Getter methods
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
            // Open the audio file as an AudioInputStream
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Get a sound clip resource
            Clip clip = AudioSystem.getClip();

            // Open the audio clip and load the audio from the audio stream
            clip.open(audioStream);

            // Start playing the audio clip
            clip.start();
            System.out.println("Playing: " + title + " by " + artist);

            // Keep the thread running until the audio clip finishes playing
            Thread.sleep(clip.getMicrosecondLength() / 1000);

            // Close the clip after playing
            clip.close();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            System.out.println("Error playing the song: " + e.getMessage());
        }
    }
}
