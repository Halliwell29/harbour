package io.github.halliwell29.harbour.assessment;

import io.github.halliwell29.harbour.ingestion.Forecast;

import java.time.Instant;
import java.util.List;

/**
 * One forecast hour, with the operating status it was assessed as.
 */
public record HourCondition(
        Instant forecastTime,
        Double temperatureC,
        Double precipitationMm,
        Double windSpeedKmh,
        Double windGustKmh,
        Double waveHeightM,
        OperatingStatus status,
        List<String> reasons) {

    public static HourCondition from(Forecast forecast, Assessment assessment) {
        return new HourCondition(
                forecast.getForecastTime(),
                forecast.getTemperatureC(),
                forecast.getPrecipitationMm(),
                forecast.getWindSpeedKmh(),
                forecast.getWindGustKmh(),
                forecast.getWaveHeightM(),
                assessment.status(),
                assessment.reasons());
    }
}
