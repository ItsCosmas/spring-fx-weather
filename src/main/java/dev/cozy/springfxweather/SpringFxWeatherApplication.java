package dev.cozy.springfxweather;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringFxWeatherApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(SpringFxWeatherApplication.class)
                .headless(false) // allows GUI
                .run(args);

        WeatherApp.setSpringContext(context);
        javafx.application.Application.launch(WeatherApp.class, args);
    }

}
