package com.cardgame.card;

import com.cardgame.cardcontainer.CardFactory;

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
        if (age >= this.ageToHarvest) {
            this.img = CardFactory.createProduct(this.product).getImage();
        } else {
            this.img = CardFactory.createPlant(this.kode).getImage();
        }
        this.age = age;
    }
    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public void addItem(String item) {
        ArrayList<String> a = new ArrayList<>(this.getItems());
        a.add(item);
        this.setItems(a);
    }

    public String harvest() {
        return this.product;
    }
    public boolean readyToHarvest() {
        return this.age >= this.ageToHarvest;
    }

    @Override
    public boolean hasProtect() {
        return this.items.contains("PROTECT");
    }

    @Override
    public boolean hasTrap() {
        return this.items.contains("TRAP");
    }

    private int countItem(String item) {
        int count = 0;
        for (String s: this.items) {
            if (s.equals(item)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String getItemDetails() {
        int acc_count = countItem("ACCELERATE");
        int del_count = countItem("DELAY");
        int prot_count = countItem("PROTECT");
        int trap_count = countItem("TRAP");
        String det = "";
        if (acc_count > 0) {
            det += "Accelerate(" + acc_count + ") ";
        }
        if (del_count > 0) {
            det += "Delay(" + del_count + ") ";
        }
        if (prot_count > 0) {
            det += "Protect(" + prot_count + ") ";
        }
        if (trap_count > 0) {
            det += "Trap(" + trap_count + ") ";
        }
        return det;
    }

    @Override
    public String getDetails() {
        return String.format("Umur: %d [%d until harvest]\nItems: %s\nHasil panen: %s",
                this.age, Math.max(0, this.ageToHarvest-this.age), this.getItemDetails(), this.product);
    }
}
