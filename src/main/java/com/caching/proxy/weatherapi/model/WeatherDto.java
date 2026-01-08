package com.caching.proxy.weatherapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherDto {

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("generationtime_ms")
    private double generationTimeMs;

    @JsonProperty("utc_offset_seconds")
    private int utcOffsetSeconds;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("timezone_abbreviation")
    private String timezoneAbbreviation;

    @JsonProperty("elevation")
    private double elevation;

    @JsonProperty("current_weather_units")
    private CurrentWeatherUnits currentWeatherUnits;

    @JsonProperty("current_weather")
    private CurrentWeather currentWeather;

    @Data
    private class CurrentWeatherUnits {

        @JsonProperty("time")
        private String time;

        @JsonProperty("interval")
        private String interval;

        @JsonProperty("temperature")
        private String temperature;

        @JsonProperty("windspeed")
        private String windspeed;

        @JsonProperty("winddirection")
        private String winddirection;

        @JsonProperty("is_day")
        private String isDay;

        @JsonProperty("weathercode")
        private String weathercode;

    }

    @Data
    private class CurrentWeather {

        // Getters and Setters
        @JsonProperty("time")
        private String time;

        @JsonProperty("interval")
        private int interval;

        @JsonProperty("temperature")
        private double temperature;

        @JsonProperty("windspeed")
        private double windspeed;

        @JsonProperty("winddirection")
        private int winddirection;

        @JsonProperty("is_day")
        private int isDay;

        @JsonProperty("weathercode")
        private int weathercode;

    }
}


