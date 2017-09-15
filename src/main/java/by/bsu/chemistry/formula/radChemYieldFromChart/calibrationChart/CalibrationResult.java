package by.bsu.chemistry.formula.radChemYieldFromChart.calibrationChart;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by Ivan on 11.09.2017.
 */
public class CalibrationResult {
    private final SimpleIntegerProperty id;
    private final SimpleDoubleProperty concentration;
    private final SimpleDoubleProperty opticalDensity;
    private final SimpleBooleanProperty selected;

    public CalibrationResult(int id, double concetration, double opticalDensity) {
        this.id = new SimpleIntegerProperty(id);
        this.concentration = new SimpleDoubleProperty(concetration);
        this.opticalDensity = new SimpleDoubleProperty(opticalDensity);
        this.selected = new SimpleBooleanProperty(true);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public double getConcentration() {
        return concentration.get();
    }

    public SimpleDoubleProperty concentrationProperty() {
        return concentration;
    }

    public void setConcentration(double concentration) {
        this.concentration.set(concentration);
    }

    public double getOpticalDensity() {
        return opticalDensity.get();
    }

    public SimpleDoubleProperty opticalDensityProperty() {
        return opticalDensity;
    }

    public void setOpticalDensity(double opticalDensity) {
        this.opticalDensity.set(opticalDensity);
    }

    public boolean isSelected() {
        return selected.get();
    }

    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    @Override
    public String toString() {
        return "CalibrationResult{" +
                "id=" + id.get() +
                ", concetration=" + concentration.get() +
                ", opticalDensity=" + opticalDensity.get() +
                ", selected=" + selected.get() +
                '}';
    }
}
