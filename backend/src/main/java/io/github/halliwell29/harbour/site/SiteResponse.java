package io.github.halliwell29.harbour.site;

import java.time.Instant;

public record SiteResponse (Long id, String name, double latitude, double longitude, String description, Instant createdAt){
    public static SiteResponse from(Site site){
        return new SiteResponse(site.getId(),  site.getName(), site.getLatitude(), site.getLongitude(), site.getDescription(), site.getCreatedAt());
    }
}
