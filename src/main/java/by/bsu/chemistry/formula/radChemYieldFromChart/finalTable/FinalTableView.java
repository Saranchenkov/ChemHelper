package by.bsu.chemistry.formula.radChemYieldFromChart.finalTable;

import by.bsu.chemistry.Controller;
import by.bsu.chemistry.View;
import by.bsu.chemistry.formula.radChemYieldFromChart.TrendLine;
import de.felixroske.jfxsupport.FXMLView;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Ivan on 11.09.2017.
 */
@FXMLView
public class FinalTableView extends View {

    private final FinalTableController controller;

    private TrendLine calibrationTrendline;

    @Autowired
    public FinalTableView(FinalTableController controller) {
        this.controller = controller;
    }

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    public Parent getView() {
        controller.setTrendLine(calibrationTrendline);
        return super.getView();
    }

    public void setCalibrationTrendline(TrendLine calibrationTrendline) {
        this.calibrationTrendline = calibrationTrendline;
    }
}
