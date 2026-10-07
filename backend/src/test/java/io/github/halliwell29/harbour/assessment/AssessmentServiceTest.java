package io.github.halliwell29.harbour.assessment;

import io.github.halliwell29.harbour.ingestion.Forecast;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.Instant;

public class AssessmentServiceTest {

    private final AssessmentService service = new AssessmentService(new OperatingLimits(30, 40, 1.5, -15));

    @Test
    void goodWhenAllMeasurementsWellWithinLimits(){
        Forecast forecast = forecastWith(15.0, 10.0, 20.0);

        Assessment result = service.assess(forecast);

        assertThat(result.status()).isEqualTo(OperatingStatus.GOOD);
        assertThat(result.reasons()).isEmpty();
    }

    private Forecast forecastWith(Double temperatureC, Double windSpeedKmh, Double windGustKmh){
        return new Forecast(null, Instant.now(), "open-meteo", temperatureC, 0.0, windSpeedKmh, windGustKmh, null);
    }

}
