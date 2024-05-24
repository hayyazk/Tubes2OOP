package com.cardgame.card;

import java.util.ArrayList;

public class Plant extends Card implements Harvestable {
    private int age;
    final private int ageToHarvest;
    final private String product;
    private ArrayList<String> items;

    public Plant(String name, String img, String kode, int age, int ageToHarvest, String product) {
        super(name, img, kode);
        this.age = age;
        this.ageToHarvest = ageToHarvest;
        this.product = product;
        this.items = new ArrayList<>();
    }
    public Plant(Plant otherPlant) {
        super(otherPlant);
        this.age = otherPlant.getAge();
        this.ageToHarvest = otherPlant.getAgeToHarvest();
        this.product = otherPlant.getProduct();
        this.items = otherPlant.getItems();
    }

    public ArrayList<String> getItems() {
        return this.items;
    }

    public int getAge() {
        return age;
    }

    public int getAgeToHarvest() {
        return ageToHarvest;
    }

    public String getProduct() {
        return product;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void addItem(String item) {
        this.items.add(item);
    }

    public String harvest() {
        return this.product;
    }
    public boolean readyToHarvest() {
        return this.age >= this.ageToHarvest;
    }
}
