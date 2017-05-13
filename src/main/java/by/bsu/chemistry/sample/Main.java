package by.bsu.chemistry.sample;

import by.bsu.chemistry.configuration.AppConfig;
import by.bsu.chemistry.util.BoxUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {

    public static BorderPane borderPane;

    ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        super.init();
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println(context.getBean(FormulaManager.class));
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (context != null) {
            context.close();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        borderPane = BoxUtils.getInstance().createBorderPane();
        borderPane.setLeft(BoxUtils.getInstance().getVBoxWithTreeView());
        borderPane.setCenter(BoxUtils.getInstance().createStartCenterBox());
        primaryStage.setTitle("ChemHelper 1.0");
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
