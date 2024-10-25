import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.sound.sampled.*;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class App {
    // List to store songs loaded from JSON
    private static List<Song> songLibrary;

    public static void main(String[] args) {
        // Load songs from JSON file
        loadLibraryFromJson();

        // Show interactive menu
        displayMenu();
    }

    // Method to load the library from the audio-library.json file
    public static void loadLibraryFromJson() {
        try {
            // Read the JSON file
            Gson gson = new Gson();
            FileReader reader = new FileReader("src/main/resources/audio-library.json");

            // Parse the JSON file into a list of Song objects
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
            System.out.println("\nWelcome to the Spotify-Like App");
            System.out.println("[H]ome");
            System.out.println("[S]earch by title");
            System.out.println("[L]ibrary");
            System.out.println("[F]avorites");
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
                case "F":
                    showFavorites();
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
        System.out.println("\nWelcome to Home! Your songs are ready to play.");
        showLibrary(); // Display the library on the home screen
    }

    // Method to show favorite songs and play selected ones
    public static void showFavorites() {
        System.out.println("\nFavorite Songs:");
        int index = 1;
        for (Song song : songLibrary) {
            if (song.isFavorite()) {
                System.out.println(index + ". " + song.getTitle() + " by " + song.getArtist() +
                        " (" + song.getYear() + ", " + song.getGenre() + ")");
            }
            index++;
        }
        System.out.println("\nSelect a number to play a favorite song, or press Enter to return to the menu:");
        playSelectedSong();
    }

    // Method to search for a song by title and play it with an option to toggle
    // favorite
    public static void searchByTitle(Scanner scanner) {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine().toLowerCase();
        boolean found = false;

        for (Song song : songLibrary) {
            if (song.getTitle().toLowerCase().contains(title)) {
                System.out.println("Found: " + song.getTitle() + " by " + song.getArtist());
                song.play(); // Play the song
                found = true;

                // Offer to toggle favorite status
                System.out.print("Do you want to favorite this song? (y/n): ");
                String favoriteChoice = scanner.nextLine();
                if (favoriteChoice.equalsIgnoreCase("y")) {
                    song.toggleFavorite();
                }
                break;
            }
        }

        if (!found) {
            System.out.println("Song not found.");
        }
    }

    // Method to show the entire library and allow selection to play and favorite
    public static void showLibrary() {
        if (songLibrary != null && !songLibrary.isEmpty()) {
            int index = 1;
            for (Song song : songLibrary) {
                System.out.println(index++ + ". " + song.getTitle() + " by " + song.getArtist() +
                        " (" + song.getYear() + ", " + song.getGenre() + ")");
            }
            System.out.println("\nSelect a number to play a song, or press Enter to return to the menu:");
            playSelectedSong();
        } else {
            System.out.println("No songs available.");
        }
    }

    // Method to play the selected song from the library with a toggle favorite
    // option
    public static void playSelectedSong() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (!input.isEmpty()) {
            try {
                int choice = Integer.parseInt(input);
                if (choice > 0 && choice <= songLibrary.size()) {
                    Song selectedSong = songLibrary.get(choice - 1);
                    selectedSong.play();

                    // Offer to toggle favorite status
                    System.out.print("Do you want to favorite this song? (y/n): ");
                    String favoriteChoice = scanner.nextLine();
                    if (favoriteChoice.equalsIgnoreCase("y")) {
                        selectedSong.toggleFavorite();
                    }
                } else {
                    System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
    }
}
