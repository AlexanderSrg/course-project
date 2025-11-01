package model;

public class BoardGame {
    private String name;
    private double price;
    private int minPlayer;
    private double rating;
    private String description;

    public BoardGame(String name, double price, double rating, int minPlayer, String description){
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.minPlayer = minPlayer;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getMinPlayers() {
        return minPlayer;
    }

    public double getRating() {
        return rating;
    }
    public String getDescription() {
        return description;
    }

    // TODO: Здесь могут быть сеттеры, если админ меняет данные
}