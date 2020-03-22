package gr.ntua.multimediaproject.ui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class AirportMenuBar extends MenuBar {
    private TopPane top;

    public AirportMenuBar(TopPane top){
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
    }
}
