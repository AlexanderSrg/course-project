package patterns;

import model.BoardGame;
import java.util.Comparator;
import java.util.List;

public class SortByName implements SortingStrategy {
    @Override
    public void sort(List<BoardGame> games) {
        // Сортировка по названию (алфавитный порядок)
        games.sort(Comparator.comparing(BoardGame::getName));
    }
}