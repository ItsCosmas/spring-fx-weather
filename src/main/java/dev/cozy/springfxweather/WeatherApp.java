package dev.cozy.springfxweather;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class WeatherApp extends Application {
    private static ConfigurableApplicationContext springContext;
    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WeatherApp.class.getResource("/weather-view.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Nimbus Weather!");
        stage.setScene(scene);
        stage.show();
    }

    public static void setSpringContext(ConfigurableApplicationContext context) {
        springContext = context;
    }
}
