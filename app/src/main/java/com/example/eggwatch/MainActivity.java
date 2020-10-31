package com.example.eggwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eggwatch.interfaces.IEggWatch;
import com.example.eggwatch.managers.EggWatchManager;
import com.example.eggwatch.models.EggModel;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements Runnable, IEggWatch {
    // Define strings that contains final values for the start / stop button.
    private final String START_EGG_TEXT = "start æggeur";
    private final String STOP_EGG_TEXT = "stop æggeur";

    // Define delay for when text view is updated.
    private final long UPDATE_TEXTVIEW_DELAY = 1000;

    // Define the Egg Watch Manager object.
    private EggWatchManager eggWatchManager;
    // Define the Egg Model Object.
    private EggModel egg;

    // Initialize a new calendar, used to set the date time in millis.
    private Calendar calendar = Calendar.getInstance();;
    // Initialize a new Date Format object, used to format the date in millis to readable minutes / seconds.
    private DateFormat dateFormat = new SimpleDateFormat("mm:ss");;

    // Define a new runnable thread. - Used for testing, as it's not working.
    private Runnable runnable;
    private Handler updateTextViewHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // A new thread that handles the Egg Time Text View with the time remaining of the egg cooking.
    @Override
    public void run() {
        // set the current time left of cooking for the egg.
        setEggWatchCookingTimeLeft(eggWatchManager.getTimeLeftForEggWatch());

        // Check the state and timer of the egg watch.
        if (eggWatchManager.isEggWatchStarted() && eggWatchManager.getTimeLeftForEggWatch() >= 0) {
            // Continue running method.
            updateTextViewHandler.postDelayed(this ,UPDATE_TEXTVIEW_DELAY);
        }
        else {
            resetEggApp();
        }
    }

    // Create an onClick listener, for when the egg option is clicked.
    public void onClick_EggSelectedEvent(View view) {
        // Switch on the view id's to get the right view.
        switch (view.getId()) {
            case R.id.eggOptionOneButton:
                // Create a new Egg Model for option one.
                egg = new EggModel("Blødkogt", 30000);
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

        // Call the set egg watch text view.
        setEggWatchTextView();

        // Enable the start / stop button for the egg watch.
        enableEggWatchStateButton();
    }

    // Create onClick listener method, for when the start / stop button is clicked.
    public void onClick_eggWatchStateButton(View view) {
        // Check the state of the egg watch.
        if (!eggWatchManager.isEggWatchStarted())
            // Start egg watch.
            startEggWatch();
        else
            // Stop egg watch.
            stopEggWatch();

        // Set the buttons text.
        setEggWatchStateButtonText();

        // Set the egg options.
        setEggOptionsState();

        // Run new thread on updating the Text View egg timer.
        run();
    }

    // Start the Egg Watch.
    public void startEggWatch() {
        eggWatchManager.startEggWatch();
    }

    // Stop the Egg Watch.
    public void stopEggWatch() {
        eggWatchManager.stopEggWatch();
    }

    // Sets the time left from the cooking into minutes / seconds.
    private void setEggWatchCookingTimeLeft(long timeLeft) {
        // Sets the time in millis with the current time left from the cooking.
        calendar.setTimeInMillis(timeLeft);

        // Convert the calendar time to a readable datetime format.
        ((TextView)findViewById(R.id.eggTextTimer)).setText(dateFormat.format(calendar.getTime()));
    }

    // Sets the default timer of the egg to cook.
    private void setEggWatchTextView() {
        // Sets the time in millis from the created egg models time.
        calendar.setTimeInMillis(egg.getCookTime());

        // Convert the calendar time to a readable datetime format.
        ((TextView)findViewById(R.id.eggTextTimer)).setText(dateFormat.format(calendar.getTime()));
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

    // Disables the Start / Stop button.
    private void disableEggWatchStateButton() {
        ((Button)findViewById(R.id.eggWatchStateButton)).setEnabled(false);
    }

    // Resets the app to default state.
    private void resetEggApp() {
        // Stop egg watch.
        stopEggWatch();
        // Set the text after it's done.
        setEggWatchStateButtonText();
        // Disables the state button if timer is done.
        disableEggWatchStateButton();
        // Set the egg options state.
        setEggOptionsState();
    }
}