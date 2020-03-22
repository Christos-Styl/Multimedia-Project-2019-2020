package gr.ntua.multimediaproject.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class LoadWindow {
    static String scenarioId;

    public static String display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(150);
        Label label = new Label();
        label.setText(message);

        TextField textField = new TextField();
        Button loadButton = new Button("Load");

        loadButton.setOnAction(e -> {
            if("".equals(textField.getText())) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Please type a non-empty scenario ID.");
                a.show();
            }
            else {
                File airportFile = new File("Multimedia-Airport/src/main/java/gr/ntua/multimediaproject/medialab/airport_" + textField.getText() + ".txt");
                File setupFile = new File("Multimedia-Airport/src/main/java/gr/ntua/multimediaproject/medialab/setup_" + textField.getText() + ".txt");
                if(airportFile.exists() && setupFile.exists()){
                    scenarioId = textField.getText();
                    window.close();
                }
                else{
                    Alert a = new Alert(Alert.AlertType.ERROR, "No scenario found for ID specified. " +
                            "Please make sure the necessary airport and setup files exist in " +
                            "\"Multimedia-Airport/src/main/java/gr/ntua/multimediaproject/medialab/\"");
                    a.show();
                }
            }
        });

        VBox layout = new VBox(10);

        layout.getChildren().addAll(label, textField, loadButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return scenarioId;
    }
}
