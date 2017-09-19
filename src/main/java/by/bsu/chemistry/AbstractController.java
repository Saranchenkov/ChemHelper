package by.bsu.chemistry;

import de.felixroske.jfxsupport.PropertyReaderHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.springframework.util.StringUtils.replace;
import static org.springframework.util.StringUtils.uncapitalize;

/**
 * Created by Ivan on 03.09.2017.
 */

public abstract class AbstractController implements Controller{

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    public Parent getView(){
        Parent pane = null;
        try {
            pane = getPaneFromFXML();
            setEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private Parent getPaneFromFXML() throws IOException {
        String path = PropertyReaderHelper.determineFilePathFromPackageName(getClass()) + getFXMLName();
        LOGGER.debug("Path to .fxml file: {}", path);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        LOGGER.debug("Location of .fxml file: {}", loader.getLocation());
        loader.setController(this);
        return loader.load();
    }

    private String getFXMLName(){
        return uncapitalize(replace(this.getClass().getSimpleName(), "Controller", ".fxml"));
    }
}
