package io.github.halliwell29.harbour.assessment;

import io.github.halliwell29.harbour.site.SiteResponse;

import java.time.Instant;
import java.util.List;

/**
 * Everything the dashboard needs for one site: the site itself, the worst status
 * in the forecast window, the current hour, every upcoming hour, and the longest
 * run of GOOD hours (the best operating window).
 */
public record ConditionsResponse(
        SiteResponse site,
        OperatingStatus status,
        HourCondition current,
        List<HourCondition> hours,
        Instant bestWindowStart,
        Instant bestWindowEnd) {
}
