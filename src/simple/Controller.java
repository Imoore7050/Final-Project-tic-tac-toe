package simple;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

public class Controller
{
    private String word;
    private String hidden;
    public String getWord() throws IOException
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

        //Takes a random word from the Api
        String apiWord = jsonArray.toString();
        String word = apiWord.replaceAll("[^a-zA-Z]", "");

        return word;

    }

    public void setWord(String word)
    {
        this.word = word;
    }
    public String getHidden(String word) throws IOException {
        // hide the word with dashes
        String hiddenWord = new String(new char[word.length()]).replace("\0", "-");

        return hiddenWord;
    }

    public void setHidden(String hiddenWord)
    {
        this.hidden = hiddenWord;
    }
    @FXML
    private Button guessButton;

    @FXML
    private Button startButton;

    @FXML
    private TextField theWord;

    @FXML
    private TextField tfGuessLetter;

    @FXML
    private TextField tfGuessWord;


    @FXML
    void btnStartClicked(ActionEvent event) throws IOException {
        String word = getWord();
        setWord(word);
        setHidden(getHidden(word));
        System.out.println(word);
        theWord.setText(hidden);
    }

    @FXML
    void btnGuessClicked(ActionEvent event) throws IOException
    {

        int attemptsLeft = 10;
        while (attemptsLeft > 0 || hidden.contains("-")) {

            System.out.println("Guess a letter:");
            System.out.println(hidden);
            String guess = tfGuessLetter.getText().toLowerCase();

            //Single letter only and guess
            if (guess.length() != 1) {
                System.out.println("Please enter a single letter.");
                attemptsLeft--;
            } else if (word.contains(guess)) {
                System.out.println("Correct!");
                attemptsLeft--;
                StringBuilder builder = new StringBuilder(hidden);
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == guess.charAt(0)) {
                        builder.setCharAt(i, guess.charAt(0));
                    }
                }
                hidden = builder.toString();

            } else {
                System.out.println("Incorrect.");
                attemptsLeft--;
            }

        }



        //Outro
        if (!hidden.contains("-"))
        {
            System.out.println("Congratulations, you won!");
        }
        else
        {
            System.out.println("Sorry, you ran out of attempts. The word was " + word + ".");
        }
    }
}