package gr.ntua.multimediaproject.ui.scenes;

import gr.ntua.multimediaproject.commonutils.AbstractHelper;
import gr.ntua.multimediaproject.flights.Flight;
import gr.ntua.multimediaproject.ui.MainWindow;
import gr.ntua.multimediaproject.ui.Sizer;
import gr.ntua.multimediaproject.ui.TopText;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;

public class FlightsScene extends Scene {
    private MainWindow mainWindow;
    private GridPane gridPane;

    public FlightsScene(StackPane stackPane, double width, double height, MainWindow mainWindow){
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
        refreshFlightsScene();
    }

    public void refreshFlightsScene(){
        gridPane.getChildren().clear();
        Text idTitleText = TopText.makeBlueText("Flight ID");
        Text cityTitleText = TopText.makeBlueText("City");
        Text flightTitleTypeText = TopText.makeBlueText("Flight Type");
        Text planeTypeTitleText = TopText.makeBlueText("Plane Type");
        Text stateTitleText = TopText.makeBlueText("State");
        Text dockingSpaceIdTitleText = TopText.makeBlueText("Docking Space ID");
        Text predictedDepartureTimeTitleText = TopText.makeBlueText("Predicted Departure Time");
        GridPane.setConstraints(idTitleText, 0, 0);
        GridPane.setConstraints(cityTitleText, 1, 0);
        GridPane.setConstraints(flightTitleTypeText, 2, 0);
        GridPane.setConstraints(planeTypeTitleText, 3, 0);
        GridPane.setConstraints(stateTitleText, 4, 0);
        GridPane.setConstraints(dockingSpaceIdTitleText, 5, 0);
        GridPane.setConstraints(predictedDepartureTimeTitleText, 6, 0);
        gridPane.getChildren().addAll(idTitleText, cityTitleText, flightTitleTypeText, planeTypeTitleText, stateTitleText,
                dockingSpaceIdTitleText, predictedDepartureTimeTitleText);

        int rowCounter = 1;
        rowCounter = addFlightsFromFlightList(mainWindow.getAirport().getHoldingFlightsList(), rowCounter);
        addFlightsFromFlightList(mainWindow.getAirport().getLandingOrParkedFlightsList(), rowCounter);
    }

    private int addFlightsFromFlightList(List<Flight> flightList, int rowCounter){
        for(Flight flight : flightList){
            Text idText = new Text(flight.getId());
            Text cityText = new Text(flight.getCity());
            Text flightTypeText = new Text(flight.getFlightType().toString());
            Text planeTypeText = new Text(flight.getPlaneType().toString());
            Text stateText = new Text(flight.getFlightState().toString());
            Text dockingSpaceIdText = new Text((flight.getDockingSpace() == null ? "-" : flight.getDockingSpace().getId()));
            Text predictedDepartureTimeText = new Text(flight.getDockingSpace() == null ?
                    "-" : AbstractHelper.minutesToHoursMinutes(
                            flight.getActualTakeoffTimeInApplicationMinutes() - flight.getDelayTimeInMinutes()));
            if("LANDING".equals(stateText.getText())){
                stateText.setFill(Color.YELLOW);
            }
            else if("PARKED".equals(stateText.getText())){
                stateText.setFill(Color.GREEN);
            }
            else{
                stateText.setFill(Color.RED);
            }
            GridPane.setConstraints(idText, 0, rowCounter);
            GridPane.setConstraints(cityText, 1, rowCounter);
            GridPane.setConstraints(flightTypeText, 2, rowCounter);
            GridPane.setConstraints(planeTypeText, 3, rowCounter);
            GridPane.setConstraints(stateText, 4, rowCounter);
            GridPane.setConstraints(dockingSpaceIdText, 5, rowCounter);
            GridPane.setConstraints(predictedDepartureTimeText, 6, rowCounter);
            gridPane.getChildren().addAll(idText, cityText, flightTypeText, planeTypeText, stateText,
                    dockingSpaceIdText, predictedDepartureTimeText);
            rowCounter++;
        }
        return rowCounter;
    }
}
