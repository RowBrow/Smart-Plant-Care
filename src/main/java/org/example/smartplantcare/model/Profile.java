package org.example.smartplantcare.model;

import java.sql.Date;

public record Profile(int id, String name, String deviceId, Date expiryDate, IdealRanges idealRanges) {
    /// Specifies the ideal ranges
    /// of measurements for a device,
    /// presumably specific to the plant
    /// the device is monitoring
    public record IdealRanges(
            float minimumTemperature, float maximumTemperature,
            float minimumHumidity, float maximumHumidity,
            int minimumWater, int maximumWater,
            int minimumLight, int maximumLight
    ) {}
}
