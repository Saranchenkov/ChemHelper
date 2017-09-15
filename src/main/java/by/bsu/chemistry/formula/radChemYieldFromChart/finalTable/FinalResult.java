package by.bsu.chemistry.formula.radChemYieldFromChart.finalTable;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by Ivan on 09.09.2017.
 */
public class FinalResult {

    private final SimpleIntegerProperty id;
    private final SimpleDoubleProperty time;
    private final SimpleDoubleProperty opticalDensity;
    private SimpleDoubleProperty concentration;
    private final SimpleBooleanProperty selected;

    public FinalResult(int id, double time, double opticalDensity) {
        this.id = new SimpleIntegerProperty(id);
        this.time = new SimpleDoubleProperty(time);
        this.opticalDensity = new SimpleDoubleProperty(opticalDensity);
        this.concentration = new SimpleDoubleProperty(0d);
        this.selected = new SimpleBooleanProperty(true);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public double getTime() {
        return time.get();
    }

    public void setTime(double time) {
        this.time.set(time);
    }

    public SimpleDoubleProperty timeProperty() {
        return time;
    }

    public double getOpticalDensity() {
        return opticalDensity.get();
    }

    public void setOpticalDensity(double opticalDensity) {
        this.opticalDensity.set(opticalDensity);
    }

    public SimpleDoubleProperty opticalDensityProperty() {
        return opticalDensity;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public SimpleBooleanProperty selectedProperty() {
        return selected;
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

    @Override
    public String toString() {
        return "OpticalDensityResult{" +
                "id=" + id.get() +
                ", time=" + time.get() +
                ", opticalDensity=" + opticalDensity.get() +
                ", selected=" + selected.get() +
                '}';
    }
}
