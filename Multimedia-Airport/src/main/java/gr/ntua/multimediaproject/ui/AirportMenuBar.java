package gr.ntua.multimediaproject.ui;

import gr.ntua.multimediaproject.ui.panes.MainTopPane;
import gr.ntua.multimediaproject.ui.popups.LoadPopUp;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class AirportMenuBar extends MenuBar {
    private MainTopPane top;

    public AirportMenuBar(MainTopPane top){
        this.top = top;
        Menu applicationMenu = new Menu("_Application");
        MenuItem start = new MenuItem("Start");
        MenuItem load = new MenuItem("Load...");
        MenuItem exit = new MenuItem("Exit");
        setApplicationMenuItemsOnAction(start, load, exit);
        applicationMenu.getItems().addAll(start,
                load,
                new SeparatorMenuItem(),
                exit);

        Menu detailsMenu = new Menu("_Details...");
        MenuItem gates = new MenuItem("Gates...");
        MenuItem flights = new MenuItem("Flights...");
        MenuItem delayed = new MenuItem("Delayed...");
        MenuItem holding = new MenuItem("Holding...");
        MenuItem nextDepartures = new MenuItem("Next Departures...");
        setDetailsMenuItemsOnAction(gates, flights, delayed, holding, nextDepartures);
        detailsMenu.getItems().addAll(gates,
                flights,
                delayed,
                holding,
                nextDepartures);

        getMenus().addAll(applicationMenu, detailsMenu);
        setMinWidth(Sizer.getWindowWidth());
        setMaxWidth(Sizer.getWindowWidth());
    }

    private void setApplicationMenuItemsOnAction(MenuItem start, MenuItem load, MenuItem exit){
        start.setOnAction(event -> top.startAirport());
        load.setOnAction(event -> loadScenario());
        exit.setOnAction(event -> closeStage());
    }

    private void setDetailsMenuItemsOnAction(MenuItem gates, MenuItem flights, MenuItem delayed, MenuItem holding,
                                             MenuItem nextDepartures){
        gates.setOnAction(event -> top.showGates());
        flights.setOnAction(event -> top.showFlights());
        delayed.setOnAction(event -> top.showDelayedFlights());
        holding.setOnAction(event -> top.showHoldingFlights());
        nextDepartures.setOnAction(event -> top.showNextDepartures());
    }

    private void loadScenario(){
        String scenarioId = LoadPopUp.display("Load Scenario ID", "Input a scenario ID below.");
        if (scenarioId != null) {
            top.loadScenario(scenarioId);
        }
    }

    public void closeStage(){
        top.closeStage();
    }
}
