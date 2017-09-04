package by.bsu.chemistry.formula;

import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by Ivan on 13.05.2017.
 */
public interface Manager {

    Pane getView(String title) throws IllegalAccessException, InstantiationException, IOException;
}
