package presenter;

import model.BoardGame;
import model.Cart;
import model.GameService;
import view.MainStoreController;
import patterns.*;

import java.util.List;
import java.util.Map;

public class MainStorePresenter {
    private GameService gameService;
    private MainStoreController view;
    private Cart shoppingCart; // Ссылка на Observable
    private Map<String, SortingStrategy> strategyMap;

    public MainStorePresenter(MainStoreController view, Cart cart) {
        this.view = view;
        this.gameService = new GameService();
        this.shoppingCart = cart;

        // Инициализация Map для паттерна Strategy (связь строки с объектом)
        this.strategyMap = Map.of(
                "По названию", new SortByName(),
                "По цене", new SortByPrice(),
                "По рейтингу", new SortByRating()
        );
    }

    /** Загружает игры из Model и просит View их отобразить */
    public void loadGames() {
        List<BoardGame> games = gameService.getAllGames();
        view.displayGames(games);
    }

    /** Применяет выбранную стратегию сортировки (Паттерн Strategy) */
    public void handleSortSelection(String sortCriteria, List<BoardGame> currentGames) {
        SortingStrategy strategy = strategyMap.get(sortCriteria);

        if (strategy != null) {
            strategy.sort(currentGames); // Применение стратегии
            view.displayGames(currentGames); // Обновление View
        }
    }

    /** Добавляет игру в корзину (Паттерн Observer) */
    public void handleAddToCart(BoardGame game) {
        shoppingCart.addItem(game); // Observable меняет состояние и уведомляет Observer'ов
        System.out.println("Добавлено в корзину: " + game.getName() +
                ". Всего товаров: " + shoppingCart.getItemCount());
    }
}