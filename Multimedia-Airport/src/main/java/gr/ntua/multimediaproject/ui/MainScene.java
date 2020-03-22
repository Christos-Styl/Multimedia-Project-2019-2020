package gr.ntua.multimediaproject.ui;

import gr.ntua.multimediaproject.airport.AirportSingleton;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class MainScene extends Scene {
    private MainWindow mainWindow;

    MainScene(BorderPane borderpane, double width, double height, MainWindow mainWindow){
        super(borderpane, width, height);
        this.mainWindow = mainWindow;
        TopPane top = new TopPane(this);
        borderpane.setTop(top);
    }

    public AirportSingleton getAirport(){
        return mainWindow.getAirport();
    }

    public void startAirport(){
        mainWindow.startAirport();
    }
}
