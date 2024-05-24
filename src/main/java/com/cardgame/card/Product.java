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

    public int getPrice() {
        return price;
    }
    public int getAddsWeight() {
        return addsWeight;
    }
    public String getType() {
        return type;
    }

    public String details() {
        return String.format("Harga: %d", this.price);
    }
}
