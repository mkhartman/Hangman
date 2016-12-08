package matt.com.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Matt on 12/6/2016.
 */

public class GameOver extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gameover);

        TextView numFails = (TextView) findViewById(R.id.numFails);
        Bundle extras = getIntent().getExtras();
        String fails = extras.getString("myKey");
        String word = extras.getString("wordGuessed");
        numFails.setText("Word was: " + word + "\nNumber of incorrect guesses: " +fails);
    }


    public void startSPGame(View v){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void exitApp(View v){
        this.finishAffinity();
    }

}
