package by.bsu.chemistry.sample;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launchApp(Main.class, StarterView.class, args);
    }
}
