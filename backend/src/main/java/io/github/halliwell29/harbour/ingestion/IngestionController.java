package io.github.halliwell29.harbour.ingestion;

import io.github.halliwell29.harbour.site.Site;
import io.github.halliwell29.harbour.site.SiteService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngestionController {

    private final SiteService siteService;
    private final ForecastIngestionService forecastIngestionService;

    public IngestionController(SiteService siteService, ForecastIngestionService forecastIngestionService){
        this.siteService = siteService;
        this.forecastIngestionService = forecastIngestionService;
    }

    @PostMapping("/api/sites/{siteId}/ingest")
    public IngestResponse ingest(@PathVariable Long siteId){
        Site site = siteService.findById(siteId);
        int count = forecastIngestionService.ingest(site);
        return new IngestResponse(siteId, count);
    }

}
