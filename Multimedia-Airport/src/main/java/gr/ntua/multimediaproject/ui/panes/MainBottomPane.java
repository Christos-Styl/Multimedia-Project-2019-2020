package gr.ntua.multimediaproject.ui.panes;

import gr.ntua.multimediaproject.ui.Sizer;
import gr.ntua.multimediaproject.ui.scenes.MainScene;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class MainBottomPane extends FlowPane {
    private GridPane gridPane;
    private int gridRow;

    public MainBottomPane() {
        gridRow = 0;
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMinSize(Sizer.getWindowWidth() - 80,Sizer.getMainBottomPaneHeight());
        scrollPane.setMaxSize(Sizer.getWindowWidth() - 80,Sizer.getMainBottomPaneHeight());
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        scrollPane.setContent(gridPane);

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(event -> clear());

        this.setMinSize(Sizer.getWindowWidth(), Sizer.getMainBottomPaneHeight());
        this.setMaxSize(Sizer.getWindowWidth(), Sizer.getMainBottomPaneHeight());
        this.setOrientation(Orientation.HORIZONTAL);
        this.setHgap(20);
        this.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.getChildren().addAll(scrollPane, clearButton);
    }

    public void addMessage(String message){
        Text text = new Text(message);
        GridPane.setConstraints(text, 0, gridRow);
        gridPane.getChildren().add(text);
        gridRow++;
    }

    public void clear(){
        gridRow = 0;
        gridPane.getChildren().clear();
    }
}
