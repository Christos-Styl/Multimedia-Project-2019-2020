package gr.ntua.multimediaproject.ui;

import gr.ntua.multimediaproject.airport.AirportSingleton;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AirportGui extends Application {
    private Scene mainScene;
//    private static AirportSingleton airport;

    @Override
    public void start(Stage primaryStage) throws Exception {
        AirportSingleton airport = AirportSingleton.getInstance();
        tryInitializeAirportContentFromFiles(airport);
        MainWindow window = new MainWindow("MediaLab Airport", airport);

//        BorderPane borderPane = new BorderPane();
//
//
//        FlowPane center = new FlowPane();
//        center.setBorder(new Border(new BorderStroke(Color.BLACK,
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//        center.setMinSize(1000,Sizer.getCenterPaneHeight());
//        center.setMaxSize(1000,Sizer.getCenterPaneHeight());
//        FlowPane bottom = new FlowPane();
//        bottom.setBorder(new Border(new BorderStroke(Color.BLACK,
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//
//
//        bottom.setMinSize(1000,Sizer.getBottomPaneHeight());
//        center.getChildren().add(new Button("yo"));
//        bottom.getChildren().add(new Button("123"));
//
//        borderPane.setTop(top);
//        borderPane.setBottom(bottom);
//        borderPane.setCenter(center);
//        mainScene = new Scene(borderPane, 1000, 700);
//        borderPane.setLeft(null);
//        borderPane.setRight(null);
//        window.setScene(mainScene);
        window.show();
    }

    private void tryInitializeAirportContentFromFiles(AirportSingleton airport){
        try{
            airport.initializeAirportContentFromFiles();
        }
        catch(Exception ex){
            System.out.println(airport.getTimeElapsedInMinutes() + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
        }
    }

    public static void main(String[] args){
//        airport = AirportSingleton.getInstance();

//        airport.clearAirport();
        launch(args);
    }
}
