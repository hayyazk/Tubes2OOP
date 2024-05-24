package com.cardgame.state;

import com.cardgame.card.Card;
import com.cardgame.card.Harvestable;
import com.cardgame.card.Product;
import com.cardgame.cardcontainer.ActiveDeck;
import com.cardgame.cardcontainer.CardFactory;
import com.cardgame.cardcontainer.Deck;
import com.cardgame.cardcontainer.Ladang;
import com.cardgame.player.Player;

import java.util.ArrayList;

public class State {
    private Player current_player, p1, p2;
    private int turn;
    private String winner;

    public State() {
        CardFactory.init();
        this.p1 = new Player("Player 1");
        this.p2 = new Player("Player 2");
        this.current_player = p1;
        this.turn = 1;
    }

    public Player getCurrentPlayer() {
        return current_player;
    }
    public Player getP1() {
        return p1;
    }
    public Player getP2() {
        return p2;
    }

    public ArrayList<String> shuffleDeck() {
        return this.current_player.shuffleDeck();
    }
    public void addToDeck(ArrayList<String> list) {
        this.current_player.addToDeck(list);
    }
    public void addToActiveDeck(ArrayList<String> list) {
        this.current_player.addToActiveDeck(list);
    }
    public void removeFromActiveDeck(String key) {
        this.current_player.removeFromActiveDeck(key);
    }
    public Card getFromActiveDeck(String key) {
        return this.current_player.getActiveDeck().get(key);
    }
    public void addToLadang(String key, Harvestable card) {
        this.current_player.addToLadang(key, card);
    }
    public void addToLadang(String key, Product card) {
        this.current_player.addToLadang(key, card);
    }
    public void harvest(String key) {
        this.current_player.harvest(key);
    }
    public void next() {
        this.turn++;
        if (this.turn > 20) {
            checkWin();
        }
        this.p1.agePlants();
        this.p2.agePlants();
        if (turn % 2 == 0) {
            this.current_player = p2;
        } else {
            this.current_player = p1;
        }
    }

    public void checkWin() {
        if (p1.getMoney() > p2.getMoney()) {
            this.winner = "PLAYER 1";
        } else if (p1.getMoney() < p2.getMoney()) {
            this.winner = "PLAYER 2";
        } else {
            this.winner = "TIE";
        }
    }

    public int getTurn() {
        return this.turn;
    }
}
