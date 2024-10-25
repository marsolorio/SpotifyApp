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
    private boolean isFavorite;
    private Clip clip;
    private boolean isPaused = false;
    private long clipPosition = 0; // For pause/resume functionality

    // Constructor
    public Song(String title, String artist, int year, String genre, String filePath, boolean isFavorite) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.genre = genre;
        this.filePath = filePath;
        this.isFavorite = isFavorite;
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

    public boolean isFavorite() {
        return isFavorite;
    }

    // Toggle favorite status with a message if already favorited
    public void toggleFavorite() {
        if (isFavorite) {
            System.out.println("The song \"" + title + "\" is already a favorite.");
        } else {
            isFavorite = true;
            System.out.println("The song \"" + title + "\" has been added to your favorites.");
        }
    }

    // Play the song with additional controls
    public void play() {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.setMicrosecondPosition(clipPosition); // Resume from last position if paused
            clip.start();
            System.out.println("Playing: " + title + " by " + artist);
            displayControls();

            // Handle user input for controls
            Scanner scanner = new Scanner(System.in);
            while (clip.isRunning() || isPaused) {
                String input = scanner.nextLine();
                switch (input.toLowerCase()) {
                    case "e":
                        stop();
                        return;
                    case "p":
                        pauseResume();
                        break;
                    case "r":
                        rewind(5000000);
                        break;
                    case "f":
                        forward(5000000);
                        break;
                    case "t":
                        toggleFavorite();
                        break;
                    default:
                        System.out.println(
                                "Invalid input. Use 'p' to pause/resume, 'e' to stop, 'r' to rewind, 'f' to forward, 't' to toggle favorite.");
                }
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error playing the song: " + e.getMessage());
        }
    }

    // Pause or resume the audio
    public void pauseResume() {
        if (isPaused) {
            clip.setMicrosecondPosition(clipPosition); // Resume from saved position
            clip.start();
            System.out.println("Resumed: " + title);
        } else {
            clipPosition = clip.getMicrosecondPosition();
            clip.stop();
            System.out.println("Paused: " + title);
        }
        isPaused = !isPaused;
    }

    // Rewind the audio by a specified amount of microseconds
    public void rewind(long microseconds) {
        long newPosition = Math.max(clip.getMicrosecondPosition() - microseconds, 0);
        clip.setMicrosecondPosition(newPosition);
        System.out.println("Rewound 5 seconds.");
    }

    // Forward the audio by a specified amount of microseconds
    public void forward(long microseconds) {
        long newPosition = Math.min(clip.getMicrosecondPosition() + microseconds, clip.getMicrosecondLength());
        clip.setMicrosecondPosition(newPosition);
        System.out.println("Forwarded 5 seconds.");
    }

    // Stop the audio clip
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
            clipPosition = 0; // Reset position
            isPaused = false;
            System.out.println("Song stopped.");
        }
    }

    // Display the control options to the user
    private void displayControls() {
        System.out.println(
                "Controls: 'p' = pause/resume, 'e' = stop, 'r' = rewind, 'f' = forward, 't' = toggle favorite");
    }
}
