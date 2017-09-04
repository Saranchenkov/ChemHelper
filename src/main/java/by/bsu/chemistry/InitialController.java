package by.bsu.chemistry;

import by.bsu.chemistry.formula.FormulaView;
import by.bsu.chemistry.isotopes.IsotopesView;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * Created by Ivan on 02.09.2017.
 */
@FXMLController
public class InitialController {

    private final ConfigurableApplicationContext context;

    @Autowired
    public InitialController(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @FXML
    public void getFormulaStage() throws IOException {
       showStage(FormulaView.class);
    }

    @FXML
    public void getIsotopesStage() throws IOException {
        showStage(IsotopesView.class);
    }

    @FXML
    public void getConstantsStage(){

    }

    @FXML
    public void getCalculatorStage(){

    }

    @FXML
    public void getConverterStage(){

    }

    @FXML
    public void getFavouriteFormulaStage(){

    }

    private <T extends View> void showStage(Class<T> viewClass) {
        Stage stage = new Stage();
        stage.setScene(new Scene(context.getBean(viewClass).getView()));
        stage.show();
    }
}
