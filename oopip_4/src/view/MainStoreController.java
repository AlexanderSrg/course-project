package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import model.BoardGame;
import model.Cart;
import model.UserRole;
import presenter.CartIconPresenter;
import presenter.MainStorePresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainStoreController {

    // FXML элементы (должны соответствовать fx:id в main-store-view.fxml)
    @FXML private Label welcomeLabel;
    @FXML private ComboBox<String> sortingComboBox;
    @FXML private TableView<BoardGame> gamesTable;
    @FXML private Label cartCountLabel;
    @FXML private Button adminButton; // Кнопка "Управление каталогом"

    // --- Поля ---
    private MainStorePresenter presenter;
    private ObservableList<BoardGame> gameList;
    private Cart shoppingCart;

    @FXML
    public void initialize() {
        // 1. Инициализация Model и Presenter
        this.shoppingCart = new Cart();
        this.presenter = new MainStorePresenter(this, shoppingCart);

        this.gameList = FXCollections.observableArrayList();
        gamesTable.setItems(gameList);

        // 2. Инициализация TableView
        initializeTableColumns();

        // 3. Инициализация паттерна Observer (Наблюдатель за корзиной)
        CartIconPresenter cartIconPresenter = new CartIconPresenter(cartCountLabel);
        shoppingCart.addObserver(cartIconPresenter);

        // 4. Инициализация сортировки (Паттерн Strategy)
        initializeSorting();

        // 5. Загрузка данных (заполнение таблицы при открытии окна)
        presenter.loadGames();
    }

    // Настраивает колонки таблицы и привязывает их к геттерам в BoardGame
    private void initializeTableColumns() {
        TableColumn<BoardGame, String> nameCol = new TableColumn<>("Название");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(250);

        TableColumn<BoardGame, Double> priceCol = new TableColumn<>("Цена, $");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<BoardGame, Double> ratingCol = new TableColumn<>("Рейтинг");
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));

        gamesTable.getColumns().clear();
        gamesTable.getColumns().addAll(nameCol, priceCol, ratingCol);
    }

    // Настраивает ComboBox и добавляет слушатель для сортировки
    private void initializeSorting() {
        sortingComboBox.getItems().addAll("По названию", "По цене", "По рейтингу");

        sortingComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // При выборе критерия вызываем Presenter для сортировки
                presenter.handleSortSelection(newVal, new ArrayList<>(gameList));
            }
        });
    }

    // --- МЕТОДЫ, ПРИВЯЗАННЫЕ К КНОПКАМ (onAction) ---

    @FXML
    private void onAddToCartButtonClick() {
        BoardGame selectedGame = gamesTable.getSelectionModel().getSelectedItem();
        if (selectedGame != null) {
            presenter.handleAddToCart(selectedGame);
        } else {
            new Alert(Alert.AlertType.WARNING, "Выберите игру для добавления в корзину.").showAndWait();
        }
    }

    @FXML
    private void onDetailsButtonClick() {
        BoardGame selectedGame = gamesTable.getSelectionModel().getSelectedItem();
        if (selectedGame == null) {
            new Alert(Alert.AlertType.WARNING, "Выберите игру, чтобы посмотреть детали.").showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/game-details-view.fxml"));
            Parent root = loader.load();

            // ✅ ВАЖНО: Получаем контроллер деталей
            GameDetailsController detailsController = loader.getController();

            // ✅ ВАЖНО: Передаем ему выбранный объект игры
            if (detailsController != null) {
                detailsController.setGame(selectedGame);
            }

            Stage stage = new Stage();
            stage.setTitle("Детали игры: " + selectedGame.getName());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Не удалось загрузить окно деталей. Убедитесь, что 'game-details-view.fxml' и 'GameDetailsController.java' существуют.").showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void onAdminPanelButtonClick() {
        try {
            // Загрузка нового, отдельного окна для панели админа
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/admin-panel-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Панель администратора");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            // Это сообщение об ошибке, которое вы видели
            new Alert(Alert.AlertType.ERROR, "Не удалось загрузить панель администратора. Убедитесь, что 'admin-panel-view.fxml' существует.").showAndWait();
            e.printStackTrace();
        }
    }



    // --- МЕТОДЫ, ВЫЗЫВАЕМЫЕ PRESENTER'ОМ ---

    /**
     * Устанавливает контекст пользователя и управляет видимостью кнопки администратора.
     */
    public void setUserContext(UserRole role) {
        welcomeLabel.setText("Привет, " + (role == UserRole.ADMIN ? "Администратор" : "Клиент") + "!");

        // Скрыть кнопку "Управление каталогом", если пользователь - Клиент
        // setVisible(false) скрывает элемент, setManaged(false) убирает его из макета
        adminButton.setVisible(role == UserRole.ADMIN);
        adminButton.setManaged(role == UserRole.ADMIN);
    }

    /**
     * Обновляет данные в TableView. Вызывается Presenter'ом.
     */
    public void displayGames(List<BoardGame> games) {
        // gameList - это ObservableList, привязанный к таблице,
        // его изменение автоматически обновляет View.
        gameList.setAll(games);
    }
}