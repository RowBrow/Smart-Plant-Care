package org.example.smartplantcare.model;

public record Measurement(String deviceId, String timestamp, int light, float temp, int water, float humidity) {}