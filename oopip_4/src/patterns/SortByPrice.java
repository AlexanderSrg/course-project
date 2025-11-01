package patterns;

import model.BoardGame;
import java.util.Comparator;
import java.util.List;

public class SortByPrice implements SortingStrategy {
    @Override
    public void sort(List<BoardGame> games) {
        // Сортировка по цене: от дешевых к дорогим
        games.sort(Comparator.comparing(BoardGame::getPrice));
    }
}