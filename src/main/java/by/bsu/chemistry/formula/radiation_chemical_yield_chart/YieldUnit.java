package by.bsu.chemistry.formula.radiation_chemical_yield_chart;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivan on 15.09.2017.
 */
public enum YieldUnit {

    PER_JOULE("mol/J"), PER_100EV("molecules/100eV");

    private final String unit;

    YieldUnit(String unit) {
        this.unit = unit;
    }

    private static final Map<String, YieldUnit> units;

    static {
        units = new HashMap<>();
        units.put("mol/J", PER_JOULE);
        units.put("molecules/100eV", PER_100EV);
    }

    public static YieldUnit getBykey(String unit){
        return units.get(unit);
    }

    @Override
    public String toString() {
        return unit;
    }
}
