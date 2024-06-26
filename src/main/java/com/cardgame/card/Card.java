package com.cardgame.card;

abstract public class Card {
    final private String name;
    protected String img;
    protected String kode;

    public Card(String name, String img, String kode) {
        this.name = name;
        this.img = img;
        this.kode = kode;
    }
    public Card(Card otherCard) {
        this.name = otherCard.getName();
        this.img = otherCard.getImage();
        this.kode = otherCard.getKode();
    }
    public String getName() {
        return name;
    }
    public String getImage() {
        return img;
    }
    public String getKode() {
        return kode;
    }
    abstract public String getDetails();
}
