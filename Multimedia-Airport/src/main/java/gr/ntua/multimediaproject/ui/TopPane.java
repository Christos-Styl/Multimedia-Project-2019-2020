package gr.ntua.multimediaproject.ui;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TopPane extends FlowPane{
    private MainScene mainScene;
    private Text numberOfHoldingFlightsText = TopText.makeText("Incoming Flights: --");
    private Text totalNumberOfDockingSpacesText = TopText.makeText("Total Docking Spaces: --");
    private Text numberOfFlightsDepartingIn10MinutesText = TopText.makeText("Flights departing in 10': --");
    private Text totalMoneyEarnedText = TopText.makeText("Total Money: --");
    private Text timeElapsedInMinutesText = TopText.makeText("Time elapsed: --");

    public TopPane(MainScene mainScene){
        this.mainScene = mainScene;
        this.setMinSize(Sizer.getWindowWidth(),Sizer.getTopPaneHeight());
        this.setMaxSize(Sizer.getWindowWidth(),Sizer.getTopPaneHeight());
        this.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setVgap(5);
        this.setHgap(30);
        AirportMenuBar menu = new AirportMenuBar(this);
        this.getChildren().addAll(menu,numberOfHoldingFlightsText, totalNumberOfDockingSpacesText, numberOfFlightsDepartingIn10MinutesText,
                totalMoneyEarnedText, timeElapsedInMinutesText);
        refreshText();
    }

    public void refreshText(){
        System.out.println("hello");
        numberOfHoldingFlightsText.setText("Incoming Flights: " + (mainScene.getAirport() != null ?
                mainScene.getAirport().getHoldingFlightsList().size() : "--"));
        totalNumberOfDockingSpacesText.setText("Total Docking Spaces: " + (mainScene.getAirport() != null ?
                mainScene.getAirport().getDockingStationList().size() : "--"));
        numberOfFlightsDepartingIn10MinutesText.setText("Flights departing in 10': " + (mainScene.getAirport() != null ?
                mainScene.getAirport().getFlightsDepartingIn10Minutes().size() : "--"));
        totalMoneyEarnedText.setText("Total Money: " + (mainScene.getAirport() != null ?
                mainScene.getAirport().getTotalMoneyEarned() : "--"));
        //TODO fix time
        timeElapsedInMinutesText.setText("Time elapsed: " + (mainScene.getAirport() != null ?
                mainScene.getAirport().getTimeElapsedInMinutes() : "--"));

    }

    public void startAirport(){
        mainScene.startAirport();
        refreshText();
    }
}
