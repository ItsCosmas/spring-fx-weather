package dev.cozy.springfxweather;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class SettingsController {

    @FXML
    private TextField locationField;

    private Stage dialogStage;
    private WeatherController weatherController;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setWeatherController(WeatherController controller) {
        this.weatherController = controller;
    }

    @FXML
    private void onSave() {
        String newLocation = locationField.getText();
        if (newLocation != null && !newLocation.isBlank()) {
            weatherController.updateLocation(newLocation);
        }
        dialogStage.close();
    }

    @FXML
    private void onCancel() {
        dialogStage.close();
    }
}
