package by.bsu.chemistry.formula.radChemYieldFromChart.calibrationTable;

import by.bsu.chemistry.Controller;
import by.bsu.chemistry.View;
import by.bsu.chemistry.formula.radChemYieldFromChart.calibrationChart.CalibrationChartController;
import de.felixroske.jfxsupport.FXMLView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;

import java.util.Objects;

/**
 * Created by Ivan on 09.09.2017.
 */
@FXMLView
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CalibrationTableView extends View{

    @Autowired
    private ConfigurableApplicationContext context;

    private CallibrationTableController controller;

    @Override
    public Controller getController() {
        if(Objects.isNull(controller))
            controller = context.getBean(CallibrationTableController.class);
        return controller;
    }
}
