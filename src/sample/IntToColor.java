package sample;

import javafx.scene.paint.Color;

public class IntToColor {
    public static Color getColor(int v) {
        if (v == 0) { //regular
            return Color.WHITE;
        }

        if(v == 1) { //border
            return Color.BLACK;
        }

        if (v==(2)) {//agent
            return Color.SILVER;
        }

        return Color.YELLOW;

    }
}
