package by.bsu.chemistry.formulaBoxes;

import by.bsu.chemistry.util.Helper;
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
    @Override
    public void evaluate() {
        System.out.println(textField.getText());
        if (Helper.checkDouble(textField)) {
            double x = Helper.getDouble(textField);
            double y = new ExpressionBuilder("x^2 + x + 5")
                    .variables("x")
                    .build()
                    .setVariable("x", x)
                    .evaluate();
            result.setText(Helper.getResult(y, numberOfSymbols));
        } else result.setText("invalid input");
    }



}
