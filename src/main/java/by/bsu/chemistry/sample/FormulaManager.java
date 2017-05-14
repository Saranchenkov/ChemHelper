package by.bsu.chemistry.sample;

import by.bsu.chemistry.formulaBoxes.Formula;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivan on 13.05.2017.
 */

@Component
public class FormulaManager implements Manager{

    private static Map<String, String> handlers = new HashMap<>();

    public FormulaManager() {
    }

    //todo: Надо занести в проперти!
    static {
        handlers.put("Formula #1", "by.bsu.chemistry.formulaBoxes.Formula1Handler");
        handlers.put("WeizsaeckerFormula", "by.bsu.chemistry.formulaBoxes.WeizsaeckerFormula");
    }

    @Override
    public Pane getView(String title) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return ((Formula)Class.forName(handlers.get(title)).newInstance()).getView();

    }
}
