package by.bsu.chemistry.formulaBoxes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

/**
 * Created by Ivan on 12.05.2017.
 */
public class Formula1Handler implements Formula{

    @FXML
    TextField textField;

    @FXML
    Label result;

    @FXML
    Button evaluate;

    @FXML
    TextField numberOfSymbols;

    @Override
    public VBox getView() {
        VBox root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/formula1.fxml"));

        } catch (IOException e) {
            root = new VBox();
            e.printStackTrace();
        }
        return root;
    }

    @FXML
    private void evaluate(){
        System.out.println(textField.getText());
        if (Pattern.matches("[-+]?[0-9]+(\\.?[0-9]+)?([eE][-+]?[0-9]+)?", textField.getText())) {
            double x = Double.parseDouble(textField.getText());
            double y = new ExpressionBuilder("x^2 + x + 5")
                    .variables("x")
                    .build()
                    .setVariable("x", x)
                    .evaluate();
            result.setText(String.valueOf(round(y, Integer.parseInt(numberOfSymbols.getText()))));
        } else result.setText("invalid input");

    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
