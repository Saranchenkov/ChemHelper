package by.bsu.chemistry;

import by.bsu.chemistry.formula.InitialFormulaController;
import by.bsu.chemistry.isotopes.IsotopesController;
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
public class InitialController implements Controller{

    private final ConfigurableApplicationContext context;

    @Autowired
    public InitialController(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @FXML
    public void getFormulaStage() throws IOException {
       showStage(InitialFormulaController.class, 670);
    }

    @FXML
    public void getIsotopesStage() throws IOException {
        showStage(IsotopesController.class, 590);
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

    private <T extends AbstractController> void showStage(Class<T> controllerClass, int minWidth) {
        Stage stage = new Stage();
        stage.setMinWidth(minWidth);
        stage.setMinHeight(450);
        stage.setScene(new Scene(context.getBean(controllerClass).getView()));
        stage.show();
    }

    @Override
    public void setEvents() {
        /*makes nothing yet*/
    }
}
