package by.bsu.chemistry.util;

import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

/**
 * Created by Ivan on 14.05.2017.
 */
public final class Helper {

    private static final String DOUBLE_REGEX = "[-+]?[0-9]+(\\.?[0-9]+)?([eE][-+]?[0-9]+)?";

    private Helper(){}

    private static String round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);

        return bd.toString();
    }

    public static double getDouble(TextField textField){
        return Double.parseDouble(textField.getText());
    }

    public static int getInteger(TextField textField){
        return Integer.parseInt(textField.getText());
    }

    public static boolean checkDouble(TextField textField){
        return Pattern.matches(DOUBLE_REGEX, textField.getText());
    }

    public static boolean checkDoubleValues(TextField... textFields){
        for (TextField textField : textFields){
            if (!Pattern.matches(DOUBLE_REGEX , textField.getText())) return false;
        }
        return true;
    }

    public static String getResult(double result, TextField numberOfSymbols){
        return round(result, getInteger(numberOfSymbols));
    }
}
