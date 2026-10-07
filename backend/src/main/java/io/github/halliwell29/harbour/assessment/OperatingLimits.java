package io.github.halliwell29.harbour.assessment;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "harbour.limits")
public record OperatingLimits(
        double maxWindKmh,
        double maxGustKmh,
        double maxWaveHeightM,
        double minTemperatureC
) {
}
