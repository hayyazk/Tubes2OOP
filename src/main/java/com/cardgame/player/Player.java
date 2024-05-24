package com.cardgame.player;

import com.cardgame.card.*;
import com.cardgame.cardcontainer.ActiveDeck;
import com.cardgame.cardcontainer.Deck;
import com.cardgame.cardcontainer.Ladang;

import java.util.ArrayList;

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
        return this.deck.shuffle();
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
    public void addToLadang(String key, Harvestable card) {
        this.ladang.add(key, card);
    }
    public void addToLadang(String key, Product card) {
        this.ladang.feedAnimal(key, card);
    }
    public void addToLadang(String key, Item card) {
        Card c = this.ladang.get(key);
        if (card.getKode().equals("ACCELERATE")) {
            if (c instanceof Animal) {
                ((Animal) c).addItem(card.getKode());
                ((Animal) c).setWeight(((Animal) c).getWeight() + 8);
                this.ladang.add(key, (Animal) c);
            } else if (c instanceof Plant) {
                ((Plant) c).addItem(card.getKode());
                ((Plant) c).setAge(((Plant) c).getAge() + 2);
            }
        } else if (card.getKode().equals("DELAY")) {
            if (c instanceof Animal) {
                ((Animal) c).addItem(card.getKode());
                ((Animal) c).setWeight(((Animal) c).getWeight() - 5);
                this.ladang.add(key, (Animal) c);
            } else if (c instanceof Plant) {
                ((Plant) c).addItem(card.getKode());
                ((Plant) c).setAge(((Plant) c).getAge() - 2);
            }
        } else if (card.getKode().equals("INSTANT_HARVEST")) {
            this.harvest(key);
        } else if (card.getKode().equals("DESTROY")) {
            if (c instanceof Animal) {
                if (!((Animal) c).getItems().contains("PROTECT")) {
                    this.ladang.destroy(key);
                }
            } else if (c instanceof Plant) {
                if (!((Plant) c).getItems().contains("PROTECT")) {
                    this.ladang.destroy(key);
                }
            }
        } else if (card.getKode().equals("PROTECT")) {
            if (c instanceof Animal) {
                ((Animal) c).addItem(card.getKode());
            } else if (c instanceof Plant) {
                ((Plant) c).addItem(card.getKode());
            }
        }
    }
    public void harvest(String key) {
        this.activeDeck.add(this.ladang.harvest(key));
    }
    public void agePlants() {
        this.ladang.agePlants();
    }
}
