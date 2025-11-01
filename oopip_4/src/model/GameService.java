package model;

import java.util.ArrayList;
import java.util.List;

public class GameService {

    //  1. Объявляем список как СТАТИЧЕСКОЕ поле класса.
    // Это делает его общим для всех методов и сохраняет данные.
    private static List<BoardGame> games = new ArrayList<>();

    //  2.  Блок инициализации для заполнения тестовыми данными
    static {
        // Заполняем список, только если он пустой (при первом запуске)
        if (games.isEmpty()) {
            games.add(new BoardGame("Колонизаторы", 50.99, 4.5, 3, "Стратегическая игра о строительстве и торговле."));
            games.add(new BoardGame("Манчкин", 15.50, 3.8, 3, "Пародийная карточная игра про монстров и сокровища."));
            games.add(new BoardGame("Глумхэвен", 120.00, 4.9, 1, "Масштабная кооперативная приключенческая игра."));
            games.add(new BoardGame("Каркассон", 35.00, 4.2, 2, "Игра на выкладывание тайлов и захват территорий."));
            games.add(new BoardGame("Ticket to Ride", 40.00, 4.0, 2, "Стройте железнодорожные маршруты по карте."));
        }
    }

    /** * Возвращает весь каталог игр.
     *  Теперь метод объявлен только один раз.
     */
    public List<BoardGame> getAllGames() {
        return games;
    }

    /** Добавляет новую игру в каталог */
    public void addGame(BoardGame game) {
        // Проверяем, существует ли игра с таким же названием (без учета регистра)
        boolean exists = games.stream().anyMatch(g -> g.getName().equalsIgnoreCase(game.getName()));
        if (!exists) {
            games.add(game);
        }
    }

    /** Удаляет игру по названию */
    public boolean deleteGame(String name) {
        // Удаляем первую игру, у которой совпадает название (без учета регистра)
        return games.removeIf(game -> game.getName().equalsIgnoreCase(name));
    }
}