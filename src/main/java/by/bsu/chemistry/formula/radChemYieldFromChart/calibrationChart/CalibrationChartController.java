package by.bsu.chemistry.formula.radChemYieldFromChart.calibrationChart;

import by.bsu.chemistry.Controller;
import by.bsu.chemistry.formula.radChemYieldFromChart.finalTable.FinalTableView;
import by.bsu.chemistry.formula.radChemYieldFromChart.TrendLine;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ivan on 11.09.2017.
 */

@FXMLController
public class CalibrationChartController implements Controller {

    // TODO: 10.09.2017 Можно сделать подсказки на точках, Auto-ranging

    private final List<CalibrationResult> data = new ArrayList<>();

    // TODO: 13.09.2017 нужна ли тут вьюха, или лучше через контекст?
    @Autowired
    FinalTableView view;

    @FXML
    LineChart<Double, Double> chart;

    @FXML
    Label equation;

    @FXML
    Label rSquared;

    @FXML
    Button nextButton;

    @Override
    public void setEvents() {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        data.forEach(result -> series.getData()
                .add(new XYChart.Data<Double, Double>(
                        result.getConcentration(),
                        result.getOpticalDensity()
                )));

        TrendLine trendLine = new TrendLine(data.stream()
                                                .map(result -> new Double[]{result.getConcentration(), result.getOpticalDensity()})
                                                .collect(Collectors.toList()));
        XYChart.Series<Double, Double> trendSeries = trendLine.getTrendlineSeries();

        equation.setText(equation.getText().concat(trendLine.getEquation()));
        rSquared.setText(rSquared.getText() + String.format(" %.5f", trendLine.getRSquared()));

        chart.getData().addAll(series, trendSeries);

        nextButton.setOnAction(event -> {
            view.setCalibrationTrendline(trendLine);
            chart.getScene().setRoot(view.getView());
        });
    }

    public void setData(List<CalibrationResult> data) {
        this.data.clear();
        this.data.addAll(data);

    }
}
