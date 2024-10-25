import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javax.sound.sampled.*;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

public class App {

    // List to store songs loaded from JSON
    private static List<Song> songLibrary;

    public static void main(String[] args) {
        loadLibraryFromJson(); // Load songs from JSON file
        displayMenu(); // Show interactive menu
    }

    // Method to load the library from the audio-library.json file
    public static void loadLibraryFromJson() {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader("src/main/resources/audio-library.json");
            songLibrary = gson.fromJson(reader, new TypeToken<List<Song>>() {
            }.getType());
            reader.close();
            System.out.println("Library loaded with " + songLibrary.size() + " songs.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to display the main menu
    public static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        String choice = "";

        while (!choice.equalsIgnoreCase("Q")) {
            System.out.println("Welcome to Spotify-Like App");
            System.out.println("[H]ome");
            System.out.println("[S]earch by title");
            System.out.println("[L]ibrary");
            System.out.println("[Q]uit");
            System.out.print("Choose an option: ");
            choice = scanner.nextLine();

            switch (choice.toUpperCase()) {
                case "H":
                    showHome();
                    break;
                case "S":
                    searchByTitle(scanner);
                    break;
                case "L":
                    showLibrary();
                    break;
                case "Q":
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    // Method to display the home screen (can be customized)
    public static void showHome() {
        System.out.println("Welcome to Home! Your songs are ready to play.");
        showLibrary();
    }

    // Method to search for a song by title and play it
    public static void searchByTitle(Scanner scanner) {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine();
        boolean found = false;

        for (Song song : songLibrary) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Found: " + song.getTitle() + " by " + song.getArtist());
                song.play();
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Song not found.");
        }
    }

    // Method to show the library and allow selection to play
    public static void showLibrary() {
        if (songLibrary != null && !songLibrary.isEmpty()) {
            int index = 1;
            for (Song song : songLibrary) {
                System.out.println(index++ + ". " + song.getTitle() + " by " + song.getArtist() + " (" + song.getYear()
                        + ", " + song.getGenre() + ")");
            }
        } else {
            System.out.println("No songs available.");
        }
    }
}
