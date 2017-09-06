package by.bsu.chemistry.formula;

import by.bsu.chemistry.Controller;
import by.bsu.chemistry.View;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.*;

/**
 * Created by Ivan on 13.05.2017.
 */

@FXMLController
public class InitialFormulaController implements Controller{

    private static Map<String, Class<? extends View>> formulaControllers = new TreeMap<>();
    static {
        formulaControllers.put("WeizsaeckerFormula", WeizsaeckerFormulaView.class);
    }

    private final ConfigurableApplicationContext context;

    @Autowired
    InitialFormulaController(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @FXML
    BorderPane pane;

    @FXML
    ListView<String> lvNuclearChem;

    @Override
    public void setEvents() {
        lvNuclearChem.setItems(FXCollections.observableList(new ArrayList<>(formulaControllers.keySet())));
        lvNuclearChem.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> setCenter(newValue));
    }

    private void setCenter(String title) {
        VBox vBox = (VBox)context.getBean(formulaControllers.get(title)).getView();
        pane.setCenter(vBox);
    }

}
