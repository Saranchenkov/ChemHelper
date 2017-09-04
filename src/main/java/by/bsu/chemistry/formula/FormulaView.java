package by.bsu.chemistry.formula;

import by.bsu.chemistry.View;
import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Ivan on 01.09.2017.
 */

@FXMLView
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FormulaView implements View{

    private final static Logger LOGGER = LoggerFactory.getLogger(FormulaView.class);

    @Autowired
    FormulaManager manager;

    private BorderPane pane;

    public FormulaView() throws IOException {
        pane = createBorderPane();
        pane.setLeft(getVBoxWithTreeView());
        pane.setCenter(createStartCenterBox());
    }

    public Parent getView() {
        return pane;
    }

    private BorderPane createBorderPane(){
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(800, 400);
        LOGGER.info("BorderPane was created!");
        return borderPane;
    }

    private VBox createStartCenterBox() throws IOException {
        VBox startVBox = new VBox();
        Image bsu_logo = new Image("/pictures/BSU_logo.jpg");
        ImageView imageView = new ImageView(bsu_logo);

        startVBox.setPrefSize(imageView.getFitWidth(), imageView.getFitHeight());
        startVBox.getChildren().add(imageView);
        startVBox.setAlignment(Pos.CENTER);
        LOGGER.info("Box with BSU_logo was created!");
        return startVBox;
    }

    private Pane getVBoxWithTreeView(){

        TreeView<String> treeView = new TreeView<>();
        treeView.setPrefSize(146.0, 344.0);
        //теперь root невидимый!
        treeView.setShowRoot(false);
        treeView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> changeVBox(newValue.getValue()));

        TreeItem<String> root = new TreeItem<>("Choose formula:");
        root.setExpanded(true);

        //Есть  возможность добавить  к TreeItem собственный eventHandler !
        TreeItem<String> nuclearChemistryItem = new TreeItem<>("Nuclear Chemistry");
        fillNuclearChemistryBranch(nuclearChemistryItem);

        TreeItem<String> radiationChemistryItem = new TreeItem<>("Radiation Chemistry");
        TreeItem<String> dosimetryItem = new TreeItem<>("Dosimetry");

        root.getChildren().setAll(nuclearChemistryItem, radiationChemistryItem, dosimetryItem);
        treeView.setRoot(root);
        VBox vBox = new VBox(treeView);
        vBox.setPrefSize(163.0, 375.0);
        VBox.setVgrow(treeView, Priority.ALWAYS);
        LOGGER.info("TreeView was created!");
        return vBox;
    }

    private void changeVBox(String title) {
        try {
            Pane newVBox = manager.getView(title);
            if (Objects.isNull(newVBox)) newVBox = getDefaultPane(title);
            pane.setCenter(newVBox);
            LOGGER.info("Move to new pane: {}", pane);
        } catch (ReflectiveOperationException e) {
            LOGGER.warn("Exception was thrown ", e);
        }
    }

    private void fillNuclearChemistryBranch(TreeItem<String> mainBranch){
        // TODO: 03.09.2017 Вытаскивать и наполнять из пропертей
        mainBranch.getChildren().setAll(Arrays.asList(
                new TreeItem<>("Formula #1"),
                new TreeItem<>("WeizsaeckerFormula"),
                new TreeItem<>("Formula #3")));
    }

    private Pane getDefaultPane(String title){

        VBox vBox = new VBox();
        vBox.setPrefSize(437.0, 328.0);
        vBox.setAlignment(Pos.CENTER);

        Label label = new Label(title);
        label.setPrefSize(600.0, 31.0);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(Font.font("Times New Roman", 60.0));
        label.setAlignment(Pos.CENTER);

        vBox.getChildren().add(label);
        LOGGER.info("Default pane({}) with title \"{}\" was setted", vBox, title);

        return vBox;
    }
}
