package gr.ntua.multimediaproject.ui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TopText {
    public static Text makeOrangeText(String content){
        Text text = new Text(content);
        text.setFill(Color.ORANGE);
        text.setFont(Font.font ("Verdana", 14));
        text.setStyle("-fx-font-weight: bold");
        return text;
    }

    public static Text makeBlueText(String content){
        Text text = new Text(content);
        text.setFill(Color.BLUE);
        text.setFont(Font.font ("Verdana", 14));
        text.setStyle("-fx-font-weight: bold");
        return text;
    }
}
