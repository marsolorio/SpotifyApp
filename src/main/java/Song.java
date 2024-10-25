import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Song {
    private String title;
    private String artist;
    private int year;
    private String genre;
    private String filePath;
    private Clip clip; // Make the Clip a class-level variable

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

            // Get a sound clip resource and store it in the class variable
            clip = AudioSystem.getClip();

            // Open the audio clip and load the audio from the audio stream
            clip.open(audioStream);

            // Start playing the audio clip
            clip.start();
            System.out.println("Playing: " + title + " by " + artist);

            // Handle stopping in the main thread
            Scanner scanner = new Scanner(System.in);
            System.out.println("Press 'e' to stop the song.");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("e")) {
                stop();
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error playing the song: " + e.getMessage());
        }
    }

    // Method to stop the audio clip
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop(); // Stop the audio
            clip.close(); // Release system resources
            System.out.println("Song stopped.");
        }
    }
}
