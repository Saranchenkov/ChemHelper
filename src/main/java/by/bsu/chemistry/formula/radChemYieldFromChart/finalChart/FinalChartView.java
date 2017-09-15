package by.bsu.chemistry.formula.radChemYieldFromChart.finalChart;

import by.bsu.chemistry.Controller;
import by.bsu.chemistry.View;
import de.felixroske.jfxsupport.FXMLView;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Ivan on 11.09.2017.
 */
@FXMLView
public class FinalChartView extends View{

    private final FinalChartController controller;

    @Autowired
    public FinalChartView(FinalChartController controller) {
        this.controller = controller;
    }

    @Override
    public Controller getController() {
        return controller;
    }
}
