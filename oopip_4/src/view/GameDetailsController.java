package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.BoardGame;

public class GameDetailsController {

    @FXML private Label titleLabel;
    @FXML private Label priceLabel;
    @FXML private Label ratingLabel;
    @FXML private Label playersLabel;
    @FXML private TextArea descriptionArea;

    /**
     * Основной метод для инициализации данных в окне.
     * Вызывается из MainStoreController.
     * @param game Объект игры, детали которого нужно отобразить.
     */
    public void setGame(BoardGame game) {
        if (game != null) {
            titleLabel.setText(game.getName());
            priceLabel.setText(String.format("Цена: %.2f $", game.getPrice()));
            ratingLabel.setText(String.format("Рейтинг: %.1f / 5.0", game.getRating()));
            playersLabel.setText(String.format("Мин. игроков: %d", game.getMinPlayers()));
            descriptionArea.setText(game.getDescription());
        }
    }
}