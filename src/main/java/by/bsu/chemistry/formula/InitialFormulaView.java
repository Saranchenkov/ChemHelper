package by.bsu.chemistry.formula;

import by.bsu.chemistry.View;
import de.felixroske.jfxsupport.FXMLView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Created by Ivan on 01.09.2017.
 */

@FXMLView
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class InitialFormulaView extends View{

    private final static Logger LOGGER = LoggerFactory.getLogger(InitialFormulaView.class);

    private final InitialFormulaController controller;

    @Autowired
    public InitialFormulaView(InitialFormulaController controller) {
        this.controller = controller;
    }

    @Override
    public InitialFormulaController getController() {
        return controller;
    }
}
