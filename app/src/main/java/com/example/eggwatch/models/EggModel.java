package com.example.eggwatch.models;

public class EggModel {
    // The name of the Egg.
    private String eggName;

    // The cooking time of the Egg in Millis.
    private long cookTime;

    // Egg Model constructor, which creates an Egg with a name and a cooking time in millis.
    public EggModel(String eggName, long cookTime) {
        setEggName(eggName);
        setCookTime(cookTime);
    }

    // Returns the Egg Name.
    public String getEggName() {
        return eggName;
    }

    // Sets the Egg Name.
    private void setEggName(String eggName) {
        this.eggName = eggName;
    }

    // Returns the cooking time in millis.
    public long getCookTime() {
        return cookTime;
    }

    // Sets the cooking time in millis.
    private void setCookTime(long cookTime) {
        this.cookTime = cookTime;
    }
}
