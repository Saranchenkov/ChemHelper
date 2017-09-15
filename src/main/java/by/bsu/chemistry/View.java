package by.bsu.chemistry;

import de.felixroske.jfxsupport.PropertyReaderHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by Ivan on 03.09.2017.
 */
public abstract class View {

    public abstract Controller getController();

    public Parent getView(){
        Parent pane = null;
        try {
            Controller controller = getController();
            pane = getPaneFromFXML(this.getClass(), controller);
            controller.setEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    public Parent getPaneFromFXML(final Class<? extends View> clazz, final Controller controller) throws IOException {
        String path = PropertyReaderHelper.determineFilePathFromPackageName(clazz) + getFXMLName(clazz);
        FXMLLoader loader = new FXMLLoader(clazz.getResource(path));
        loader.setController(controller);
        return loader.load();
    }

    public String getFXMLName(Class<? extends View> clazz){
        char[] name = clazz.getSimpleName().replace("View", ".fxml").toCharArray();
        name[0] = Character.toLowerCase(name[0]);
        return new String(name);
    }
}
