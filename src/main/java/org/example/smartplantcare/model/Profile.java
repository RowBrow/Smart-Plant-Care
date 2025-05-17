package org.example.smartplantcare.model;

import java.sql.Date;


/// Represents a profile to be
/// applied to a device
///
/// This class is a `record`, which is a kind of
/// class that is immutable, and comes with a few
/// pre-implemented methods.
public record Profile(int id, String name, String deviceId, Date expiryDate, IdealRanges idealRanges) {
    /// Specifies the ideal ranges
    /// of measurements for a device,
    /// presumably specific to the plant
    /// the device is monitoring
    public record IdealRanges(
            float idealTemperature,
            float minimumTemperature,
            float maximumTemperature,
            float idealHumidity,
            float minimumHumidity,
            float maximumHumidity,
            int idealWater,
            int minimumWater,
            int maximumWater,
            int idealLight,
            int minimumLight,
            int maximumLight
    ) {}
}
