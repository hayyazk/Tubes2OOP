module com.cardgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.controlsfx.controls;

    opens com.cardgame.controller to javafx.fxml;
    exports com.cardgame.controller;

    opens com.cardgame to javafx.graphics;
    exports com.cardgame;

    opens com.cardgame.state to javafx.graphics;
    exports com.cardgame.state;

    opens com.cardgame.cardcontainer to javafx.graphics;
    exports com.cardgame.cardcontainer;

    opens com.cardgame.card to javafx.graphics;
    exports com.cardgame.card;

    opens com.cardgame.player to javafx.graphics;
    exports com.cardgame.player;

    opens com.cardgame.store to javafx.graphics;
    exports com.cardgame.store;
}