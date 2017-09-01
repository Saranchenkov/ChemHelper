package by.bsu.chemistry.sample;

import javafx.scene.layout.Pane;

/**
 * Created by Ivan on 13.05.2017.
 */
public interface Manager {

    Pane getView(String title) throws IllegalAccessException, InstantiationException;
}
