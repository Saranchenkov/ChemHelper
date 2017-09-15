package by.bsu.chemistry.formula.radChemYieldFromChart;

import javafx.scene.chart.XYChart;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 * Created by Ivan on 11.09.2017.
 */
public class TrendLine {

    private final Supplier<Stream<Double[]>> pairs;
    private final Supplier<DoubleStream> xs;
    private final Supplier<DoubleStream> ys;
    private final double avgX;
    private final double avgY;

    private final double slope;
    private final double yIntercept;

    public TrendLine(List<Double[]> data) {
        pairs = data::stream;

        xs = () -> pairs.get().mapToDouble(pair -> pair[0]);
        ys = () -> pairs.get().mapToDouble(pair -> pair[1]);
        avgX = xs.get().average().getAsDouble();
        avgY = ys.get().average().getAsDouble();

        //Excel LINEST function
        slope = pairs.get().mapToDouble(pair -> (pair[0] - avgX)*(pair[1] - avgY)).sum() / xs.get().map(x -> Math.pow(x - avgX, 2)).sum();
        yIntercept = avgY - slope * avgX;
    }

    public XYChart.Series<Double, Double> getTrendlineSeries(){
        XYChart.Series<Double, Double> trendSeries = new XYChart.Series<>();
        xs.get().forEach(x -> trendSeries.getData().add(new XYChart.Data<>(x, slope*x + yIntercept)));
        return trendSeries;
    }

    // Excel RSQ function
    public double getRSquared(){
        return Math.pow(pairs.get().mapToDouble(pair -> (pair[0] - avgX)*(pair[1] - avgY)).sum()
                / (Math.sqrt(xs.get().map(x -> Math.pow(x - avgX, 2)).sum() * ys.get().map(y -> Math.pow(y - avgY, 2)).sum())), 2);
    }

    public double getXFromEquation(double y){
        return (y - yIntercept)/slope;
    }

    public String getEquation(){
        return new StringBuilder(30)
                .append(" y = ")
                .append(String.format("%.5E", slope))
                .append("x ")
                .append(yIntercept < 0 ? "- " : "+ ")
                .append(String.format("%.5E", Math.abs(yIntercept)))
                .toString();
    }

    public Supplier<Stream<Double[]>> getPairs() {
        return pairs;
    }

    public Supplier<DoubleStream> getXs() {
        return xs;
    }

    public Supplier<DoubleStream> getYs() {
        return ys;
    }

    public double getAvgX() {
        return avgX;
    }

    public double getAvgY() {
        return avgY;
    }

    public double getSlope() {
        return slope;
    }

    public double getyIntercept() {
        return yIntercept;
    }
}
