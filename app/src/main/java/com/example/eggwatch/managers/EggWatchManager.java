package com.example.eggwatch.managers;

import android.os.CountDownTimer;

import com.example.eggwatch.models.EggModel;

public class EggWatchManager{
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
        // Initializing a new count down timer with the egg's cook time and the update speed.
        countDownTimer = new CountDownTimer(eggModel.getCookTime(), eggWatchUpdateSpeed) {
            public void onTick(long millisUntilFinished) {
                // Set the time left of the egg's cooking time.
                setTimeLeftForEggWatch(millisUntilFinished);
            }
            public void onFinish() {
                // Set the egg watch state to false.
                setEggWatchStarted(false);
            }
        };
    }

    // Start egg watch.
    public void startEggWatch() {
        setEggWatchStarted(true);
        countDownTimer.start();
    }

    // Stop egg watch.
    public void stopEggWatch() {
        setEggWatchStarted(false);
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
