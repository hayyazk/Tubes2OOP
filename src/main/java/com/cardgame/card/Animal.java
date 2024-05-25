package com.cardgame.card;

import java.util.ArrayList;

public class Animal extends Card implements Harvestable {
    private int weight;
    final private int weightToHarvest;
    final private String product;
    final private String animalType;
    private ArrayList<String> items;

    public Animal(String name, String img, String kode, int weight, int weightToHarvest, String product, String animalType) {
        super(name, img, kode);
        this.weight = weight;
        this.weightToHarvest = weightToHarvest;
        this.product = product;
        this.animalType = animalType;
        this.items = new ArrayList<>();
    }

    public Animal(Animal otherAnimal) {
        super(otherAnimal);
        this.weight = otherAnimal.getWeight();
        this.weightToHarvest = otherAnimal.getWeightToHarvest();
        this.product = otherAnimal.getProducts();
        this.animalType = otherAnimal.getAnimalType();
        this.items = otherAnimal.getItems();
    }

    public int getWeight() {
        return weight;
    }
    public int getWeightToHarvest() {
        return weightToHarvest;
    }
    public String getProducts() {
        return product;
    }
    public String getAnimalType() {return animalType;}
    public ArrayList<String> getItems() {return items;}
    public void setWeight(int weight) {
        this.weight = Math.max(weight, 0);
    }

    public String harvest() {
        return this.product;
    }
    public boolean readyToHarvest() {
        return alteredWeight() >= this.weightToHarvest;
    }
    public void feed(Product product) {
        this.setWeight(this.getWeight()+product.getAddsWeight());
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public void addItem(String item) {
        ArrayList<String> a = new ArrayList<>(this.getItems());
        a.add(item);
        this.setItems(a);
    }

    @Override
    public boolean hasProtect() {
        return this.items.contains("PROTECT");
    }
    @Override
    public boolean hasTrap() {
        return this.items.contains("TRAP");
    }

    public int alteredWeight() {
        return Math.max(getWeight() + 8*countItem("ACCELERATE") - 5*countItem("DELAY"), 0);
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
        return String.format("Berat: %d (%d) [%d until harvest]\nItems: %s\nHasil panen: %s",
                this.weight, alteredWeight(), Math.max(0, this.weightToHarvest-this.weight), this.getItemDetails(), this.product);
    }
}
