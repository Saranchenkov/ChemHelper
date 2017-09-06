package by.bsu.chemistry.formula;

import by.bsu.chemistry.View;
import de.felixroske.jfxsupport.FXMLController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Created by Ivan on 13.05.2017.
 */
@FXMLController
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WeizsaeckerFormulaView extends View{

    private final FormulaController controller;

    @Autowired
    public WeizsaeckerFormulaView(FormulaController controller) {
        this.controller = controller;
    }

    @Override
    public FormulaController getController() {
        return controller;
    }
}
