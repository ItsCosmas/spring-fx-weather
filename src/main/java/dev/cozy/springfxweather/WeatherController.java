package dev.cozy.springfxweather;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class WeatherController {

    @FXML private Label locationLabel;
    @FXML private Label conditionLabel;
    @FXML private Label temperatureLabel;
    @FXML private Label windLabel;
    @FXML private Label humidityLabel;


    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    private String currentLocation;

    @FXML
    private void initialize() {
        this.currentLocation = "Nairobi";
        // Default to Nairobi on startup
        fetchAndUpdateWeather(currentLocation);
    }

    @FXML
    private void onRefreshClick() {
        System.out.println("Refresh button clicked");

        if (Optional.ofNullable(currentLocation).orElse("").isBlank()) {
            System.out.println("No location set to refresh.");
            return;
        }
        System.out.println("Refreshing weather for " + currentLocation);
        fetchAndUpdateWeather(currentLocation);
    }

    @FXML
    private void onExitClick() {
        System.out.println("Exit button clicked");
        Platform.exit();   // Cleanly shuts down the JavaFX application
        System.exit(0);    // Ensures JVM terminates
    }

    // Open Settings dialog
    @FXML
    private void onSettingsClick() throws IOException {
        System.out.println("Settings button clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/settings-view.fxml"));
        loader.setControllerFactory(WeatherApp.getSpringContext()::getBean);

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Settings");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(locationLabel.getScene().getWindow());

        Scene scene = new Scene(loader.load());
        dialogStage.setScene(scene);

        // Pass reference to controller
        SettingsController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setWeatherController(this);

        dialogStage.showAndWait();
    }

    // Update weather info from API or settings
    public void updateWeather(WeatherDataResponse data) {
        this.currentLocation = data.location();
        locationLabel.setText("📍 Location: " + data.location());
        conditionLabel.setText("☀️ Condition: " + data.weatherDescription());
        temperatureLabel.setText("🌡️ Temperature: " + data.tempCelsius() + "°C / " + data.tempFahrenheit() + "°F");
        windLabel.setText("💨 Wind: " + data.windSpeed());
        humidityLabel.setText("💧 Humidity: " + data.humidity() + "%");
    }

    // Helper for SettingsController
    public void updateLocation(String location) {
        fetchAndUpdateWeather(location);
    }

    private void fetchAndUpdateWeather(String location) {
        weatherService.getWeather(location)
                .doOnError(err -> System.err.println("Failed to fetch weather: " + err.getMessage()))
                .subscribe(data ->
                        Platform.runLater(() -> updateWeather(data)) // ensure UI updates on FX thread
                );
    }
}
