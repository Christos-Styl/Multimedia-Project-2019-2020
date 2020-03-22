package gr.ntua.multimediaproject.ui;

import gr.ntua.multimediaproject.dockingstations.DockingSpace;
import gr.ntua.multimediaproject.dockingstations.DockingStation;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.List;


public class CenterCenterPane extends FlowPane{
    private MainCenterPane mainCenterPane;
    private ScrollPane scrollPane;

    public CenterCenterPane(MainCenterPane mainCenterPane){
        this.mainCenterPane = mainCenterPane;
        this.setOrientation(Orientation.VERTICAL);
        this.setMinSize(Sizer.getCenterCenterPaneWidth(),Sizer.getMainCenterPaneHeight());
        this.setMaxSize(Sizer.getCenterCenterPaneWidth(),Sizer.getMainCenterPaneHeight());
        Text text = TopText.makeOrangeText("Docking Stations are displayed below. Each Docking Station has a number of " +
                "Docking Spaces, each with its own Docking Space ID (top) and flight ID (bottom). Taken Docking Spaces " +
                "are colored red, while empty ones are colored green.");
        text.setWrappingWidth(Sizer.getCenterCenterPaneWidth());
        scrollPane = new ScrollPane();
        scrollPane.setMinSize(Sizer.getCenterCenterPaneWidth(),Sizer.getMainCenterPaneHeight() -
                text.getLayoutBounds().getHeight() - 2);
        scrollPane.setMaxSize(Sizer.getCenterCenterPaneWidth(),Sizer.getMainCenterPaneHeight() -
                text.getLayoutBounds().getHeight() - 2);
        this.getChildren().addAll(text, scrollPane);
    }

    public void refreshPane(){
        createDockingSpaceScrollPane(mainCenterPane.getAirport().getDockingStationList());
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    }

    private void createDockingSpaceScrollPane(List<DockingStation> dockingStationList){
        GridPane dockingSpaceGrid = new GridPane();
        dockingSpaceGrid.setHgap(8);
        dockingSpaceGrid.setVgap(20);
        dockingSpaceGrid.setPadding(new Insets(5,5,5,5));
        int dockingStationCounter = 0;
        for(DockingStation dockingStation : dockingStationList){
            Text name = new Text(dockingStation.getDockingStationType());
            dockingSpaceGrid.getChildren().add(name);
            GridPane.setConstraints(name, 0, dockingStationCounter);
            int columnCounter = 1;
            for(DockingSpace dockingSpace : dockingStation.getDockingSpaceList()) {
                FlowPane outerSingleDockingStationPane = new FlowPane();
                StackPane dockingSpaceStackPane = new StackPane();
                Text dockingSpaceIdText = new Text(dockingSpace.getId());
                Rectangle dockingSpaceBox = new Rectangle(20, 20);
                dockingSpaceBox.setFill(Color.MOCCASIN);
                if(dockingSpaceIdText.getLayoutBounds().getWidth() > dockingSpaceBox.getWidth()) {
                    dockingSpaceBox.setWidth(dockingSpaceIdText.getLayoutBounds().getWidth());
                }
                dockingSpaceStackPane.getChildren().addAll(dockingSpaceBox, dockingSpaceIdText);

                StackPane flightStackPane = new StackPane();
                Rectangle flightBox = new Rectangle(20, 20);
                Text flightIdText = new Text("-");
                if(dockingSpace.getFlight() == null){
                    flightBox.setFill(Color.GREEN);
                }
                else{
                    flightBox.setFill(Color.ORANGERED);
                    flightIdText.setText(dockingSpace.getFlight().getId());
                    if(flightIdText.getLayoutBounds().getWidth() > flightBox.getWidth()) {
                        flightBox.setWidth(flightIdText.getLayoutBounds().getWidth());
                    }
                }
                flightStackPane.getChildren().addAll(flightBox, flightIdText);
                outerSingleDockingStationPane.getChildren().addAll(dockingSpaceStackPane, flightStackPane);
                outerSingleDockingStationPane.setAlignment(Pos.CENTER);
                GridPane.setConstraints(outerSingleDockingStationPane, columnCounter, dockingStationCounter);
                dockingSpaceGrid.getChildren().add(outerSingleDockingStationPane);
                columnCounter++;
            }
            dockingStationCounter++;
        }
        scrollPane.setContent(dockingSpaceGrid);
    }

    public void startAirport(){
        refreshPane();
    }
}
