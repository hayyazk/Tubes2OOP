package com.cardgame.cardcontainer;

import com.cardgame.card.Animal;
import com.cardgame.card.Card;
import com.cardgame.card.Harvestable;
import com.cardgame.card.Product;
import com.cardgame.card.Plant;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ladang {
    private static final int row = 4;
    private static final int col = 5;
    private HashMap<String, Harvestable> ladang;
    public Ladang() {
        ladang = new HashMap<>();
    }
    public Ladang(HashMap<String, Harvestable> ladang) {
        this.ladang = ladang;
    }
    public Ladang(Ladang other) {
        this.ladang = other.getLadang();
    }
    public void add(String key, Harvestable card) {
        ladang.put(key, card);
    }
    public Card get(String key) {
        Harvestable v = this.ladang.get(key);
        if (v instanceof Animal) {
            return (Animal) v;
        } else if (v instanceof Plant) {
            return (Plant) v;
        }
        return null;
    }
    public static Pair<Integer, Integer> getIdxFromKey(String key) {
        int col = key.charAt(0) - 65;
        int row = key.charAt(2) - 49;
        return new Pair<>(col, row);
    }
    public static String getKeyFromIdx(Pair<Integer, Integer> idx) {
        ArrayList<String> letters = new ArrayList<>(List.of("A", "B", "C", "D", "E"));
        int num = idx.getValue() + 1;
        return letters.get(idx.getKey()) + "0" + num;
    }
    public static String getKeyFromIdx(int col, int row) {
        ArrayList<String> letters = new ArrayList<>(List.of("A", "B", "C", "D", "E"));
        int num = row + 1;
        return letters.get(col) + "0" + num;
    }
    public HashMap<String, Harvestable> getLadang() {
        return this.ladang;
    }
    public void addItem(String key, String item) {
        Harvestable h = this.ladang.get(key);
        h.addItem(item);
        this.ladang.put(key, h);
    }
    public void destroy(String key) {
        if (!this.ladang.get(key).getItems().contains("PROTECT")) {
            this.ladang.remove(key);
        }
    }
    public void feedAnimal(String key, Product product) {
        Animal animal = (Animal) this.ladang.get(key);
        animal.feed(product);
        this.ladang.put(key, animal);
    }
    public String harvest(String key) {
        Harvestable card = this.ladang.remove(key);
        return card.harvest();
    }
    public void remove(String key) {
        this.ladang.remove(key);
    }
    public void agePlants() {
        for (Map.Entry<String, Harvestable> v : this.ladang.entrySet()) {
            if (v.getValue() instanceof Plant) {
                Plant p = (Plant) v.getValue();
                p.setAge(p.getAge() + 1);
                this.ladang.put(v.getKey(), p);
            }
        }
    }

    public static Harvestable castToHarvestable(Card c) {
        if (c instanceof Animal) {
            return (Animal) c;
        }
        if (c instanceof Plant) {
            return (Plant) c;
        }
        return null;
    }
}
