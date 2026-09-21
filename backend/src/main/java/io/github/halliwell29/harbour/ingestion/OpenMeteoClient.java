package io.github.halliwell29.harbour.ingestion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
public class OpenMeteoClient {
    private static final String HOURLY_FIELDS = "temperature_2m,precipitation,wind_speed_10m,wind_gusts_10m";
    private final RestClient restClient;

    public OpenMeteoClient(RestClient.Builder builder, @Value("${harbour.open-meteo.weather-url}") String baseUrl) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(5));
        factory.setReadTimeout(Duration.ofSeconds(10));
        this.restClient = builder
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .build();
    }

    public OpenMeteoWeatherResponse fetchWeather(double latitude, double longitude){
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/forecast")
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam("hourly", HOURLY_FIELDS)
                        .queryParam("timezone", "UTC")
                        .queryParam("forecast_days", 2)
                        .build())
                .retrieve()
                .body(OpenMeteoWeatherResponse.class);
    }

}
