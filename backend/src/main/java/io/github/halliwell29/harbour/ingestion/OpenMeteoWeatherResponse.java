package io.github.halliwell29.harbour.ingestion;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenMeteoWeatherResponse(double latitude, double longitude, Hourly hourly) {
    public record Hourly(
            List<String> time,
            @JsonProperty("temperature_2m")
            List<Double> temperatureC,
            @JsonProperty("precipitation")
            List<Double> precipitationMm,
            @JsonProperty("wind_speed_10m")
            List<Double> windSpeedKmh,
            @JsonProperty("wind_gusts_10m")
            List<Double> windGustKmh) { }
}
