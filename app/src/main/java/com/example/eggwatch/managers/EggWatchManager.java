package com.example.eggwatch.managers;

import android.os.CountDownTimer;

import com.example.eggwatch.interfaces.IEggWatch;
import com.example.eggwatch.models.EggModel;

public class EggWatchManager implements IEggWatch {
    // Define a count down timer.
    private CountDownTimer countDownTimer;

    // The time left of the eggs cooking.
    private long timeLeftForEggWatch;

    // The timer for when they count down timer is updated.
    private long eggWatchUpdateSpeed = 1000;

    // State of the egg watch.
    private boolean isEggWatchStarted = false;

    // Egg Watch Constructor, which takes an Egg Model to provide the right values.
    public EggWatchManager(EggModel eggModel) {
        setTimeLeftForEggWatch(eggModel.getCookTime());
    }

    // Start egg watch.
    public void startEggWatch() {
        // Initializing a new count down timer with the egg's time left on the cooking and the update speed.
        countDownTimer = new CountDownTimer(timeLeftForEggWatch, eggWatchUpdateSpeed) {
            public void onTick(long millisUntilFinished) {
                // Set the time left of the egg's cooking time.
                setTimeLeftForEggWatch(millisUntilFinished);
            }
            public void onFinish() {
                // Set the egg watch state to false.
                setEggWatchStarted(false);
            }
        };

        // Set the time state to started.
        setEggWatchStarted(true);

        // Start the countdown timer.
        countDownTimer.start();
    }

    // Stop egg watch.
    public void stopEggWatch() {
        // Set the time state to started.
        setEggWatchStarted(false);

        // Stops / Cancels the countdown timer.
        countDownTimer.cancel();
    }

    // Gets the time left of the cooking in millis.
    public long getTimeLeftForEggWatch() {
        return timeLeftForEggWatch;
    }

    // Sets the time left of the cooking in millis.
    private void setTimeLeftForEggWatch(Long timeLeftForEggWatch) {
        this.timeLeftForEggWatch = timeLeftForEggWatch;
    }

    // Sets the update speed of the countdown timer.
    private void setEggWatchUpdateSpeed(long eggWatchUpdateSpeed) {
        this.eggWatchUpdateSpeed = eggWatchUpdateSpeed;
    }

    // Returns the state of egg watch.
    public boolean isEggWatchStarted() {
        return isEggWatchStarted;
    }

    // Sets the state of the egg watch.
    private void setEggWatchStarted(boolean eggWatchStarted) {
        isEggWatchStarted = eggWatchStarted;
    }
}
