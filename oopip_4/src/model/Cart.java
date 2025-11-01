package model;

import patterns.CartObserver;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartObserver> observers = new ArrayList<>();
    private List<BoardGame> items = new ArrayList<>();

    public void addObserver(CartObserver observer) {
        observers.add(observer);
    }

    public void addItem(BoardGame game) {
        items.add(game);
        notifyObservers(); // Ключевой вызов Observer
    }

    public int getItemCount() {
        return items.size();
    }

    private void notifyObservers() {
        for (CartObserver observer : observers) {
            observer.update(items.size()); // Передаем новое количество
        }
    }

    // TODO: Добавить логику для удаления товаров, подсчета суммы
}