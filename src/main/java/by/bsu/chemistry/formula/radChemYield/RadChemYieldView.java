package by.bsu.chemistry.formula.radChemYield;

import by.bsu.chemistry.Controller;
import by.bsu.chemistry.View;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Ivan on 07.09.2017.
 */
public class RadChemYieldView extends View{

    RadChemYieldController controller;

    @Autowired
    public RadChemYieldView(RadChemYieldController controller) {
        this.controller = controller;
    }

    @Override
    public Controller getController() {
        return controller;
    }
}
