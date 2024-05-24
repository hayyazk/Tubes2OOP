package com.cardgame.controller;

import com.cardgame.card.Card;
import com.cardgame.card.Harvestable;
import com.cardgame.cardcontainer.ActiveDeck;
import com.cardgame.cardcontainer.CardFactory;
import com.cardgame.cardcontainer.Ladang;
import com.cardgame.state.State;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public class StateController {
    @FXML
    private Text n_deck, p1_money, p2_money, turn, current;
    @FXML
    private FlowPane activedeck;
    @FXML
    private GridPane this_ladang, other_ladang;
    @FXML
    private Button next_button, save_button, load_button, plugin_button;

    private State state;

    @FXML
    public void initialize() {
        this.n_deck.setText("20/20");
    }

    public void showShuffle() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ArrayList<String> shuffle_res = this.state.shuffleDeck();
        ArrayList<String> chosen = new ArrayList<>(2);
        for (String s: shuffle_res) {
            Card card = CardFactory.createCard(s);
            StackPane pane = displayCard(card);
            pane.setOnMouseClicked(e ->
                    pane.isPickOnBounds());
        }
    }

    @FXML
    public void next() {
        this.state.next();
        updateState();
    }

    public void updateState() {
        updateDeck();
        updateActiveDeck();
        updateThisLadang();
        updateInfo();
    }

    public void updateInfo() {
        this.turn.setText(String.format("TURN COUNT: %d", this.state.getTurn()));
        this.p1_money.setText(String.format("%d", this.state.getP1().getMoney()));
        this.p2_money.setText(String.format("%d", this.state.getP2().getMoney()));
        this.current.setText(String.format("%s's Turn", this.state.getCurrentPlayer().getName()));
    }

    public void updateDeck() {
        this.n_deck.setText(String.format("%d/40", this.state.getCurrentPlayer().getDeck().getDeck().size()));
    }

    public void updateActiveDeck() {
        this.activedeck.getChildren().clear();
        for (String s : ActiveDeck.keys) {
            if (this.state.getCurrentPlayer().getActiveDeck().getActiveDeck().containsKey(s)) {
                this.activedeck.getChildren().add(displayCard(this.state.getCurrentPlayer().getActiveDeck().get(s)));
            } else {
                Pane pane = new Pane();
                pane.setStyle("-fx-background-color:  #ecc39f; -fx-border-color:  #000000; -fx-border-radius: 10; -fx-background-radius: 10;");
                pane.prefWidth(300);
                pane.prefHeight(420);
                pane.setPadding(new Insets(50, 35, 50, 35));
                this.activedeck.getChildren().add(pane);
            }
        }
    }

    public void updateThisLadang() {
        this.this_ladang.getChildren().clear();
        Ladang l = this.state.getCurrentPlayer().getLadang();
        for (HashMap.Entry<String, Harvestable> v : l.getLadang().entrySet()) {
            Pair<Integer, Integer> idx = Ladang.getIdxFromKey(v.getKey());
            Card c = l.get(v.getKey());
            StackPane pane = displayCard(c);
            this.this_ladang.add(pane, idx.getKey(), idx.getValue());
        }
    }

    public StackPane displayCard(Card c) {
        StackPane pane = new StackPane();
        try {
            String imgPath = getClass().getResource("/com/cardgame/card/" + c.getImage()).toURI().toString();
            pane.setStyle("-fx-background-image: url('" + imgPath +"');-fx-border-color:  #000000; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-size: contain;");
            pane.setPadding(new Insets(50, 35, 50, 35));
        } catch (URISyntaxException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return pane;
    }

    public void setState(State state) {
        this.state = state;
    }
}
