package com.cardgame.cardcontainer;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<String> deck;
    final private static int size = 40;
    public Deck() {
        this.deck = new ArrayList<>(size);

        for (int i = 0; i < Math.round(size*0.25); i++) {
            String card = getRandom(CardFactory.animalNames());
            while (card.equals("BERUANG")) {
                card = getRandom(CardFactory.animalNames());
            }
            this.deck.add(card);
        }
        for (int i = 0; i < Math.round(size*0.25); i++) {
            this.deck.add(getRandom(CardFactory.plantNames()));
        }
        for (int i = 0; i < Math.round(size*0.25); i++) {
            this.deck.add(getRandom(CardFactory.productNames()));
        }
        for (int i = 0; i < Math.round(size*0.25); i++) {
            this.deck.add(getRandom(CardFactory.itemNames()));
        }
    }
    public Deck(Deck other) {
        this.deck = other.getDeck();
    }
    public Deck(ArrayList<String> deck) {
        this.deck = deck;
    }

    public ArrayList<String> shuffle(int n) {
        ArrayList<String> shuffled = new ArrayList<>();
        n = Math.min(n, 4);
        n = Math.min(n, this.deck.size());
        for (int i = 0; i < n; i++) {
            String res = getRandom(this.deck);
            shuffled.add(res);
            this.deck.remove(res);
        }
        return shuffled;
    }

    public void add(ArrayList<String> cards) {
        this.deck.addAll(cards);
    }

    public ArrayList<String> getDeck() {
        return this.deck;
    }

    public String getRandom(ArrayList<String> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
