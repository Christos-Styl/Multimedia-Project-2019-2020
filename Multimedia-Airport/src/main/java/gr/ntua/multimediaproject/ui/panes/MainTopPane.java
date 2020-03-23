package gr.ntua.multimediaproject.ui.panes;

import gr.ntua.multimediaproject.commonutils.AbstractHelper;
import gr.ntua.multimediaproject.dockingstations.DockingStation;
import gr.ntua.multimediaproject.ui.AirportMenuBar;
import gr.ntua.multimediaproject.ui.Sizer;
import gr.ntua.multimediaproject.ui.TopText;
import gr.ntua.multimediaproject.ui.scenes.MainScene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MainTopPane extends FlowPane{
    private MainScene mainScene;
    private Text numberOfHoldingFlightsText = TopText.makeOrangeText("Incoming Flights: --");
    private Text totalNumberOfDockingSpacesText = TopText.makeOrangeText("Total Docking Spaces: --");
    private Text numberOfFlightsDepartingIn10MinutesText = TopText.makeOrangeText("Flights departing in 10': --");
    private Text totalMoneyEarnedText = TopText.makeOrangeText("Total Money: --");
    private Text timeElapsedInMinutesText = TopText.makeOrangeText("Time elapsed: --");
    private Text scenarioIdText = TopText.makeOrangeText("Scenario ID: --");

    public MainTopPane(MainScene mainScene){
        this.mainScene = mainScene;
        this.setMinSize(Sizer.getWindowWidth(),Sizer.getMainTopPaneHeight());
        this.setMaxSize(Sizer.getWindowWidth(),Sizer.getMainTopPaneHeight());
        this.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setVgap(5);
        this.setHgap(30);
        AirportMenuBar menu = new AirportMenuBar(this);
        this.getChildren().addAll(menu,numberOfHoldingFlightsText, totalNumberOfDockingSpacesText, numberOfFlightsDepartingIn10MinutesText,
                totalMoneyEarnedText, timeElapsedInMinutesText, scenarioIdText);
        refreshText();
    }

    public void refreshText(){
        int numberOfDockingStations = 0;
        if(mainScene.getAirport() != null){
            for(DockingStation dockingStation : mainScene.getAirport().getDockingStationList()){
                numberOfDockingStations += dockingStation.getDockingSpaceList().size();
            }
        }
        numberOfHoldingFlightsText.setText("Incoming Flights: " + (mainScene.getAirport() != null ?
                mainScene.getAirport().getHoldingFlightsList().size() : "--"));
        totalNumberOfDockingSpacesText.setText("Total Docking Spaces: " + (mainScene.getAirport() != null ?
                numberOfDockingStations : "--"));
        numberOfFlightsDepartingIn10MinutesText.setText("Flights departing in 10': " + (mainScene.getAirport() != null ?
                mainScene.getAirport().getFlightsDepartingIn10Minutes().size() : "--"));
        totalMoneyEarnedText.setText("Total Money: " + (mainScene.getAirport() != null ?
                mainScene.getAirport().getTotalMoneyEarned() : "--"));
        timeElapsedInMinutesText.setText("Time elapsed: " + (mainScene.getAirport() != null ?
                AbstractHelper.minutesToHoursMinutes(mainScene.getAirport().getTimeElapsedInMinutes()) : "--"));
        scenarioIdText.setText("Scenario ID: " + mainScene.getScenarioId());
    }

    public void startAirport(){
        mainScene.startAirport();
        refreshPane();
    }

    public void refreshPane(){
        refreshText();
    }

    public void loadScenario(String scenarioId){
        mainScene.loadScenario(scenarioId);
        scenarioIdText.setText("Scenario ID: " + mainScene.getScenarioId());
    }

    public void closeStage(){
        mainScene.closeStage();
    }

    public void showGates(){
        mainScene.showGates();
    }

    public void showFlights(){
        mainScene.showFlights();
    }

    public void showDelayedFlights(){
        mainScene.showDelayedFlights();
    }

    public void showHoldingFlights(){
        mainScene.showHoldingFlights();
    }

    public void showNextDepartures(){
        mainScene.showNextDepartures();
    }
}
