package com.cardgame.card;

import com.cardgame.cardcontainer.CardFactory;

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
        this.weight = weight;
    }

    public String harvest() {
        return this.product;
    }
    public boolean readyToHarvest() {
        return this.weight >= this.weightToHarvest;
    }
    public void feed(Product product) {
        this.setWeight(this.getWeight()+product.getAddsWeight());
    }

    public void addItem(String item) {
        this.items.add(item);
    }
}
