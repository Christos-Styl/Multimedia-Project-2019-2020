package gr.ntua.multimediaproject.ui.popups;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class DetailsPopUp {
    public static void display(Scene scene, String title) {
        if(scene.getWindow() != null) {
            ((Stage) scene.getWindow()).close();
        }
        Stage window = new Stage();
        window.setTitle(title);
        window.setScene(scene);
        window.show();
    }
}
