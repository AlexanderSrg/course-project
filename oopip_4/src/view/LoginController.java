package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import model.UserRole;
import presenter.LoginPresenter;

import java.io.IOException;

public class LoginController {

    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;

    private LoginPresenter presenter;

    @FXML
    public void initialize() {
        // Инициализация Presenter
        this.presenter = new LoginPresenter(this);
    }

    @FXML
    private void onLoginButtonClick() {
        // Передаем работу Presenter'у
        presenter.handleLogin(loginField.getText(), passwordField.getText());
    }

    @FXML
    private void onRegisterButtonClick() {
        // Передаем работу Presenter'у
        presenter.handleRegistration(loginField.getText(), passwordField.getText());
    }

    // --- МЕТОДЫ, КОТОРЫЕ PRESENTER ВЫЗЫВАЕТ ДЛЯ ОБНОВЛЕНИЯ VIEW ---

    /**
     * ✅ Главный метод, который открывает интерфейс магазина.
     */
    public void showMainStore(UserRole role) {
        try {
            // 1. Указываем FXML-файл, который нужно загрузить
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/resources/main-store-view.fxml"));

            // 2. Загружаем корневой элемент
            Parent root = loader.load();

            // 3. Получаем контроллер НОВОГО ОКНА
            MainStoreController mainController = loader.getController();

            // 4. Передаем роль пользователя в контроллер магазина
            if (mainController != null) {
                mainController.setUserContext(role);
            }

            // 5. Смена сцены: получаем текущее окно и устанавливаем новую сцену
            // Получаем Stage (окно) по любому элементу
            Stage stage = (Stage) loginField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Магазин настольных игр: Каталог");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showError("Критическая ошибка загрузки интерфейса магазина.");
        }
    }

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успех");
        alert.setContentText(message);
        alert.showAndWait();
    }
}