package io.github.halliwell29.harbour.assessment;

import io.github.halliwell29.harbour.ingestion.Forecast;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssessmentService {

    private final OperatingLimits operatingLimits;

    private static final double CAUTION_FRACTION = 0.8;
    private static final double CAUTION_TEMP_MARGIN_C = 3.0;

    public AssessmentService(OperatingLimits operatingLimits){
        this.operatingLimits = operatingLimits;
    }

    public Assessment assess(Forecast forecast) {
        List<String> reasons = new ArrayList<>();
        OperatingStatus status = OperatingStatus.GOOD;

        Double gust = forecast.getWindGustKmh();
        if (gust != null) {
            if (gust > operatingLimits.maxGustKmh()) {
                status = worst(status, OperatingStatus.UNSAFE);
                reasons.add("Gust %.1f km/h exceeds limit of %.0f km/h"
                        .formatted(gust, operatingLimits.maxGustKmh()));
            } else if (gust >= operatingLimits.maxGustKmh() * CAUTION_FRACTION) {
                status = worst(status, OperatingStatus.CAUTION);
                reasons.add("Gust %.1f km/h approaching limit of %.0f km/h"
                        .formatted(gust, operatingLimits.maxGustKmh()));
            }
        }

        Double wind = forecast.getWindSpeedKmh();
        if (wind != null){
            if (wind > operatingLimits.maxWindKmh()){
                status = worst(status, OperatingStatus.UNSAFE);
                reasons.add("Wind %.1f km/h exceeds limit of %.0f km/h"
                        .formatted(wind, operatingLimits.maxWindKmh()));
            } else if (wind >= operatingLimits.maxWindKmh() * CAUTION_FRACTION){
                status = worst(status, OperatingStatus.CAUTION);
                reasons.add("Wind %.1f km/h approaching limit of %.0f km/h"
                        .formatted(wind, operatingLimits.maxWindKmh()));
            }
        }

        Double wave = forecast.getWaveHeightM();
        if (wave != null){
            if (wave > operatingLimits.maxWaveHeightM()){
                status = worst(status, OperatingStatus.UNSAFE);
                reasons.add("Wave height %.1f m exceeds limit of %.1f m"
                        .formatted(wave, operatingLimits.maxWaveHeightM()));
            } else if (wave >= operatingLimits.maxWaveHeightM() * CAUTION_FRACTION){
                status = worst(status, OperatingStatus.CAUTION);
                reasons.add("Wave height %.1f m approaching limit of %.1f m"
                        .formatted(wave, operatingLimits.maxWaveHeightM()));
            }
        }

        Double temperature = forecast.getTemperatureC();
        if (temperature != null){
            if (temperature < operatingLimits.minTemperatureC()){
                status = worst(status, OperatingStatus.UNSAFE);
                reasons.add("Temperature %.1f °C is below minimum of %.0f °C"
                        .formatted(temperature, operatingLimits.minTemperatureC()));
            } else if (temperature <= operatingLimits.minTemperatureC() + CAUTION_TEMP_MARGIN_C){
                status = worst(status, OperatingStatus.CAUTION);
                reasons.add("Temperature %.1f °C approaching minimum of %.0f °C"
                        .formatted(temperature, operatingLimits.minTemperatureC()));
            }
        }

        return new Assessment(status, reasons);
    }

    private static OperatingStatus worst(OperatingStatus a, OperatingStatus b) {
        return a.compareTo(b) >= 0 ? a :b;
    }
}
