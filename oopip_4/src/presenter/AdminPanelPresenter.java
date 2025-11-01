package presenter;

import model.BoardGame;
import model.GameService;
import view.AdminPanelController;

import java.util.List;

public class AdminPanelPresenter {
    private GameService gameService = new GameService();
    private AdminPanelController view;

    public AdminPanelPresenter(AdminPanelController view) {
        this.view = view;
    }

    /** Загружает и отображает текущий список игр в админ-панели */
    public void loadGames() {
        List<BoardGame> games = gameService.getAllGames();
        view.displayGames(games);
    }

    /** Обрабатывает добавление новой игры */
    public void handleAddGame(String name, String priceStr, String ratingStr, String playersStr, String description) {
        if (name.isEmpty() || description.isEmpty() || priceStr.isEmpty() || ratingStr.isEmpty() || playersStr.isEmpty()) {
            view.showAlert("Заполните все поля.", true);
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            double rating = Double.parseDouble(ratingStr);
            int minPlayers = Integer.parseInt(playersStr);

            if (rating < 1.0 || rating > 5.0 || price <= 0 || minPlayers <= 0) {
                view.showAlert("Цена, рейтинг и игроки должны быть корректными.", true);
                return;
            }

            BoardGame newGame = new BoardGame(name, price, rating, minPlayers, description);
            gameService.addGame(newGame);

            view.showAlert("Игра успешно добавлена!", false);
            loadGames(); // Обновляем список после добавления
            view.clearFields(); // Очищаем поля ввода

        } catch (NumberFormatException e) {
            view.showAlert("Цена, рейтинг и игроки должны быть числами.", true);
        }
    }

    /** Обрабатывает удаление выбранной игры */
    public void handleDeleteGame(BoardGame selectedGame) {
        if (selectedGame == null) {
            view.showAlert("Выберите игру для удаления.", true);
            return;
        }

        if (gameService.deleteGame(selectedGame.getName())) {
            view.showAlert("Игра '" + selectedGame.getName() + "' успешно удалена.", false);
            loadGames(); // Обновляем список после удаления
        } else {
            view.showAlert("Ошибка при удалении игры.", true);
        }
    }
}