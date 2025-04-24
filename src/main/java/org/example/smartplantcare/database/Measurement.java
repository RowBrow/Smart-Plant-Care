package org.example.smartplantcare.database;

public class Measurement {
    private final String timestamp;
    private final float light;
    private final float temp;
    private final float water;
    private final float humidity;

    public Measurement(String timestamp, float light, float temp, float water, float humidity) {
        this.timestamp = timestamp;
        this.light = light;
        this.temp = temp;
        this.water = water;
        this.humidity = humidity;
    }

    public String getDatetime() {
        return timestamp;
    }

    public float getLight() {
        return light;
    }

    public float getTemp() {
        return temp;
    }

    public float getWater() {
        return water;
    }

    public float getHumidity() {
        return humidity;
    }

    // Optional: add getters, setters, toString(), etc.
}

