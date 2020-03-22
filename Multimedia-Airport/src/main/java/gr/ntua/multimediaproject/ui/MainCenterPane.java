package gr.ntua.multimediaproject.ui;

import gr.ntua.multimediaproject.airport.AirportSingleton;
import gr.ntua.multimediaproject.ui.scenes.MainScene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MainCenterPane extends BorderPane{
    private MainScene mainScene;
    private CenterCenterPane centerCenterPane;
    private CenterRightPane centerRightPane;

    public MainCenterPane(MainScene mainScene){
        this.mainScene = mainScene;
        this.setMinSize(Sizer.getWindowWidth(),Sizer.getMainCenterPaneHeight());
        this.setMaxSize(Sizer.getWindowWidth(),Sizer.getMainCenterPaneHeight());
        this.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setTop(null);
        this.setBottom(null);
        this.setLeft(null);
        centerCenterPane = new CenterCenterPane(this);
        this.setCenter(centerCenterPane);
        centerRightPane = new CenterRightPane(this);
        this.setRight(centerRightPane);
    }

    public AirportSingleton getAirport(){
        return mainScene.getAirport();
    }

    public void startAirport(){
        centerCenterPane.startAirport();
    }

    public void refreshPane(){
        centerCenterPane.refreshPane();
    }
}
