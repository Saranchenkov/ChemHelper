package by.bsu.chemistry.formula.weizsaecker;

import by.bsu.chemistry.View;
import by.bsu.chemistry.formula.FormulaController;
import de.felixroske.jfxsupport.FXMLView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Created by Ivan on 13.05.2017.
 */
@FXMLView
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WeizsaeckerFormulaView extends View{

    private final WeizsaeckerFormulaController controller;

    @Autowired
    public WeizsaeckerFormulaView(WeizsaeckerFormulaController controller) {
        this.controller = controller;
    }

    @Override
    public FormulaController getController() {
        return controller;
    }
}
