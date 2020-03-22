package gr.ntua.multimediaproject.ui;

import gr.ntua.multimediaproject.airport.AirportSingleton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.text.html.StyleSheet;

public class MainWindow extends Stage {
    private AirportSingleton airport;

    public MainWindow(String title, AirportSingleton airport){
        this.airport = airport;
        System.out.println("first");
        this.setTitle(title);
        this.centerOnScreen();
        this.setOnCloseRequest(event -> {
            event.consume();
            closeStage();
        });
        MainScene mainScene = new MainScene(new BorderPane(), Sizer.getWindowWidth(), Sizer.getWindowHeight(), this);
        setScene(mainScene);
        System.out.println("TEST TEST: " + airport);
    }

    private void closeStage(){
        boolean answer = ConfirmBox.display("Title", "Are you sure you want to close the application?");
        if(answer){
            if(airport != null){
                airport.clearAirport();
            }
            System.out.println("INFO: AirportGui closing...");
            this.close();
        }
    }

    public AirportSingleton getAirport() {
        return airport;
    }

    public void setAirport(AirportSingleton airport) {
        this.airport = airport;
    }
}
