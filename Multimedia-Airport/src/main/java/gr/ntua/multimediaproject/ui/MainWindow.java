package gr.ntua.multimediaproject.ui;

import gr.ntua.multimediaproject.airport.AirportSingleton;
import gr.ntua.multimediaproject.commonutils.AbstractHelper;
import gr.ntua.multimediaproject.ui.scenes.GatesScene;
import gr.ntua.multimediaproject.ui.scenes.MainScene;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainWindow extends Stage {
    private AirportSingleton airport;
    private String scenarioId;
    MainScene mainScene;
    GatesScene gatesScene;

    public MainWindow(String title, AirportSingleton airport){
        this.airport = airport;
        scenarioId = "default";
        airport.setMainWindow(this);
        this.setTitle(title);
        this.centerOnScreen();
        this.setOnCloseRequest(event -> {
            event.consume();
            closeStage();
        });
        mainScene = new MainScene(new BorderPane(), Sizer.getWindowWidth(), Sizer.getWindowHeight(), this);
        gatesScene = new GatesScene(new StackPane(), Sizer.getDetailsPopUpWidth(), Sizer.getDetailsPopUpHeight(), this);
        setScene(mainScene);
        System.out.println("TEST TEST: " + airport);
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    public void closeStage(){
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

    public void startAirport(){
        if(airport == null){
            airport = AirportSingleton.getInstance();
        }
        airport.clearAirport();
        try {
            Platform.runLater(() -> logMessage(AbstractHelper.minutesToHoursMinutes(airport.getTimeElapsedInMinutes())
                    + " - INFO: Running airport..."));
            airport.runAirport(scenarioId);
        }
        catch(Exception ex){
            System.out.println(airport.getTimeElapsedInMinutes() + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error loading file.\n\nException message: " + ex.getMessage());
            alert.show();
        }
    }

    public void refreshGui(){
        mainScene.refreshScene();
        gatesScene.refreshGatesScene();
    }

    public void loadScenario(String scenarioId){
        this.scenarioId = scenarioId;
    }

    public void logMessage(String message){
        mainScene.logMessage(message);
    }

    public void showGates(){
        GatesPopUp.display(gatesScene);
    }

    public void focusMainScene(){
        setScene(mainScene);
    }

}
