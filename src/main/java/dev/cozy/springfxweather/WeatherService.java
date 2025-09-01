package dev.cozy.springfxweather;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherService {

    private final WebClient webClient;

    public WeatherService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://wttr.in")
                .defaultHeader("Accept", "application/geo+json")
                .defaultHeader("User-Agent", "WeatherApiClient/1.0")
                .build();
    }

    public Mono<WeatherDataResponse> getWeather(String location) {
        return webClient.get()
                .uri("/{location}?format=j1", location) // wttr.in supports ?format=j1 (JSON)
                .retrieve()
                .bodyToMono(String.class) // first, raw JSON
                .map(json -> WeatherMapper.mapJsonToWeatherData(location, json));
    }
}

