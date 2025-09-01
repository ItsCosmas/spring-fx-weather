package dev.cozy.springfxweather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherMapper {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static WeatherDataResponse mapJsonToWeatherData(String location, String json) {
        try {
            JsonNode root = mapper.readTree(json);

            JsonNode current = root.path("current_condition").get(0);

            String description = current.path("weatherDesc").get(0).path("value").asText();
            String tempC = current.path("temp_C").asText();
            String tempF = current.path("temp_F").asText();
            String humidity = current.path("humidity").asText() + " %";
            String wind = current.path("windspeedKmph").asText() + " km/h";

            return new WeatherDataResponse(
                    location,
                    description,
                    tempC,
                    tempF,
                    humidity,
                    wind
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse weather JSON", e);
        }
    }
}

