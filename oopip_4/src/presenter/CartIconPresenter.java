package presenter;

import patterns.CartObserver;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class CartIconPresenter implements CartObserver {
    private Label cartCountLabel;

    public CartIconPresenter(Label label) {
        this.cartCountLabel = label;
        // Установка начального значения
        Platform.runLater(() -> cartCountLabel.setText("0"));
    }

    /**
     * Вызывается, когда корзина (Observable) меняется.
     * Реализация метода из интерфейса CartObserver.
     */
    @Override
    public void update(int itemCount) {
        // Platform.runLater нужен для безопасного обновления UI (Label)
        Platform.runLater(() -> {
            cartCountLabel.setText(String.valueOf(itemCount));
        });
    }
}