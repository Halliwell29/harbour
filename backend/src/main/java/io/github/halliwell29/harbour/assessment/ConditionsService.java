package io.github.halliwell29.harbour.assessment;

import io.github.halliwell29.harbour.ingestion.Forecast;
import io.github.halliwell29.harbour.ingestion.ForecastRepository;
import io.github.halliwell29.harbour.site.Site;
import io.github.halliwell29.harbour.site.SiteResponse;
import io.github.halliwell29.harbour.site.SiteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Service
public class ConditionsService {

    private static final int HOURS_AHEAD = 24;

    private final OperatingLimits operatingLimits;
    private final SiteService siteService;
    private final ForecastRepository forecastRepository;
    private final AssessmentService assessmentService;

    public ConditionsService(OperatingLimits operatingLimits,
                             SiteService siteService,
                             ForecastRepository forecastRepository,
                             AssessmentService assessmentService) {
        this.operatingLimits = operatingLimits;
        this.siteService = siteService;
        this.forecastRepository = forecastRepository;
        this.assessmentService = assessmentService;
    }

    @Transactional(readOnly = true)
    public ConditionsResponse conditionsFor(Long siteId) {
        Site site = siteService.findById(siteId);

        List<Forecast> forecasts = forecastRepository
                .findBySiteIdAndForecastTimeGreaterThanEqualOrderByForecastTimeAsc(siteId, Instant.now())
                .stream()
                .limit(HOURS_AHEAD)
                .toList();

        List<HourCondition> hours = forecasts.stream()
                .map(forecast -> HourCondition.from(forecast, assessmentService.assess(forecast)))
                .toList();

        OperatingStatus worstStatus = hours.stream()
                .map(HourCondition::status)
                .max(Comparator.naturalOrder())
                .orElse(OperatingStatus.GOOD);

        HourCondition current = hours.isEmpty() ? null : hours.getFirst();
        Window bestWindow = longestGoodRun(hours);

        return new ConditionsResponse(
                SiteResponse.from(site),
                operatingLimits,
                worstStatus,
                current,
                hours,
                bestWindow.start(),
                bestWindow.end());
    }

    /**
     * Finds the longest unbroken run of GOOD hours. Returns an empty window when
     * every hour carries at least a caution.
     */
    private Window longestGoodRun(List<HourCondition> hours) {
        int bestStart = -1;
        int bestLength = 0;
        int runStart = -1;
        int runLength = 0;

        for (int i = 0; i < hours.size(); i++) {
            if (hours.get(i).status() == OperatingStatus.GOOD) {
                if (runLength == 0) {
                    runStart = i;
                }
                runLength++;
                if (runLength > bestLength) {
                    bestLength = runLength;
                    bestStart = runStart;
                }
            } else {
                runLength = 0;
            }
        }

        if (bestLength == 0) {
            return new Window(null, null);
        }

        Instant start = hours.get(bestStart).forecastTime();
        Instant end = hours.get(bestStart + bestLength - 1).forecastTime().plus(Duration.ofHours(1));
        return new Window(start, end);
    }

    private record Window(Instant start, Instant end) {
    }
}
