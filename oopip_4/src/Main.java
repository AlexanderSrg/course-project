
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Убедитесь, что FXML-файл находится в папке resources
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/resources/login-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle("Вход в Магазин настольных игр");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}