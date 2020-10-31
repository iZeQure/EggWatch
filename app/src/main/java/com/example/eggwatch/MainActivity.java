package com.example.eggwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eggwatch.managers.EggWatchManager;
import com.example.eggwatch.models.EggModel;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    // Define strings that contains final values for the start / stop button.
    private final String START_EGG_TEXT = "start æggeur";
    private final String STOP_EGG_TEXT = "stop æggeur";

    // Define the Egg Watch Manager object.
    EggWatchManager eggWatchManager;
    // Define the Egg Model Object.
    EggModel egg;

    // Initialize a new calendar, used to set the date time in millis.
    Calendar calendar = Calendar.getInstance();;
    // Initialize a new Date Format object, used to format the date in millis to readable minutes / seconds.
    DateFormat dateFormat = new SimpleDateFormat("mm:ss");;

    // Define a new runnable thread. - Used for testing, as it's not working.
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Create an onClick listener, for when the egg option is clicked.
    public void onClick_EggSelectedEvent(View view) {
        // Switch on the view id's to get the right view.
        switch (view.getId()) {
            case R.id.eggOptionOneButton:
                // Create a new Egg Model for option one.
                egg = new EggModel("Blødkogt", 300000);
                break;
            case R.id.eggOptionTwoButton:
                // Create a new Egg Model for option two.
                egg = new EggModel("Smilende", 420000);
                break;
            case R.id.eggOptionThreeButton:
                // Create a new Egg Model for option three.
                egg = new EggModel("Hårdkogt", 600000);
                break;

            // Throw exception if they weren't found.
            default:
                throw new RuntimeException("Unknown Egg Option");
        }

        // Initialize the egg watch manager with the created egg model.
        eggWatchManager = new EggWatchManager(egg);

        // Initialize a new runnable thread, used to set the time left text in the view.
        // Currently isn't working. - View is either freezing or nothing happens.
        runnable = new Runnable() {
            @Override
            public void run() {
                /*while (eggWatchManager.isEggWatchStarted()) {
                    System.out.println("Cooking timer : " + eggWatchManager.getTimeLeftForEggWatch());
                }*/
                    setEggWatchCookingTimeLeft(eggWatchManager.getTimeLeftForEggWatch());

                /*if(eggWatchManager.getTimeLeftForEggWatch() < 0 && eggWatchManager.isEggWatchStarted()) {
                    setEggWatchCookingTimeLeft(eggWatchManager.getTimeLeftForEggWatch());
                }*/
            }
        };

        // Call the set egg watch text view.
        setEggWatchTextView();

        // Enable the start / stop button for the egg watch.
        enableEggWatchStateButton();
    }

    // Sets the time left from the cooking into minutes / seconds.
    private void setEggWatchCookingTimeLeft(long timeLeft) {
        // Sets the time in millis with the current time left from the cooking.
        calendar.setTimeInMillis(timeLeft);
        ((TextView)findViewById(R.id.eggTextTimer)).setText(dateFormat.format(calendar.getTime()));
    }

    // Sets the default timer of the egg to cook.
    private void setEggWatchTextView() {
        // Sets the time in millis from the created egg models time.
        calendar.setTimeInMillis(egg.getCookTime());
        ((TextView)findViewById(R.id.eggTextTimer)).setText(dateFormat.format(calendar.getTime()));
    }

    // Create onClick listener method, for when the start / stop button is clicked.
    public void onClick_eggWatchStateButton(View view) {
        // Check the state of the egg watch.
        if (!eggWatchManager.isEggWatchStarted())
            eggWatchManager.startEggWatch();
        else
            eggWatchManager.stopEggWatch();

        // Set the buttons text.
        setEggWatchStateButtonText();

        // Set the egg options.
        setEggOptionsState();

        // Run the thread. - Is not working.
        runnable.run();
    }

    // Sets the Start / Stop buttons text.
    private void setEggWatchStateButtonText() {
        // Checks the state of the watch, to set the text.
        if (eggWatchManager.isEggWatchStarted())
            ((Button)findViewById(R.id.eggWatchStateButton)).setText(STOP_EGG_TEXT);
        else
            ((Button)findViewById(R.id.eggWatchStateButton)).setText(START_EGG_TEXT);
    }

    // Sets the egg options.
    private void setEggOptionsState() {
        // Disables or Enables the options for whether the egg watch state is T / F.
        if (eggWatchManager.isEggWatchStarted()) {
            ((Button)findViewById(R.id.eggOptionOneButton)).setEnabled(false);
            ((Button)findViewById(R.id.eggOptionTwoButton)).setEnabled(false);
            ((Button)findViewById(R.id.eggOptionThreeButton)).setEnabled(false);
        }
        else {
            ((Button)findViewById(R.id.eggOptionOneButton)).setEnabled(true);
            ((Button)findViewById(R.id.eggOptionTwoButton)).setEnabled(true);
            ((Button)findViewById(R.id.eggOptionThreeButton)).setEnabled(true);
        }
    }

    // Enables the Start / Stop button.
    private void enableEggWatchStateButton() {
        ((Button)findViewById(R.id.eggWatchStateButton)).setEnabled(true);
    }
}