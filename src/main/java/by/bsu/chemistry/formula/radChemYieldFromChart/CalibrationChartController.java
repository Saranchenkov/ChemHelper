package by.bsu.chemistry.formula.radChemYieldFromChart;

import by.bsu.chemistry.AbstractController;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ivan on 11.09.2017.
 */

@FXMLController
public class CalibrationChartController extends AbstractController {

    // TODO: 10.09.2017 Можно сделать подсказки на точках, Auto-ranging

    private final List<CalibrationResult> data = new ArrayList<>();

    @Autowired
    FinalTableController finalTableController;

    private TabPane tabPane;

    @FXML LineChart<Double, Double> chart;

    @FXML Label equation;

    @FXML Label rSquared;

    @FXML Button nextButton;

    @Override
    public void setEvents() {

        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        data.forEach(result -> series.getData()
                .add(new XYChart.Data<Double, Double>(
                        result.getConcentration(),
                        result.getOpticalDensity()
                )));

        TrendLine trendLine = new TrendLine(series.getData()
                                                  .stream()
                                                  .map(point -> new Double[]{point.getXValue(), point.getYValue()})
                                                  .collect(Collectors.toList()));

        XYChart.Series<Double, Double> trendSeries = trendLine.getTrendlineSeries();

        equation.setText("Equation of trendline: ".concat(trendLine.getEquation()));
        rSquared.setText("R squared = ".concat(String.format(" %.5f", trendLine.getRSquared())));

        chart.getData().setAll(series, trendSeries);

        nextButton.setOnAction(event -> {
            finalTableController.setTrendLine(trendLine);
            showFinalTable();
        });
    }

    private void showFinalTable(){
        finalTableController.setTabPane(tabPane);
        Tab newTab = ((TabPane)finalTableController.getView()).getTabs().get(0);
        tabPane.getTabs().addAll(newTab);
        tabPane.getSelectionModel().select(newTab);

    }

    void setData(List<CalibrationResult> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    void setTabPane(TabPane tabPane){
        this.tabPane = tabPane;
    }
}
