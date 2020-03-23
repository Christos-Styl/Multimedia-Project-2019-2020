package gr.ntua.multimediaproject.ui;

import gr.ntua.multimediaproject.airport.AirportSingleton;
import javafx.application.Application;
import javafx.stage.Stage;

public class AirportGui extends Application {

    @Override
    public void start(Stage primaryStage){
        AirportSingleton airport = AirportSingleton.getInstance();
        MainWindow window = new MainWindow("MediaLab Airport", airport);
        window.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
