package gr.ntua.multimediaproject.ui.scenes;

import gr.ntua.multimediaproject.airport.AirportSingleton;
import gr.ntua.multimediaproject.commonutils.AbstractHelper;
import gr.ntua.multimediaproject.ui.MainBottomPane;
import gr.ntua.multimediaproject.ui.MainCenterPane;
import gr.ntua.multimediaproject.ui.MainTopPane;
import gr.ntua.multimediaproject.ui.MainWindow;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class MainScene extends Scene {
    private MainWindow mainWindow;
    MainTopPane top;
    MainCenterPane center;
    MainBottomPane bottom;

    public MainScene(BorderPane borderpane, double width, double height, MainWindow mainWindow){
        super(borderpane, width, height);
        this.mainWindow = mainWindow;
        top = new MainTopPane(this);
        borderpane.setTop(top);
        center = new MainCenterPane(this);
        borderpane.setCenter(center);
        bottom = new MainBottomPane(this);
        borderpane.setBottom(bottom);
    }

    public AirportSingleton getAirport(){
        return mainWindow.getAirport();
    }

    public void startAirport(){
        mainWindow.startAirport();
        center.startAirport();
    }

    public void refreshScene(){
        top.refreshPane();
        center.refreshPane();
    }

    public void loadScenario(String scenarioId){
        mainWindow.loadScenario(scenarioId);
        Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(mainWindow.getAirport().getTimeElapsedInMinutes())
                + " - INFO: Scenario with id " + scenarioId + " loaded."));
    }

    public String getScenarioId(){
        return mainWindow.getScenarioId();
    }

    public void closeStage(){
        mainWindow.closeStage();
    }

    public void logMessage(String message){
        bottom.addMessage(message);
    }

    public void showGates(){
        mainWindow.showGates();
    }
}
