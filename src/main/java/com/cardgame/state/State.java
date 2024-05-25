package com.cardgame.state;

import com.cardgame.card.Card;
import com.cardgame.card.Item;
import com.cardgame.card.Product;
import com.cardgame.cardcontainer.CardFactory;
import com.cardgame.player.Player;
import com.cardgame.store.Store;

import java.util.ArrayList;
import java.util.List;

public class State {
    private Player current_player, other_player, p1, p2;
    private int turn;
    private Store store;

    public State() {
        CardFactory.init();
        this.p1 = new Player("Player 1");
        this.p2 = new Player("Player 2");
        this.current_player = p1;
        this.other_player = p2;
        this.turn = 1;
        this.store = new Store();
    }

    public Player getCurrentPlayer() {
        return current_player;
    }
    public Player getOtherPlayer() {
        return other_player;
    }
    public Player getP1() {
        return p1;
    }
    public Player getP2() {
        return p2;
    }
    public Store getStore() {
        return store;
    }

    public void deployItemSelf(String key, Item card, String deckKey) {
        if (!card.getKode().equals("DELAY") && !card.getKode().equals("DESTROY")) {
            boolean success = this.current_player.addToLadang(key, card);
            if (success) {
                this.current_player.removeFromActiveDeck(deckKey);
            }
        }
    }

    public void deployItemOther(String key, Item card, String deckKey) {
        if (card.getKode().equals("DELAY") || card.getKode().equals("DESTROY")) {
            boolean success = this.other_player.addToLadang(key, card);
            if (success) {
                this.current_player.removeFromActiveDeck(deckKey);
            }
        }
    }

    public void buy(String product) {
        int price = CardFactory.createProduct(product).getPrice();
        if (current_player.getMoney() >= price) {
            this.current_player.addToActiveDeck(new ArrayList<>(List.of(product)));
            this.store.remove(product);
            this.current_player.setMoney(this.current_player.getMoney() - price);
        }
    }

    public void sell(String key) {
        Card c = this.current_player.getActiveDeck().get(key);
        if (c instanceof Product) {
            this.store.add(c.getKode());
            this.current_player.removeFromActiveDeck(key);
            this.current_player.setMoney(this.current_player.getMoney() + ((Product) c).getPrice());
        }
    }

    public void harvest(String key) {
        this.current_player.harvest(key);
    }
    public void next() {
        this.turn++;
        this.p1.agePlants();
        this.p2.agePlants();
        if (turn % 2 == 0) {
            this.current_player = p2;
            this.other_player = p1;
        } else {
            this.current_player = p1;
            this.other_player = p2;
        }
    }

    public String getWinner() {
        if (p1.getMoney() > p2.getMoney()) {
            return p1.getName();
        } else if (p1.getMoney() < p2.getMoney()) {
            return p2.getName();
        }
        return "TIE";
    }

    public int getTurn() {
        return this.turn;
    }

    public boolean isOver() {
        return this.turn > 20;
    }

}
