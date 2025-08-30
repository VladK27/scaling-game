CREATE TABLE device_events (
    event_id BIGSERIAL PRIMARY KEY,
    event_time TIMESTAMPTZ NOT NULL,
    battery_level INT NOT NULL CHECK (battery_level BETWEEN 0 AND 100),
    city VARCHAR(128) NOT NULL,
    country TEXT NOT NULL,
    temperature REAL NOT NULL,
    wind_speed REAL NOT NULL,
    weather VARCHAR(20) NOT NULL
);