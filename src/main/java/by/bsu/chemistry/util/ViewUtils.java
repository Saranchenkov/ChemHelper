package by.bsu.chemistry.util;

import de.felixroske.jfxsupport.PropertyReaderHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by Ivan on 03.09.2017.
 */
public final class ViewUtils {
    public static Parent getPaneFromFXML(final Class<?> clazz, String fileName) throws IOException {
        System.err.println("Trying to get fxml with path:   " + PropertyReaderHelper.determineFilePathFromPackageName(clazz) + fileName);
        return FXMLLoader.load(clazz.getResource(PropertyReaderHelper.determineFilePathFromPackageName(clazz) + fileName));
    }
}
