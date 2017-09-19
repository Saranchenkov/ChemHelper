package by.bsu.chemistry.formula.radiation_chemical_yield_chart;

import javafx.scene.chart.XYChart;
import lombok.Getter;
import org.apache.commons.math3.stat.regression.SimpleRegression;

/**
 * Created by Ivan on 11.09.2017.
 */
@Getter
public class TrendLine {

    private final SimpleRegression regression;
    private final double[] xValues;
    private final double slope;
    private final double intercept;


    public TrendLine(final double[][] data, boolean includeIntercept){
        regression = new SimpleRegression(includeIntercept);
        xValues = new double[data.length];
        regression.addData(data);
        for(int i = 0; i < data.length; i++){
            xValues[i] = data[i][0];
        }
        slope = regression.getSlope();
        intercept = regression.getIntercept();
    }

    public XYChart.Series<Double, Double> getTrendlineSeries(){
        XYChart.Series<Double, Double> trendSeries = new XYChart.Series<>();
        for (int i = 0; i < xValues.length; i++) {
            trendSeries.getData().add(new XYChart.Data<>(xValues[i], regression.predict(xValues[i])));
        }
        return trendSeries;
    }

    public double getRSquare(){
        return regression.getRSquare();
    }

    public double getSlopeStandardError(){
        return regression.getSlopeStdErr();
    }

    public double getInterceptStandardError(){
        return regression.getInterceptStdErr();
    }

    public double getXFromEquation(double y){
        return (y - intercept)/slope;
    }

    public String getEquation(){
        return new StringBuilder(30)
                .append(" y = ")
                .append(String.format("%.5E", slope))
                .append("x ")
                .append(intercept < 0 ? "- " : "+ ")
                .append(String.format("%.5E", Math.abs(intercept)))
                .toString();
    }
}
