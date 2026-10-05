CREATE TABLE forecast (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    site_id BIGINT NOT NULL REFERENCES site(id) ON DELETE CASCADE,
    forecast_time TIMESTAMPTZ NOT NULL,
    provider TEXT NOT NULL,
    temperature_c DOUBLE PRECISION,
    precipitation_mm DOUBLE PRECISION,
    wind_speed_kmh DOUBLE PRECISION,
    wind_gust_kmh DOUBLE PRECISION,
    wave_height_m DOUBLE PRECISION,
    fetched_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    UNIQUE (site_id, forecast_time, provider)
);