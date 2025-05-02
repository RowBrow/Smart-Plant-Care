package org.example.smartplantcare.database;

public class Measurement {
    private final String deviceId;
    private final String timestamp;
    private final int light;
    private final float temp;
    private final int water;
    private final float humidity;

    public Measurement(String deviceId, String timestamp, int light, float temp, int water, float humidity) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.light = light;
        this.temp = temp;
        this.water = water;
        this.humidity = humidity;
    }

    public String getDatetime() {
        return timestamp;
    }

    public int getLight() {
        return light;
    }

    public float getTemp() {
        return temp;
    }

    public int getWater() {
        return water;
    }

    public float getHumidity() {
        return humidity;
    }

    public String getDeviceId() {
        return deviceId;
    }
}

