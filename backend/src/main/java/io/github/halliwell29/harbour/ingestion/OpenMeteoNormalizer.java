package io.github.halliwell29.harbour.ingestion;

import io.github.halliwell29.harbour.site.Site;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpenMeteoNormalizer {

    private static final String PROVIDER = "open-meteo";

    public List<Forecast> toForecasts(Site site, OpenMeteoWeatherResponse response){

        return List.of();
    }

}
