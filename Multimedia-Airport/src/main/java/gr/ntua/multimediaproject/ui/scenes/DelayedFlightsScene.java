package gr.ntua.multimediaproject.ui.scenes;

import gr.ntua.multimediaproject.commonutils.AbstractHelper;
import gr.ntua.multimediaproject.flights.Flight;
import gr.ntua.multimediaproject.flights.FlightState;
import gr.ntua.multimediaproject.ui.MainWindow;
import gr.ntua.multimediaproject.ui.Sizer;
import gr.ntua.multimediaproject.ui.TopText;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;

public class DelayedFlightsScene extends Scene {
    private MainWindow mainWindow;
    private GridPane gridPane;

    public DelayedFlightsScene(StackPane stackPane, double width, double height, MainWindow mainWindow){
        super(stackPane, width, height);
        this.mainWindow = mainWindow;
        stackPane.setMinSize(Sizer.getFlightsDetailsPopUpWidth(), Sizer.getFlightsDetailsPopUpHeight());
        stackPane.setMaxSize(Sizer.getFlightsDetailsPopUpWidth(), Sizer.getFlightsDetailsPopUpHeight());
        stackPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMinSize(Sizer.getFlightsDetailsPopUpWidth(), Sizer.getFlightsDetailsPopUpHeight());
        scrollPane.setMaxSize(Sizer.getFlightsDetailsPopUpWidth(), Sizer.getFlightsDetailsPopUpHeight());
        scrollPane.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.ALWAYS);

        gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(30);
        gridPane.setMinWidth(Sizer.getFlightsDetailsPopUpWidth());
        gridPane.setMaxWidth(Sizer.getFlightsDetailsPopUpWidth());
        gridPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        scrollPane.setContent(gridPane);
        stackPane.getChildren().add(scrollPane);
        refreshDelayedFlightsScene();
    }

    public void refreshDelayedFlightsScene(){
        gridPane.getChildren().clear();
        Text idTitleText = TopText.makeBlueText("Flight ID");
        Text flightTitleTypeText = TopText.makeBlueText("Flight Type");
        Text planeTypeTitleText = TopText.makeBlueText("Plane Type");
        Text dockingSpaceIdTitleText = TopText.makeBlueText("Docking Space ID");
        Text predictedDepartureTimeTitleText = TopText.makeBlueText("Predicted Departure Time");
        GridPane.setConstraints(idTitleText, 0, 0);
        GridPane.setConstraints(flightTitleTypeText, 1, 0);
        GridPane.setConstraints(planeTypeTitleText, 2, 0);
        GridPane.setConstraints(dockingSpaceIdTitleText, 3, 0);
        GridPane.setConstraints(predictedDepartureTimeTitleText, 4, 0);
        gridPane.getChildren().addAll(idTitleText, flightTitleTypeText, planeTypeTitleText,
                dockingSpaceIdTitleText, predictedDepartureTimeTitleText);

        int rowCounter = 1;
        addFlightsFromFlightList(mainWindow.getAirport().getLandingOrParkedFlightsList(), rowCounter);
    }

    private void addFlightsFromFlightList(List<Flight> flightList, int rowCounter){
        for(Flight flight : flightList){
            if(flight.getFlightState() == FlightState.PARKED &&
                    flight.getActualTakeoffTimeInApplicationMinutes() - flight.getDelayTimeInMinutes() < mainWindow.getAirport().getTimeElapsedInMinutes()) {
                Text idText = new Text(flight.getId());
                Text flightTypeText = new Text(flight.getFlightType().toString());
                Text planeTypeText = new Text(flight.getPlaneType().toString());
                Text dockingSpaceIdText = new Text((flight.getDockingSpace() == null ? "-" : flight.getDockingSpace().getId()));
                Text predictedDepartureTimeText = new Text(flight.getDockingSpace() == null ?
                        "-" : AbstractHelper.minutesToHoursMinutes(
                        flight.getActualTakeoffTimeInApplicationMinutes() - flight.getDelayTimeInMinutes()));
                GridPane.setConstraints(idText, 0, rowCounter);
                GridPane.setConstraints(flightTypeText, 1, rowCounter);
                GridPane.setConstraints(planeTypeText, 2, rowCounter);
                GridPane.setConstraints(dockingSpaceIdText, 3, rowCounter);
                GridPane.setConstraints(predictedDepartureTimeText, 4, rowCounter);
                gridPane.getChildren().addAll(idText, flightTypeText, planeTypeText,
                        dockingSpaceIdText, predictedDepartureTimeText);
                rowCounter++;
            }
        }
    }
}
