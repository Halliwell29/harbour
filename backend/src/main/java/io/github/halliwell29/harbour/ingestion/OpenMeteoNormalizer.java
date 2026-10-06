package io.github.halliwell29.harbour.ingestion;

import io.github.halliwell29.harbour.site.Site;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Component
public class OpenMeteoNormalizer {

    private static final String PROVIDER = "open-meteo";

    public List<Forecast> toForecasts(Site site, OpenMeteoWeatherResponse response){

        OpenMeteoWeatherResponse.Hourly hourly = response.hourly();
        int hours = hourly.time().size();

        if (hourly.temperatureC().size() != hours || hourly.precipitationMm().size() != hours
                || hourly.windSpeedKmh().size() != hours || hourly.windGustKmh().size() != hours){
            throw new IllegalStateException("Open-Meteo returned mismatched array lengths for site " + site.getName());
        }

        List<Forecast> forecasts = new ArrayList<>();

        for (int i = 0; i < hours; i++){
            Instant forecastTime = LocalDateTime.parse(hourly.time().get(i)).toInstant(ZoneOffset.UTC);

            forecasts.add(new Forecast(
                    site,
                    forecastTime,
                    PROVIDER,
                    hourly.temperatureC().get(i),
                    hourly.precipitationMm().get(i),
                    hourly.windSpeedKmh().get(i),
                    hourly.windGustKmh().get(i),
                    null
            ));
        }

        return forecasts;
    }

}
