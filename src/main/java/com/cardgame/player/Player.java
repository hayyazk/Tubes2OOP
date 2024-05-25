package com.cardgame.player;

import com.cardgame.card.*;
import com.cardgame.cardcontainer.ActiveDeck;
import com.cardgame.cardcontainer.Deck;
import com.cardgame.cardcontainer.Ladang;

import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;

public class Player {
    private String name;
    private int money;
    private Deck deck;
    private ActiveDeck activeDeck;
    private Ladang ladang;

    public Player(String name) {
        this.name = name;
        this.money = 0;
        this.deck = new Deck();
        this.activeDeck = new ActiveDeck();
        this.ladang = new Ladang();
    }

    public Player(String name, int money, Deck deck, ActiveDeck activeDeck, Ladang ladang) {
        this.name = name;
        this.money = money;
        this.deck = new Deck(deck);
        this.activeDeck  = new ActiveDeck(activeDeck);
        this.ladang = new Ladang(ladang);
    }
    public void setDeck(Deck deck) {
        this.deck = deck;
    }
    public String getName() {
        return name;
    }
    public int getMoney() {
        return money;
    }
    public Deck getDeck() {
        return deck;
    }
    public ActiveDeck getActiveDeck() {
        return activeDeck;
    }
    public Ladang getLadang() {
        return ladang;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public ArrayList<String> shuffleDeck() {
        return this.deck.shuffle(this.activeDeck.getAvailable());
    }
    public void addToDeck(ArrayList<String> list) {
        this.deck.add(list);
    }
    public void addToActiveDeck(ArrayList<String> list) {
        this.activeDeck.add(list);
    }
    public void removeFromActiveDeck(String key) {
        this.activeDeck.remove(key);
    }
    public void addToLadang(String key, Harvestable card, String deckKey) {
        if (this.isLadangSlotEmpty(key)) {
            this.ladang.add(key, card);
            this.removeFromActiveDeck(deckKey);
        }
    }
    public void relocateLadang(String target, Harvestable card, String source) {
        if (this.isLadangSlotEmpty(target)) {
            this.ladang.add(target, card);
            this.removeFromLadang(source);
        }
    }
    public void addToLadang(String key, Product card, String deckKey) {
        Card c = this.ladang.get(key);
        if (c instanceof Animal) {
            if (((Animal) c).getAnimalType().equals(card.getType()) || ((Animal) c).getAnimalType().equals("OMNIVORE")) {
                this.ladang.feedAnimal(key, card);
                this.removeFromActiveDeck(deckKey);
            }
        }
    }
    public boolean addToLadang(String key, Item card) {
        boolean success = false;
        String kode = card.getKode();
        if (kode.equals("ACCELERATE") || kode.equals("PROTECT") || kode.equals("TRAP") || kode.equals("DELAY")) {
            this.ladang.addItem(key, card.getKode());
            success = true;
        } else if (card.getKode().equals("INSTANT_HARVEST")) {
            this.harvest(key);
            success = true;
        } else if (card.getKode().equals("DESTROY")) {
            this.ladang.destroy(key);
            success = true;
        }
        return success;
    }
    public void removeFromLadang(String key) {
        this.ladang.remove(key);
    }
    public boolean isLadangSlotEmpty(String key) {
        return this.ladang.get(key) == null;
    }
    public void harvest(String key) {
        this.activeDeck.add(this.ladang.harvest(key));
    }
    public void agePlants() {
        this.ladang.agePlants();
    }
}
