package io.github.halliwell29.harbour.ingestion;

import io.github.halliwell29.harbour.site.Site;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.Instant;

@Entity
public class Forecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    private String provider;
    private Instant forecastTime;
    private Instant fetchedAt;
    private Double temperatureC;
    private Double precipitationMm;
    private Double windSpeedKmh;
    private Double windGustKmh;
    private Double waveHeightM;

    public Forecast(Site site, Instant forecastTime, String provider,
                    Double temperatureC, Double precipitationMm,
                    Double windSpeedKmh, Double windGustKmh, Double waveHeightM){

        this.site = site;
        this.forecastTime = forecastTime;
        this.provider = provider;
        this.temperatureC = temperatureC;
        this.precipitationMm = precipitationMm;
        this.windSpeedKmh = windSpeedKmh;
        this.windGustKmh = windGustKmh;
        this.waveHeightM = waveHeightM;
        this.fetchedAt = Instant.now();
    }

    protected Forecast() { }

    /** GETTERS **/

    public Site getSite() {
        return site;
    }

    public String getProvider(){
        return provider;
    }

    public Instant getForecastTime(){
        return forecastTime;
    }

    public Instant getFetchedAt(){
        return fetchedAt;
    }

    public Double getTemperatureC() {
        return temperatureC;
    }

    public Double getPrecipitationMm(){
        return precipitationMm;
    }

    public Double getWindSpeedKmh(){
        return windSpeedKmh;
    }

    public Double getWindGustKmh(){
        return windGustKmh;
    }

    public Double getWaveHeightM(){
        return waveHeightM;
    }


}
