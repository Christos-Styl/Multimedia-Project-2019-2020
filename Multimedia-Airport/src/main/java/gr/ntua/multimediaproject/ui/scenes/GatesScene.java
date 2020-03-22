package gr.ntua.multimediaproject.ui.scenes;

import gr.ntua.multimediaproject.dockingstations.DockingSpace;
import gr.ntua.multimediaproject.dockingstations.DockingStation;
import gr.ntua.multimediaproject.ui.MainWindow;
import gr.ntua.multimediaproject.ui.Sizer;
import gr.ntua.multimediaproject.ui.TopText;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GatesScene extends Scene {
    private MainWindow mainWindow;
    private GridPane gridPane;

    public GatesScene(StackPane stackPane, double width, double height, MainWindow mainWindow){
        super(stackPane, width, height);
        this.mainWindow = mainWindow;
        stackPane.setMinSize(Sizer.getDetailsPopUpWidth(), Sizer.getDetailsPopUpHeight());
        stackPane.setMaxSize(Sizer.getDetailsPopUpWidth(), Sizer.getDetailsPopUpHeight());
        stackPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMinSize(Sizer.getDetailsPopUpWidth(), Sizer.getDetailsPopUpHeight());
        scrollPane.setMaxSize(Sizer.getDetailsPopUpWidth(), Sizer.getDetailsPopUpHeight());
        scrollPane.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.ALWAYS);

        gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(50);
        gridPane.setMinWidth(Sizer.getDetailsPopUpWidth());
        gridPane.setMaxWidth(Sizer.getDetailsPopUpWidth());
        gridPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        scrollPane.setContent(gridPane);
        stackPane.getChildren().add(scrollPane);
        refreshGatesScene();
    }

    public void refreshGatesScene(){
        gridPane.getChildren().clear();
        Text idTitleText = TopText.makeBlueText("Docking Space ID");
        Text stateTitleText = TopText.makeBlueText("State");
        Text flightIdTitleText = TopText.makeBlueText("Flight ID");
        Text predictedDepartureTimeTitleText = TopText.makeBlueText("Predicted Departure Time");
        GridPane.setConstraints(idTitleText, 0, 0);
        GridPane.setConstraints(stateTitleText, 1, 0);
        GridPane.setConstraints(flightIdTitleText, 2, 0);
        GridPane.setConstraints(predictedDepartureTimeTitleText, 3, 0);
        gridPane.getChildren().addAll(idTitleText, stateTitleText, flightIdTitleText, predictedDepartureTimeTitleText);

        int rowCounter = 1;
        for(DockingStation dockingStation : mainWindow.getAirport().getDockingStationList()){
            for(DockingSpace dockingSpace : dockingStation.getDockingSpaceList()){
                Text idText = new Text(dockingSpace.getId());
                Text stateText = new Text(dockingSpace.getFlight() == null ? "Empty" : "Taken");
                stateText.setFill(stateText.getText().equals("Empty") ? Color.GREEN : Color.RED);
                Text flightIdText = new Text(dockingSpace.getFlight() == null ? "-" : dockingSpace.getFlight().getId());
                Text predictedDepartureTimeText = new Text(dockingSpace.getFlight() == null ? "-" :
                        Integer.toString(dockingSpace.getFlight().getActualTakeoffTimeInApplicationMinutes() - dockingSpace.getFlight().getDelayTimeInMinutes()));
                GridPane.setConstraints(idText, 0, rowCounter);
                GridPane.setConstraints(stateText, 1, rowCounter);
                GridPane.setConstraints(flightIdText, 2, rowCounter);
                GridPane.setConstraints(predictedDepartureTimeText, 3, rowCounter);
                gridPane.getChildren().addAll(idText, stateText, flightIdText, predictedDepartureTimeText);
                rowCounter++;
            }
        }
    }
}
