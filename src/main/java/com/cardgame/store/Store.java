package com.cardgame.store;

import com.cardgame.cardcontainer.CardFactory;

import java.util.HashMap;

public class Store {
    private HashMap<String, Integer> products;

    public Store() {
        products = new HashMap<>();
        for (String s: CardFactory.productNames()) {
            products.put(s, 0);
        }
    }
    public void add(String name) {
        Integer n = this.products.get(name) + 1;
        this.products.put(name, n);
    }
    public void setAmount(String name, int amount) {
        this.products.put(name, amount);
    }
    public void remove(String name) {
        Integer n = this.products.get(name) - 1;
        this.products.put(name, n);
    }
    public HashMap<String, Integer> getContent() {
        return this.products;
    }
}
