package patterns;
import model.BoardGame;
import java.util.List;

public interface SortingStrategy {
    void sort(List<BoardGame> games);
}