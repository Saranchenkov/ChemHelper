package by.bsu.chemistry.formula;

import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivan on 13.05.2017.
 */

@Component
public class FormulaManager implements Manager {

    private static Map<String, Class> handlers = new HashMap<>();

    public FormulaManager() {
    }

    //todo: Надо занести в проперти!
    static {
        handlers.put("Formula #1", Formula1Handler.class);
        handlers.put("WeizsaeckerFormula", WeizsaeckerFormula.class);
    }

    @Override
    public Pane getView(String title) throws IllegalAccessException, InstantiationException {
        Pane pane;
        try {
            pane = ((FormulaHandler)handlers.get(title).newInstance()).getView();
            return pane;
        } catch (NullPointerException e){
            return null;
        }

    }
}
