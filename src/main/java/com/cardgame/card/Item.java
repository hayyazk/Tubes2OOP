package com.cardgame.card;

public class Item extends Card {
    private String desc;

    public Item(String name, String img, String kode, String desc) {
        super(name, img, kode);
        this.desc = desc;
    }

    public Item(Item other) {
        super(other);
        this.desc = other.getDesc();
    }

    public String getDesc() {
        return this.desc;
    }

    @Override
    public String getDetails() {
        return getDesc();
    }
}
