package gr.ntua.multimediaproject.ui;

public class Sizer {
    private static final int windowWidth =1000;
    private static final int windowHeight =800;

    //top + center + bottom heights must equal window height
    private static final int mainTopPaneHeight = 100;
    private static final int mainCenterPaneHeight = 500;
    private static final int mainBottomPaneHeight = 200;

    //centerCenter + centerRight widths must equal windowWidth
    private static final int centerCenterPaneWidth = 700;
    private static final int centerRightPaneWidth = 300;

    private static final int detailsPopUpWidth = 700;
    private static final int detailsPopUpHeight = 500;

    public static int getMainTopPaneHeight() {
        return mainTopPaneHeight;
    }

    public static int getMainCenterPaneHeight() {
        return mainCenterPaneHeight;
    }

    public static int getMainBottomPaneHeight() {
        return mainBottomPaneHeight;
    }

    public static int getWindowWidth() {
        return windowWidth;
    }

    public static int getWindowHeight() {
        return windowHeight;
    }

    public static int getCenterCenterPaneWidth() {
        return centerCenterPaneWidth;
    }

    public static int getCenterRightPaneWidth() {
        return centerRightPaneWidth;
    }

    public static int getDetailsPopUpWidth() {
        return detailsPopUpWidth;
    }

    public static int getDetailsPopUpHeight() {
        return detailsPopUpHeight;
    }
}
