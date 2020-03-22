package gr.ntua.multimediaproject.ui;

public class Sizer {
    private static final int windowWidth =1000;
    private static final int windowHeight =800;
    //top + center + bottom heights must equal window height
    private static final int topPaneHeight = 100;
    private static final int centerPaneHeight = 500;
    private static final int bottomPaneHeight = 200;

    public static int getTopPaneHeight() {
        return topPaneHeight;
    }

    public static int getCenterPaneHeight() {
        return centerPaneHeight;
    }

    public static int getBottomPaneHeight() {
        return bottomPaneHeight;
    }

    public static int getWindowWidth() {
        return windowWidth;
    }

    public static int getWindowHeight() {
        return windowHeight;
    }
}
