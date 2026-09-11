package io.github.halliwell29.harbour.health;

import java.time.Instant;

public record HealthResponse(String status, String service, Instant timestamp) { }
