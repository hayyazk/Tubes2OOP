package com.cardgame.controller;

import com.cardgame.card.*;
import com.cardgame.cardcontainer.ActiveDeck;
import com.cardgame.cardcontainer.CardFactory;
import com.cardgame.cardcontainer.Ladang;
import com.cardgame.state.State;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class StateController {
    @FXML
    private Text n_deck, p1_money, p2_money, turn, current;
    @FXML
    private GridPane this_ladang, other_ladang, activedeck;
    @FXML
    private Button next_button, save_button, load_button, plugin_button;
    @FXML
    private Tab lawan_tab, store_tab;
    @FXML
    private Pane sell_pane;
    @FXML
    private FlowPane store_pane;

    private State state;
    private Card draggedCard;
    private boolean dragFromDeck;

    @FXML
    public void initialize() {
        this.n_deck.setText("20/20");
    }

    @FXML
    public void next() {
        this.state.next();
        if (this.state.isOver()) {
            showGameOver();
        } else {
            updateState();
            if (this.state.getCurrentPlayer().getActiveDeck().getAvailable() > 0) {
                showShuffle();
            }
        }
    }

    @FXML
    public void save() {
        this.state.saveState();
        this.state.savePlayers();
    }

    public void initDragDrop() {
        this.this_ladang.setOnDragOver(e -> {
            if (e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        });
        this.this_ladang.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            Node node = e.getPickResult().getIntersectedNode();
            if (db.hasString()) {
                Integer x1 = GridPane.getColumnIndex(node);
                Integer y1 = GridPane.getRowIndex(node);
                int x = x1 == null ? 0 : x1;
                int y = y1 == null ? 0 : y1;
                dropOnThisLadang(x, y, db.getString());
            }
            e.setDropCompleted(true);
            e.consume();
        });
        this.other_ladang.setOnDragOver(e -> {
            if (e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        });
        this.other_ladang.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            Node node = e.getPickResult().getIntersectedNode();
            if (db.hasString()) {
                Integer x1 = GridPane.getColumnIndex(node);
                Integer y1 = GridPane.getRowIndex(node);
                int x = x1 == null ? 0 : x1;
                int y = y1 == null ? 0 : y1;
                dropOnOtherLadang(x, y, db.getString());
            }
            e.setDropCompleted(true);
            e.consume();
        });
        this.sell_pane.setOnDragOver(e -> {
            if (e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        });
        this.sell_pane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasString()) {
                this.state.sell(db.getString());
                updateState();
            }
            e.setDropCompleted(true);
            e.consume();
        });
    }

    public void dropOnThisLadang(int x, int y, String s) {
        String key = Ladang.getKeyFromIdx(x, y);
        boolean available = this.state.getCurrentPlayer().isLadangSlotEmpty(key);
        if (this.draggedCard instanceof Animal ||this.draggedCard instanceof Plant) {
            if (available) {
                if (this.dragFromDeck) {
                    Card c = this.state.getCurrentPlayer().getActiveDeck().get(s);
                    this.state.getCurrentPlayer().addToLadang(key, Ladang.castToHarvestable(c), s);
                } else {
                    Card c = this.state.getCurrentPlayer().getLadang().get(s);
                    this.state.getCurrentPlayer().relocateLadang(key, Ladang.castToHarvestable(c), s);
                }
            }
        } else if (this.draggedCard instanceof Product) {
            if (!available) {
                Card c = this.state.getCurrentPlayer().getActiveDeck().get(s);
                this.state.getCurrentPlayer().addToLadang(key, (Product) c, s);
            }
        } else if (this.draggedCard instanceof Item) {
            if (!available) {
                Card c = this.state.getCurrentPlayer().getActiveDeck().get(s);
                this.state.deployItemSelf(key, (Item) c, s);
            }
        }
        updateState();
    }

    public void dropOnOtherLadang(int x, int y, String s) {
        String key = Ladang.getKeyFromIdx(x, y);
        boolean available = this.state.getOtherPlayer().isLadangSlotEmpty(key);
        if (this.draggedCard instanceof Item) {
            if (!available) {
                Card c = this.state.getCurrentPlayer().getActiveDeck().get(s);
                this.state.deployItemOther(key, (Item) c, s);
            }
        }
        updateState();
    }

    public void showGameOver() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        String winner = this.state.getWinner();
        if (winner.equals("TIE")) {
            alert.setHeaderText("It's a tie!");
        } else {
            alert.setHeaderText("The winner is " + winner);
        }
        ButtonType replay = new ButtonType("Replay");
        ButtonType exit = new ButtonType("Exit");
        alert.getButtonTypes().setAll(replay, exit);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == replay) {
            this.setState(new State());
            updateState();
        } else if (result.get() == exit) {
            System.exit(0);
        }
    }

    public void showShuffle() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Hasil Shuffle");
        GridPane shuffle_grid = new GridPane();
        shuffle_grid.setHgap(5);
        ArrayList<String> shuffle_res = this.state.getCurrentPlayer().shuffleDeck();
        int i = 0;
        for (String s: shuffle_res) {
            Card card = CardFactory.createCard(s);
            StackPane pane = displayCard(card);
            pane.setId(card.getKode());
            shuffle_grid.add(pane, i, 0);
            i++;
        }
        ButtonType reshuffle = new ButtonType("Reshuffle");
        ButtonType ok = new ButtonType("OK");
        alert.getDialogPane().setContent(shuffle_grid);
        alert.getButtonTypes().setAll(ok, reshuffle);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == reshuffle) {
            this.state.getCurrentPlayer().addToDeck(shuffle_res);
            showShuffle();
        } else {
            this.state.getCurrentPlayer().addToActiveDeck(shuffle_res);
            updateState();
        }
    }

    public void showDetails(String key, String s) {
        Card c = this.state.getCurrentPlayer().getLadang().get(key);
        if (s.equals("other")) {
            c = this.state.getOtherPlayer().getLadang().get(key);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detail petak");
        alert.setHeaderText(c.getName());
        GridPane info_grid = new GridPane();
        info_grid.setHgap(10);
        info_grid.add(displayCard(c), 0, 0);
        info_grid.add(new Text(c.getDetails()), 1, 0);
        ButtonType harvest = new ButtonType("Harvest");
        alert.getDialogPane().setContent(info_grid);
        alert.getButtonTypes().setAll(harvest);
        if (!Ladang.castToHarvestable(c).readyToHarvest()) {
            alert.getDialogPane().lookupButton(harvest).setDisable(true);
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == harvest) {
            this.state.harvest(key);
            updateState();
        }
    }

    public void updateState() {
        updateDeck();
        updateActiveDeck();
        updateThisLadang();
        updateOtherLadang();
        updateInfo();
        updateStore();
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
        int i=0;
        for (String s : ActiveDeck.keys) {
            if (this.state.getCurrentPlayer().getActiveDeck().getActiveDeck().containsKey(s)) {
                Card c = this.state.getCurrentPlayer().getActiveDeck().get(s);
                StackPane pane = displayCard(c);
                pane.setOnDragDetected(e -> {
                    Dragboard db = pane.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent cc = new ClipboardContent();
                    cc.putString(s);
                    db.setContent(cc);
                    this.draggedCard = c;
                    this.dragFromDeck = true;
                    e.consume();
                });
                this.activedeck.add(pane, i, 0);
            } else {
                this.activedeck.add(displayEmptyCard(), i, 0);
            }
            i++;
        }
    }

    public void updateThisLadang() {
        this.this_ladang.getChildren().clear();
        Ladang l = this.state.getCurrentPlayer().getLadang();
        for (int j=0; j<4; j++) {
            for (int i=0; i<5; i++) {
                String key = Ladang.getKeyFromIdx(i, j);
                Card c = l.get(key);
                if (c == null) {
                    this.this_ladang.add(displayEmptyCard(), i, j);
                } else {
                    StackPane pane = displayCard(c);
                    pane.setOnDragDetected(e -> {
                        Dragboard db = pane.startDragAndDrop(TransferMode.ANY);
                        ClipboardContent cc = new ClipboardContent();
                        cc.putString(key);
                        db.setContent(cc);
                        this.draggedCard = c;
                        this.dragFromDeck = false;
                        e.consume();
                    });
                    pane.setOnMouseClicked(e -> {
                        showDetails(key, "self");
                    });
                    this.this_ladang.add(pane, i, j);
                }
            }
        }
    }

    public void updateOtherLadang() {
        this.other_ladang.getChildren().clear();
        Ladang l = this.state.getOtherPlayer().getLadang();
        for (int j=0; j<4; j++) {
            for (int i=0; i<5; i++) {
                String key = Ladang.getKeyFromIdx(i, j);
                Card c = l.get(key);
                if (c == null) {
                    this.other_ladang.add(displayEmptyCard(), i, j);
                } else {
                    StackPane pane = displayCard(c);
                    pane.setOnMouseClicked(e -> {
                        showDetails(key, "other");
                    });
                    this.other_ladang.add(pane, i, j);
                }
            }
        }
    }

    public void updateStore() {
        this.store_pane.getChildren().clear();
        for (HashMap.Entry<String, Integer> e : this.state.getStore().getContent().entrySet()) {
            if (e.getValue() > 0) {
                Product p = CardFactory.createProduct(e.getKey());
                this.store_pane.getChildren().add(displayStoreItem(p, e.getValue()));
            }
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

    public StackPane displayEmptyCard() {
        StackPane pane = new StackPane();
        pane.setStyle("-fx-background-color:  #000000; -fx-border-color:  #000000; -fx-border-radius: 10; -fx-background-radius: 10; -fx-opacity: 0.2");
        pane.setPadding(new Insets(50, 35, 50, 35));
        return pane;
    }

    public GridPane displayStoreItem(Product p, Integer amount) {
        GridPane item_grid = new GridPane();
        item_grid.getColumnConstraints().add(new ColumnConstraints(78));
        item_grid.getColumnConstraints().add(new ColumnConstraints(72));
        item_grid.setHgap(3);
        item_grid.setStyle("-fx-background-color: #deb087; -fx-border-color: #000000; -fx-border-radius: 10; -fx-background-radius: 10");
        item_grid.add(displayCard(p), 0, 0);
        VBox item_det = new VBox();
        item_det.setAlignment(Pos.CENTER_LEFT);
        item_det.getChildren().add(new Text(String.format("Price: %d\nAmount: %d\n\n", p.getPrice(), amount)));
        Button buy = new Button("Buy");
        buy.setStyle("-fx-background-color: #ffffff");
        buy.setOnAction(e -> {
            this.state.buy(p.getKode());
            updateState();
        });
        item_det.getChildren().add(buy);
        item_grid.add(item_det, 1, 0);
        return item_grid;
    }

    public void setState(State state) {
        this.state = state;
    }
}
