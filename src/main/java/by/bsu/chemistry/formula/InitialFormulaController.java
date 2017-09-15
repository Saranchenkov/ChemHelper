package by.bsu.chemistry.formula;

import by.bsu.chemistry.Controller;
import by.bsu.chemistry.View;
import by.bsu.chemistry.formula.radChemYieldFromChart.calibrationTable.CalibrationTableView;
import by.bsu.chemistry.formula.weizsaecker.WeizsaeckerFormulaView;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
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
        formulaControllers.put("G - calculation (chart)", CalibrationTableView.class);
    }

    private final ConfigurableApplicationContext context;

    @Autowired
    InitialFormulaController(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @FXML
    Accordion accordion;

    @FXML
    BorderPane pane;

    @FXML
    ListView<String> lvNuclearChem;

    @FXML
    ListView<String> lvRadChem;

    @Override
    public void setEvents() {
        lvNuclearChem.setItems(FXCollections.singletonObservableList("WeizsaeckerFormula"));
        lvNuclearChem.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> setCenter(newValue));

        lvRadChem.setItems(FXCollections.singletonObservableList("G - calculation (chart)"));
        lvRadChem.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> setCenter(newValue));

        accordion.getPanes()
                .forEach(pane -> pane
                        .expandedProperty()
                        .addListener((observable, oldValue, newValue) -> {
                            if(!newValue) ((ListView)pane.getContent()).getSelectionModel().clearSelection();
                        }));
    }

    private void setCenter(String title) {
        VBox vBox = (VBox)context.getBean(formulaControllers.get(title)).getView();
        pane.setCenter(vBox);
    }
}
