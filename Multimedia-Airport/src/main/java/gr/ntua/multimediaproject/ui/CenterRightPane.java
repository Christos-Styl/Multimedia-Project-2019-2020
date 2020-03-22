package gr.ntua.multimediaproject.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class CenterRightPane extends GridPane {
    private MainCenterPane mainCenterPane;

    public CenterRightPane(MainCenterPane mainCenterPane){
        this.mainCenterPane = mainCenterPane;
        this.setMinSize(Sizer.getCenterRightPaneWidth(),Sizer.getMainCenterPaneHeight());
        this.setMaxSize(Sizer.getCenterRightPaneWidth(),Sizer.getMainCenterPaneHeight());
        this.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setHgap(25);
        setVgap(5);
        setPadding(new Insets(5,5,5,5));

        Text flightIdText = new Text("Flight ID:");
        flightIdText.setFill(Color.BLUE);
        GridPane.setConstraints(flightIdText, 0, 0);

        TextField flightIdTextField = new TextField();
        flightIdTextField.setPromptText("e.x. A12");
        GridPane.setConstraints(flightIdTextField, 1, 0);

        Text cityText = new Text("City:");
        cityText.setFill(Color.BLUE);
        GridPane.setConstraints(cityText, 0, 1);

        TextField cityTextField = new TextField();
        cityTextField.setPromptText("e.x. Athens");
        GridPane.setConstraints(cityTextField, 1, 1);

        Text flightTypeText = new Text("Flight Type (numerical):");
        flightTypeText.setFill(Color.BLUE);
        GridPane.setConstraints(flightTypeText, 0, 2);

        TextField flightTypeTextField = new TextField();
        flightTypeTextField.setPromptText("e.x. 2");
        GridPane.setConstraints(flightTypeTextField, 1, 2);

        Text planeTypeText = new Text("Plane Type (numerical):");
        planeTypeText.setFill(Color.BLUE);
        GridPane.setConstraints(planeTypeText, 0, 3);

        TextField planeTypeTextField = new TextField();
        planeTypeTextField.setPromptText("e.x. 3");
        GridPane.setConstraints(planeTypeTextField, 1, 3);

        Text predictedParkingDurationText = new Text("Predicted Parking Duration (minutes):");
        predictedParkingDurationText.setFill(Color.BLUE);
        GridPane.setConstraints(predictedParkingDurationText, 0, 4);

        TextField predictedParkingDurationTextField = new TextField();
        predictedParkingDurationTextField.setPromptText("e.x. 30");
        GridPane.setConstraints(predictedParkingDurationTextField, 1, 4);

        Text servicesText = new Text("Required Services:");
        servicesText.setFill(Color.BLUE);
        GridPane.setConstraints(servicesText, 0, 5);

        CheckBox cleaningCheckBox = new CheckBox("Cleaning");
        GridPane.setConstraints(cleaningCheckBox, 0, 6);
        CheckBox loadingUnloadingCheckBox = new CheckBox("Loading/Unloading");
        GridPane.setConstraints(loadingUnloadingCheckBox, 0, 7);
        CheckBox passengerTransitCheckBox = new CheckBox("Passenger Transit");
        GridPane.setConstraints(passengerTransitCheckBox, 0, 8);
        CheckBox refuelingCheckBox = new CheckBox("Refueling");
        GridPane.setConstraints(refuelingCheckBox, 0, 9);

        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, 15);
        submitButton.setOnAction(event -> {
            String id = flightIdTextField.getText();
            String city = cityTextField.getText();
            String flightType = flightTypeTextField.getText();
            String planeType = planeTypeTextField.getText();
            String predictedParkingDurationString = predictedParkingDurationTextField.getText();
            boolean requiresCleaningService = cleaningCheckBox.isSelected();
            boolean requiresLoadingUnloadingService = loadingUnloadingCheckBox.isSelected();
            boolean requiresPassengerTransitService = passengerTransitCheckBox.isSelected();
            boolean requiresRefuelingService = refuelingCheckBox.isSelected();
            if("".equals(id.trim()) || "".equals(city.trim()) || "".equals(flightType.trim()) || "".equals(planeType.trim()) || "".equals(predictedParkingDurationString.trim())){
                Alert a = new Alert(Alert.AlertType.ERROR, "You must fill every field to submit a new flight.");
                a.show();
                return;
            }
            if((!"1".equals(flightType) && !"2".equals(flightType) && !"3".equals(flightType)) || (!"1".equals(planeType) && !"2".equals(planeType) && !"3".equals(planeType))){
                Alert a = new Alert(Alert.AlertType.ERROR, "Flight and Plane types must be 1, 2 or 3.");
                a.show();
                return;
            }
            int predictedParkingDuration;
            try{
                predictedParkingDuration = Integer.parseInt(predictedParkingDurationString);

            }
            catch (NumberFormatException ex){
                Alert a = new Alert(Alert.AlertType.ERROR, "Predicted parking duration in minutes must be a number.");
                a.show();
                return;
            }
            if(predictedParkingDuration <= 0 || predictedParkingDuration > 600){
                Alert a = new Alert(Alert.AlertType.ERROR, "Predicted parking duration in minutes must be a" +
                        "positive integer and lower than 600 (minutes).");
                a.show();
                return;
            }
            try {
                mainCenterPane.getAirport().createNewFlight(id, city, flightType, planeType, predictedParkingDuration,
                        requiresCleaningService, requiresLoadingUnloadingService, requiresPassengerTransitService, requiresRefuelingService);
            } catch (Exception e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Error while creating flight.\n\nException message: " + e.getMessage());
                a.show();
                return;
            }
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Flight with id " + id + " is being added.");
            a.show();
        });

        getChildren().addAll(flightIdText, flightIdTextField, cityText, cityTextField, flightTypeText, flightTypeTextField,
                planeTypeText, planeTypeTextField, predictedParkingDurationText, predictedParkingDurationTextField,
                servicesText, cleaningCheckBox, loadingUnloadingCheckBox, passengerTransitCheckBox, refuelingCheckBox,
                submitButton);
    }
}
