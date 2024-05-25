package com.cardgame.card;

public class Product extends Card{
    final private int price;
    final private int addsWeight;
    final private String type;

    public Product(String name, String img, String kode, int price, int addsWeight, String type) {
        super(name, img, kode);
        this.price = price;
        this.addsWeight = addsWeight;
        this.type = type;
    }

    public Product(Product other) {
        super(other);
        this.price = other.getPrice();
        this.addsWeight = other.getAddsWeight();
        this.type = other.getType();
    }

    public int getPrice() {
        return price;
    }
    public int getAddsWeight() {
        return addsWeight;
    }
    public String getType() {
        return type;
    }

    public String getDetails() {
        return String.format("Harga: %d", this.price);
    }
}
