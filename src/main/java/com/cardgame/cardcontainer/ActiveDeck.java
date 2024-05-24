package com.cardgame.cardcontainer;

import com.cardgame.card.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActiveDeck {
    public static final ArrayList<String> keys = new ArrayList<>(List.of("A01", "B01", "C01", "D01", "E01", "F01"));
    private HashMap<String, Card> activeDeck;
    private static final int size = 6;

    public ActiveDeck() {
        this.activeDeck = new HashMap<>();
    }
    public ActiveDeck(HashMap<String, Card> activeDeck) {
        this.activeDeck = activeDeck;
    }
    public ActiveDeck(ActiveDeck other) {
        this.activeDeck = other.getActiveDeck();
    }

    public void add(ArrayList<String> card) {
        for (String c : card) {
            this.add(c);
        }
    }

    public void add(String card) {
        if (this.activeDeck.size() < size) {
            if (CardFactory.animalNames().contains(card)) {
                this.activeDeck.put(firstAvailableKey(), CardFactory.createAnimal(card));
            } else if (CardFactory.plantNames().contains(card)) {
                this.activeDeck.put(firstAvailableKey(), CardFactory.createPlant(card));
            } else if (CardFactory.productNames().contains(card)) {
                this.activeDeck.put(firstAvailableKey(), CardFactory.createProduct(card));
            } else if (CardFactory.itemNames().contains(card)) {
                this.activeDeck.put(firstAvailableKey(), CardFactory.createItem(card));
            }
        }
    }

    public Card get(String key) {
        return this.activeDeck.get(key);
    }

    public void remove(String key) {
        this.activeDeck.remove(key);
    }

    public HashMap<String, Card> getActiveDeck() {
        return this.activeDeck;
    }

    public String firstAvailableKey() {
        for (String key : keys) {
            if (!this.activeDeck.containsKey(key)) {
                return key;
            }
        }
        return null;
    }
}
