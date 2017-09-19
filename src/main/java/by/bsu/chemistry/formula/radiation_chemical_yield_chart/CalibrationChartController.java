package by.bsu.chemistry.formula.radiation_chemical_yield_chart;

import by.bsu.chemistry.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by Ivan on 11.09.2017.
 */

@Slf4j
public class CalibrationChartController extends AbstractController {

    // TODO: 10.09.2017 Можно сделать подсказки на точках, Auto-ranging

    private final List<CalibrationResult> data;
    private final TabPane tabPane;
    private FinalTableController finalTableController;

    CalibrationChartController(List<CalibrationResult> data, TabPane tabPane){
        this.data = data;
        this.tabPane = tabPane;
    }

    @FXML LineChart<Double, Double> chart;
    @FXML Label equation;
    @FXML Label rSquared;
    @FXML Button nextButton;

    @Override
    public void setEvents() {

        XYChart.Series<Double, Double> selectedSeries = new XYChart.Series<>();
        XYChart.Series<Double, Double> unselectedSeries = new XYChart.Series<>();
        fillSeries(selectedSeries, unselectedSeries);

        double[][] points = selectedSeries.getData()
                                  .stream()
                                  .map(point -> new double[]{point.getXValue(), point.getYValue()})
                                  .toArray(double[][]::new);

        TrendLine trendLine = new TrendLine(points, true);
        XYChart.Series<Double, Double> trendSeries = trendLine.getTrendlineSeries();

        equation.setText("Equation of trendline: ".concat(trendLine.getEquation()));
        rSquared.setText("R squared = ".concat(String.format(" %.5f", trendLine.getRSquare())));

        chart.getData().setAll(selectedSeries, unselectedSeries, trendSeries);

        nextButton.setOnAction(event -> {
            showFinalTable(trendLine);
        });
    }

    private void showFinalTable(TrendLine trendLine){
        finalTableController = new FinalTableController(trendLine, tabPane);
        Tab newTab = ((TabPane)finalTableController.getView()).getTabs().get(0);
        tabPane.getTabs().addAll(newTab);
        tabPane.getSelectionModel().select(newTab);
    }

    private void fillSeries(XYChart.Series<Double, Double> selected, XYChart.Series<Double, Double> unselected){
        for(CalibrationResult result : data){
            if (result.isSelected()) {
                selected.getData().add(new XYChart.Data<>(
                        result.getConcentration(),
                        result.getOpticalDensity()
                ));
            } else {
                unselected.getData().add(new XYChart.Data<>(
                        result.getConcentration(),
                        result.getOpticalDensity()
                ));
            }
        }
    }
}
