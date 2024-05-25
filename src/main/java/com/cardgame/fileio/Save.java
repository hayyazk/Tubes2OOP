package com.cardgame.fileio;

import com.cardgame.card.Animal;
import com.cardgame.card.Card;
import com.cardgame.card.Harvestable;
import com.cardgame.card.Plant;
import com.cardgame.cardcontainer.ActiveDeck;
import com.cardgame.cardcontainer.Ladang;
import com.cardgame.player.Player;
import com.cardgame.state.State;
import com.cardgame.store.Store;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class Save {
    private File dir;

    public Save(String folder) {
        File d = new File(folder);
        if (!d.exists()) {
            d.mkdirs();
        }
        this.dir = d;
    }

    public void writeGameState(State state) throws IOException {
        FileWriter f = new FileWriter(new File(this.dir, "gamestate.txt"));
        PrintWriter p = new PrintWriter(f);
        p.println(state.getTurn());
        Store store = state.getStore();
        int n = 0;
        String store_items = "";
        for (HashMap.Entry<String, Integer> e: store.getContent().entrySet()) {
            if (e.getValue() > 0) {
                n++;
                store_items += e.getKey() + " " + e.getValue() + "\n";
            }
        }
        p.println(n);
        p.print(store_items);
        p.close();
    }

    public void writePlayer(Player player, String name) throws IOException {
        PrintWriter p = new PrintWriter(new FileWriter(new File(this.dir, name)));
        p.println(player.getMoney());
        p.println(player.getDeck().getDeck().size());

        ActiveDeck ad = player.getActiveDeck();
        p.println(ad.getActiveDeck().size());
        for (HashMap.Entry<String, Card> e: ad.getActiveDeck().entrySet()) {
            p.println(e.getKey() + " " + e.getValue().getKode());
        }

        Ladang l = player.getLadang();
        p.println(l.getLadang().size());
        for (HashMap.Entry<String, Harvestable> e: l.getLadang().entrySet()) {
            Card c = l.get(e.getKey());
            int level = 0;
            if (c instanceof Animal) {
                level = ((Animal) c).getWeight();
            } else {
                level = ((Plant) c).getAge();
            }
            String items = "";
            if (!e.getValue().getItems().isEmpty()) {
                items += e.getValue().getItems().size();
                for (String s: e.getValue().getItems()) {
                    items += " " + s;
                }
            }
            p.println(e.getKey() + " " + c.getKode() + " " + level + " " + items);
        }
        p.close();
    }
}
