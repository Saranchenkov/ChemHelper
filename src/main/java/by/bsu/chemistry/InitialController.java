package by.bsu.chemistry;

import by.bsu.chemistry.formula.InitialFormulaView;
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
public class InitialController implements Controller{

    private final ConfigurableApplicationContext context;

    @Autowired
    public InitialController(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @FXML
    public void getFormulaStage() throws IOException {
       showStage(InitialFormulaView.class, 660);
    }

    @FXML
    public void getIsotopesStage() throws IOException {
        showStage(IsotopesView.class, 590);
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

    private <T extends View> void showStage(Class<T> viewClass, int width) {
        Stage stage = new Stage();
        stage.setHeight(400);
        stage.setWidth(width);
        stage.setScene(new Scene(context.getBean(viewClass).getView()));
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void setEvents() {
        /*makes nothing yet*/
    }
}
