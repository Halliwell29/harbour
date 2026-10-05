package io.github.halliwell29.harbour.ingestion;

import io.github.halliwell29.harbour.site.Site;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ForecastIngestionService {

    private final OpenMeteoClient openMeteoClient;
    private final OpenMeteoNormalizer openMeteoNormalizer;
    private final ForecastRepository forecastRepository;

    public ForecastIngestionService(OpenMeteoClient openMeteoClient, OpenMeteoNormalizer openMeteoNormalizer, ForecastRepository forecastRepository){
        this.openMeteoClient = openMeteoClient;
        this.openMeteoNormalizer = openMeteoNormalizer;
        this.forecastRepository = forecastRepository;
    }

    @Transactional
    public int ingest(Site site){
        OpenMeteoWeatherResponse response = openMeteoClient.fetchWeather(site.getLatitude(), site.getLongitude());
        List<Forecast> forecasts = openMeteoNormalizer.toForecasts(site, response);
        forecasts = forecastRepository.saveAll(forecasts);
        return forecasts.size();
    }

}
