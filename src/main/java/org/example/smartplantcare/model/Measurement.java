package org.example.smartplantcare.model;

/// Represents a measurement made by a device
///
/// This class is a `record`, which is a kind of
/// class that is immutable, and comes with a few
/// pre-implemented methods.
public record Measurement(String deviceId, String timestamp, int light, float temp, int water, float humidity) {}