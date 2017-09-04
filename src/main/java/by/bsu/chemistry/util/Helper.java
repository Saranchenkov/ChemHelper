package by.bsu.chemistry.util;

import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by Ivan on 14.05.2017.
 */
public final class Helper {

    public static final String DOUBLE_REGEX = "[-+]?[0-9]+(\\.?[0-9]+)?([eE][-+]?[0-9]+)?";
    public static final String SYMBOL_REGEX = "[a-zA-Z]{1,2}([m][0-9]?)?";
    public static final String NUMBER = "\\d{1,3}";
    public static final String NUCLIDE_ID = "\\d{1,3}[a-zA-Z]{1,2}([m][0-9]?)?";
    private static final HashMap<String, String> units = new HashMap<>();
    static {
        units.put("PS", "picoseconds");
        units.put("NS", "nanoseconds");
        units.put("MS", "milliseconds");
        units.put("S", "seconds");
        units.put("M", "minutes");
        units.put("H", "hours");
        units.put("D", "days");
        units.put("Y", "years");
    }

    private Helper(){}

    public static String getUnit(String unit){return units.getOrDefault(unit, null);}

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

    public static boolean checkDouble(String text){
        return Pattern.matches(DOUBLE_REGEX, text);
    }

    public static boolean checkDoubleValues(TextField... textFields){
        for (TextField textField : textFields){
            if (!Pattern.matches(DOUBLE_REGEX , textField.getText())) return false;
        }
        return true;
    }

    public static boolean checkElementSymbol(TextField textField){
        return Pattern.matches(SYMBOL_REGEX, textField.getText());
    }

    public static boolean checkNuclideId(TextField textField){
        return Pattern.matches(NUCLIDE_ID, textField.getText());
    }

    public static boolean checkNumber(TextField textField){
        return Pattern.matches(NUMBER, textField.getText());
    }

    public static String getResult(double result, TextField numberOfSymbols){
        return round(result, getInteger(numberOfSymbols));
    }


}
