package by.bsu.chemistry.formula;

import by.bsu.chemistry.AbstractController;
import by.bsu.chemistry.formula.radiation_chemical_yield_chart.CalibrationTableController;
import by.bsu.chemistry.formula.weizsaecker.WeizsaeckerFormulaController;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Ivan on 13.05.2017.
 */

@FXMLController
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class InitialFormulaController extends AbstractController {

    private static Map<String, Class<? extends AbstractController>> formulaControllers = new HashMap<>();
    static {
        formulaControllers.put("WeizsaeckerFormula", WeizsaeckerFormulaController.class);
        formulaControllers.put("G - calculation (chart)", CalibrationTableController.class);
    }

    private final ConfigurableApplicationContext context;

    @Autowired
    InitialFormulaController(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @FXML Accordion accordion;

    @FXML BorderPane pane;

    @FXML ListView<String> lvNuclearChem;
    @FXML ListView<String> lvRadChem;

    @Override
    public void setEvents() {
        lvNuclearChem.setItems(FXCollections.singletonObservableList("WeizsaeckerFormula"));
        lvRadChem.setItems(FXCollections.singletonObservableList("G - calculation (chart)"));

        addListenersToListViews(new ListView[]{lvNuclearChem, lvRadChem});

        accordion.getPanes()
                .forEach(pane -> pane
                        .expandedProperty()
                        .addListener((observable, oldValue, newValue) -> {
                            if(!newValue) ((ListView)pane.getContent()).getSelectionModel().clearSelection();
                        }));
    }

    private void setCenter(String title) {
        log.info("Title of pane: {}", title);
        Parent parent = context.getBean(formulaControllers.get(title)).getView();
        pane.setCenter(parent);
    }

    private void addListenersToListViews(ListView<String>[] views){
        for (ListView<String> listView : views) {
            listView.getSelectionModel()
                    .selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        if (Objects.nonNull(newValue)) setCenter(newValue);
                    });
        }
    }
}
