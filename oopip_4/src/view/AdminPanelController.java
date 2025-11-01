package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.BoardGame;
import presenter.AdminPanelPresenter;
import java.util.List;

public class AdminPanelController {

    // FXML элементы для таблицы
    @FXML private TableView<BoardGame> gamesTable;
    @FXML private TableColumn<BoardGame, String> nameCol;
    @FXML private TableColumn<BoardGame, Double> priceCol;
    @FXML private TableColumn<BoardGame, Double> ratingCol;
    @FXML private TableColumn<BoardGame, Integer> playersCol;
    @FXML private TableColumn<BoardGame, String> descriptionCol;

    // FXML элементы для полей ввода
    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextField ratingField;
    @FXML private TextField minPlayersField;
    @FXML private TextArea descriptionArea;

    private AdminPanelPresenter presenter;
    private ObservableList<BoardGame> gameList;

    @FXML
    public void initialize() {
        this.presenter = new AdminPanelPresenter(this);
        this.gameList = FXCollections.observableArrayList();
        gamesTable.setItems(gameList);

        // Настройка колонок таблицы
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        playersCol.setCellValueFactory(new PropertyValueFactory<>("minPlayers"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Загрузка данных при инициализации
        presenter.loadGames();
    }

    // --- ОБРАБОТЧИКИ КНОПОК ---

    @FXML
    private void onAddGameButtonClick() {
        presenter.handleAddGame(
                nameField.getText(),
                priceField.getText(),
                ratingField.getText(),
                minPlayersField.getText(),
                descriptionArea.getText()
        );
    }

    @FXML
    private void onDeleteGameButtonClick() {
        BoardGame selectedGame = gamesTable.getSelectionModel().getSelectedItem();
        presenter.handleDeleteGame(selectedGame);
    }

    // --- МЕТОДЫ, ВЫЗЫВАЕМЫЕ PRESENTER'ОМ ---

    public void displayGames(List<BoardGame> games) {
        gameList.setAll(games);
    }

    public void showAlert(String message, boolean isError) {
        Alert alert = new Alert(isError ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alert.setTitle(isError ? "Ошибка" : "Успех");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void clearFields() {
        nameField.clear();
        priceField.clear();
        ratingField.clear();
        minPlayersField.clear();
        descriptionArea.clear();
    }
}