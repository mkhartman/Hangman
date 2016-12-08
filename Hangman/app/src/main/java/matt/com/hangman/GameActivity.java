package matt.com.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static matt.com.hangman.R.id.usedLetters;

public class GameActivity extends AppCompatActivity{
    String[] WORD_LIST = new String[]{"word"};
    //String[] WORD_LIST = new String[]{"word", "meme", "internet", "android", "computer", "stack", "overflow", "game", "stress", "anxiety"};
    int mFailCounter = 0;
    List<String> guessedLetters;
    int mCorrectGuesses = 0;
    String mGameWord;
    short EASY_POINTS = 1;
    short MED_POINTS = 3;
    short HARD_POINTS = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game);
        //mEditLetter = (EditText)findViewById(R.id.editLetter);
        guessedLetters = new ArrayList<>();
        newGame();

    }

    public void newGame(){
        resetGame();
        // Grab the word to guess
        Random rng = new Random();
        int indx = rng.nextInt(WORD_LIST.length);

        mGameWord = WORD_LIST[indx];

        mCorrectGuesses = 0;
        mFailCounter = 0;
    }

    public void introduceLetter(View v){
        TextView textViewUsed = (TextView) findViewById(usedLetters);
        EditText mEditLetter = (EditText)findViewById(R.id.editLetter);
        String letter = mEditLetter.getText().toString().toLowerCase();
        String usedLetters = "";
        guessedLetters.add(letter);

        for(int i = 0; i < guessedLetters.size(); i++){
            usedLetters = usedLetters + guessedLetters.get(i);
        }
        textViewUsed.setText(usedLetters);

        if (letter.length() == 1)
            checkLetter(letter, v);
        else{
            Toast.makeText(this, "Please guess a letter!", Toast.LENGTH_SHORT).show();
        }
        mEditLetter.setText("");
    }

    public void checkLetter(String guess, View v){
        TextView debug = (TextView)findViewById(R.id.debugWord);
        debug.setText(mGameWord);
        String display = mGameWord;
        String noDupes = removeDuplicates(guess);
        boolean isCorrect = true;
        // Replace letters with _ if they are not known and update _'s to letters as they are guessed
        for(int i = 0; i < mGameWord.length(); i++){
            Log.d("Letter", "" + mGameWord.charAt(i));
            if(!guessedLetters.contains("" + mGameWord.charAt(i))){
                display = display.replace("" + mGameWord.charAt(i), "_ ");
                isCorrect = false;


            }else{
                display = display.replace("" + mGameWord.charAt(i), "" + mGameWord.charAt(i) + " ");
                isCorrect = true;

            }
        }

        TextView word = (TextView)findViewById(R.id.wordGuessed);
        word.setText(display);

        if(!isCorrect)
            guessFail(v);



        /*
        char introducedLetter = guess.charAt(0);
        boolean letterGuessed = false;

        // Loop through the word, check if you got a letter and fill it in
        for(int i = 0; i < mGameWord.length(); i++){
            char charFromWord = mGameWord.charAt(i);
            if(charFromWord == introducedLetter){
                letterGuessed = true;
                mCorrectGuesses++;
                guessCorrect(i, introducedLetter, v);
            }
        }
        // If you didn't, update the failed letter section
        if(!letterGuessed)
            guessFail(Character.toString(introducedLetter), v);

        if(mCorrectGuesses == mGameWord.length()){
            //TODO HiScores and end game


        }*/

    }
/*
    public void guessCorrect(int pos, char letter, View v){
        LinearLayout layout = (LinearLayout)findViewById(R.id.layoutLetters);
        TextView wordGuessed = (TextView)findViewById(R.id.wordGuessed);
        //letters.setText(Character.toString(letter));
        // TODO figure out how to add to the word
    }
*/
    public void guessFail(View v){

        mFailCounter++;
        ImageView imageView = (ImageView)(findViewById(R.id.imageView));

        if (mFailCounter == 1) {
            imageView.setImageResource(R.drawable.hangdroid_1);
        }else if(mFailCounter == 2) {
            imageView.setImageResource(R.drawable.hangdroid_2);
        }else if(mFailCounter == 3) {
            imageView.setImageResource(R.drawable.hangdroid_3);
        }else if(mFailCounter == 4) {
            imageView.setImageResource(R.drawable.hangdroid_4);
        }else if(mFailCounter == 5) {
            imageView.setImageResource(R.drawable.hangdroid_5);
        }else if(mFailCounter == 6){
            imageView.setImageResource(R.drawable.game_over);
            gameLost();
        }

    }

    public void gameLost(){
        Intent intent = new Intent(this, GameOver.class);
        resetGame();
        startActivity(intent);

    }

    public void resetGame(){
        // Reset the failed letter box and instance variables
        TextView failedText = (TextView)findViewById(usedLetters);
        failedText.setText("");
        mCorrectGuesses = 0;
        mFailCounter = 0;

        LinearLayout letterLayout = (LinearLayout)findViewById(R.id.layoutLetters);
        for(int i = 0; i < letterLayout.getChildCount(); i++){
            TextView currText = (TextView) letterLayout.getChildAt(i);
            currText.setText("");
        }
        ImageView image = (ImageView)findViewById(R.id.imageView);
        image.setImageResource(R.drawable.hangdroid_0);

    }

    /**
     * Method for removing duplicate characters from a string
     *
     * @param s string variable to be operated on
     * @return string value with no duplicate letters
     */

    public static String removeDuplicates(final String s){
        final StringBuilder end = new StringBuilder();
        for(int i = 0; i < s.length(); i++){
            String currChar = s.substring(i, i+1);
            if(end.indexOf(currChar) < 0)
                end.append(currChar);

        }
        return "" + end;
    }

}
