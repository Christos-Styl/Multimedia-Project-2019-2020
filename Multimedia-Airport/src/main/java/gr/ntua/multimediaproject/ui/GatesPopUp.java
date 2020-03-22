package gr.ntua.multimediaproject.ui;

import gr.ntua.multimediaproject.ui.*;
import gr.ntua.multimediaproject.ui.scenes.GatesScene;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GatesPopUp {
    private GridPane gridPane;

    public static void display(GatesScene gatesScene) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Gates Details");
        window.setScene(gatesScene);
//
//        VBox layout = new VBox(10);
//
//        layout.getChildren().addAll(label, yesButton, noButton);
//        layout.setAlignment(Pos.CENTER);
//        Scene scene = new Scene(layout);
        window.show();
    }
}
