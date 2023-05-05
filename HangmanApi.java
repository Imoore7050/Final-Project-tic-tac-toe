/*
Name: Isaiah Moore, Roshni Patel, Zaid Barakat
Date: 5-3-23
Project: this program is an Hangman style game that takes a word form an api and the player needs
to solve it
*/
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HangmanApi
{
    public static void main(String[] args) throws IOException 
    {
        // Make a GET request to the API
        URL url = new URL("https://random-word-api.herokuapp.com/word");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        // Parse the JSON response using GSON
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(new InputStreamReader(connection.getInputStream()), JsonElement.class);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        
        //Print a copy
        System.out.println(jsonArray);
        
        //Hangman Game
        Scanner scanner = new Scanner(System.in);
        //Takes a random word from the Api
        String apiWord = jsonArray.toString();
        String word = apiWord.replaceAll("[^a-zA-Z]", "");
        String hiddenWord = new String(new char[word.length()]).replace("\0", "-"); // hide the word with dashes
        int attemptsLeft = 10;

        //Intro
        System.out.println("Welcome to Hangman!");
        System.out.println("The word has " + word.length() + " letters. You have " + attemptsLeft + " attempts to guess the word.");
        
        //
        while (attemptsLeft > 0 && hiddenWord.contains("-"))
        {
            System.out.println("Guess a letter:");
            System.out.println(hiddenWord);
            String guess = scanner.nextLine().toLowerCase();
            
            //Single letter only and guess
            if (guess.length() != 1) 
            {
                System.out.println("Please enter a single letter.");
            } 
            else if (word.contains(guess)) 
            {
                System.out.println("Correct!");
                StringBuilder builder = new StringBuilder(hiddenWord);
                for (int i = 0; i < word.length(); i++) 
                {
                    if (word.charAt(i) == guess.charAt(0)) 
                    {
                        builder.setCharAt(i, guess.charAt(0));
                    }
                }
                hiddenWord = builder.toString();
            } 
            
            else 
            {
                System.out.println("Incorrect.");
                attemptsLeft--;
            }
        }
        
        //Outro
        if (!hiddenWord.contains("-")) 
        {
            System.out.println("Congratulations, you won!");
        }
        else 
        {
            System.out.println("Sorry, you lost. The word was " + word + ".");
        }
    }
}