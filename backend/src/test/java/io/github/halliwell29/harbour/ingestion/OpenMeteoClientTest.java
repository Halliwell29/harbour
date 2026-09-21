package io.github.halliwell29.harbour.ingestion;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import static org.assertj.core.api.Assertions.assertThat;

class OpenMeteoClientTest {
    @Test
    void fetchesFortyEightHourlyEntriesForMalpequeBay() {
        OpenMeteoClient client = new OpenMeteoClient(RestClient.builder(), "https://api.open-meteo.com");

        OpenMeteoWeatherResponse response = client.fetchWeather(46.5333, -63.6667);

        assertThat(response.hourly().time()).hasSize(48);
        assertThat(response.hourly().temperatureC()).hasSize(48);
        assertThat(response.hourly().windSpeedKmh().get(0)).isNotNull();
        assertThat(response.hourly().windGustKmh().get(0)).isNotNull();
    }

}