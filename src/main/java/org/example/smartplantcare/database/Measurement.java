package org.example.smartplantcare.database;

public class Measurement {
    private String datetime;
    private float light;
    private float temp;
    private float water;
    private float humidity;

    public Measurement() {

    }

    public Measurement(String datetime, float light, float temp, float water, float humidity) {
        this.datetime = datetime;
        this.light = light;
        this.temp = temp;
        this.water = water;
        this.humidity = humidity;
    }

    public String getDatetime() {
        return datetime;
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

