package by.bsu.chemistry.formulaBoxes;

import by.bsu.chemistry.util.Helper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.io.IOException;

/**
 * Created by Ivan on 13.05.2017.
 */
public class WeizsaeckerFormula implements Formula{

    @FXML
    TextField massNumber;

    @FXML
    TextField chargeNumber;

    @FXML
    TextField numberOfSymbols;

    @FXML
    Label result;

    @Override
    public VBox getView() {
        VBox root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/WeizsaeckerFormula.fxml"));

        } catch (IOException e) {
            root = new VBox();
            e.printStackTrace();
        }
        return root;
    }

    @Override
    @FXML
    public void evaluate() {
        if (Helper.checkDoubleValues(massNumber, chargeNumber)){
        double A = Helper.getDouble(massNumber);
        double Z = Helper.getDouble(chargeNumber);
        Expression expression = new ExpressionBuilder("15.75*A - 17.8*A^(2/3) - 0.71*(Z*(Z-1)/A^(1/3)) - 23.7*((A - 2*Z)^2)/A + Delta/(A^(3/4))")
                .variables("A", "Z", "Delta")
                .build()
                .setVariable("A", A)
                .setVariable("Z", Z)
                .setVariable("Delta", getDelta(A, Z));
        result.setText(Helper.getResult(expression.evaluate(), numberOfSymbols)+ " MeV");
        } else result.setText("invalid input");
    }

    private int getDelta(double A, double Z){
        if (A % 2 == 0) {
            if (Z % 2 == 0) return 34; else return -34;
        } else return 0;
    }
}
