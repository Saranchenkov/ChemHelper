package by.bsu.chemistry.isotopes;

import by.bsu.chemistry.View;
import de.felixroske.jfxsupport.FXMLView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Created by Ivan on 03.09.2017.
 */
@FXMLView
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IsotopesView extends View {

    private final IsotopesController controller;

    @Autowired
    public IsotopesView(IsotopesController controller) {
        this.controller = controller;
    }

    @Override
    public IsotopesController getController() {
        return controller;
    }
}
