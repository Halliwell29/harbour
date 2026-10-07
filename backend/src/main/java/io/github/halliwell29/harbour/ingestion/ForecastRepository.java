package io.github.halliwell29.harbour.ingestion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface ForecastRepository extends JpaRepository<Forecast, Long> {
    List<Forecast> findBySiteIdAndForecastTimeGreaterThanEqualOrderByForecastTimeAsc(Long siteId, Instant from);
    void deleteBySiteIdAndProvider(Long siteId, String provider);
}
