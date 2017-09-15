package by.bsu.chemistry.util;

import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by Ivan on 14.05.2017.
 */
public final class Helper {

    public static final String DESKTOP_PATH =  new File(System.getProperty("user.home"), "Desktop").getPath();

    public static final Pattern DOUBLE_REGEX = Pattern.compile("[-+]?[0-9]+(\\.?[0-9]+)?([eE][-+]?[0-9]+)?");
    public static final Pattern SYMBOL_REGEX = Pattern.compile("[a-zA-Z]{1,2}([m][0-9]?)?");
    public static final Pattern NUMBER = Pattern.compile("\\d{1,3}");
    public static final Pattern NUCLIDE_ID = Pattern.compile("\\d{1,3}[a-zA-Z]{1,2}([m][0-9]?)?");
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
        String text = replaceIfContains(textField.getText(), ',', '.');
        return Double.parseDouble(text);
    }

    public static double getDouble(String text){
        text = replaceIfContains(text, ',', '.');
        return Double.parseDouble(text);
    }

    public static int getInteger(TextField textField){
        return Integer.parseInt(textField.getText());
    }

    public static boolean checkDouble(TextField textField){
        String text = replaceIfContains(textField.getText(), ',', '.');
        return DOUBLE_REGEX.matcher(text).matches();
    }

    public static boolean checkDouble(String text){
        text = replaceIfContains(text, ',', '.');
        return DOUBLE_REGEX.matcher(text).matches();
    }

    public static boolean checkDoubleValues(TextField... textFields){
        for (TextField textField : textFields){
            String text = replaceIfContains(textField.getText(), ',', '.');
            if (!DOUBLE_REGEX.matcher(text).matches()) return false;
        }
        return true;
    }

    public static boolean checkElementSymbol(TextField textField){
        return SYMBOL_REGEX.matcher(textField.getText()).matches();
    }

    public static boolean checkNuclideId(TextField textField){
        return NUCLIDE_ID.matcher(textField.getText()).matches();
    }

    public static boolean checkNumber(TextField textField){
        return NUMBER.matcher(textField.getText()).matches();
    }

    public static String getResult(double result, TextField numberOfSymbols){
        return round(result, getInteger(numberOfSymbols));
    }

    public static StringConverter<Double> getConverter(){
        return new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return object.toString();
            }

            @Override
            public Double fromString(String string) {
                return Helper.checkDouble(string) ? getDouble(string) : 0d;
            }
        };
    }

    public static String replaceIfContains(String source, char c, char newC){
        char[] array = source.toCharArray();
        for(int i = 0; i < array.length; i++){
            if (array[i] == c) array[i] = newC;
        }
        return new String(array);
    }
}
