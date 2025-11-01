package patterns;

import model.BoardGame;
import java.util.Comparator;
import java.util.List;

public class SortByRating implements SortingStrategy {
    @Override
    public void sort(List<BoardGame> games) {
        // Сортировка по рейтингу: от высокого к низкому (обратный порядок)
        games.sort(Comparator.comparing(BoardGame::getRating).reversed());
    }
}