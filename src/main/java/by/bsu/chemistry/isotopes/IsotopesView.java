package by.bsu.chemistry.isotopes;

import by.bsu.chemistry.View;
import de.felixroske.jfxsupport.FXMLView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.Objects;

/**
 * Created by Ivan on 03.09.2017.
 */
@FXMLView
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IsotopesView implements View {

    private final Accordion pane;

    private final IsotopesController controller;

    private final Font FONT = Font.font("Times New Roman", 18);

    @Autowired
    public IsotopesView(IsotopesController controller) {
        this.pane = getPane();
        this.controller = controller;
    }

    @Override
    public Parent getView() {
        return pane;
    }

    private Accordion getPane(){
        Accordion accordion = new Accordion(
                getTitledPaneBySymbol(),
                getTitledPaneByMassNumber(),
                getTitledPaneByChargeNumber(),
                getTitledPaneByNucId()
        );
        accordion.setPrefSize(600, 400);
        return accordion;
    }

    private TitledPane getTitledPaneBySymbol(){
        Label label = getLabel("Enter the nuclide symbol:", 215);
        TextField textField = getTextField("For example: Cs or Ra", 200);
        Button button = getButton();
        ListView<String> listView = getListView();
        button.setOnAction(event -> controller.findBySymbol(textField, listView));
        VBox vBox = new VBox(getHBox(label, textField, button), getResultLabel(), listView);
        return new TitledPane("Find nuclides by symbol", vBox);
    }

    private TitledPane getTitledPaneByMassNumber(){
        Label label = getLabel("Enter the nuclide mass number (A):", 270);
        TextField textField = getTextField("For example: 235 or 40", 220);
        Button button = getButton();
        ListView<String> listView = getListView();
        button.setOnAction(event -> controller.findByMassNumber(textField, listView));
        VBox vBox = new VBox(getHBox(label, textField, button), getResultLabel(), listView);
        return new TitledPane("Find nuclides by mass number (A)", vBox);
    }

    private TitledPane getTitledPaneByChargeNumber(){
        Label label = getLabel("Enter the nuclide charge number (Z):", 270);
        TextField textField = getTextField("For example: 92 or 19", 200);
        Button button = getButton();
        ListView<String> listView = getListView();
        button.setOnAction(event -> controller.findByChargeNumber(textField, listView));
        VBox vBox = new VBox(getHBox(label, textField, button), getResultLabel(), listView);
        return new TitledPane("Find nuclides by charge number (Z)", vBox);
    }

    private TitledPane getTitledPaneByNucId(){
        Label label = getLabel("Enter the nuclide:", 165);
        TextField textField = getTextField("For example: 235U or 40K", 220);
        Button button = getButton();
        ListView<String> listView = getListView();
        button.setOnAction(event -> controller.findById(textField, listView));
        VBox vBox = new VBox(getHBox(label, textField, button), getResultLabel(), listView);
        return new TitledPane("Find nuclides by mass number and symbol", vBox);
    }

    private Label getLabel(String text, int prefWidth){
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.setFont(FONT);
        HBox.setMargin(label, new Insets(5, 0,0,0));
        label.setPrefSize(prefWidth, 45);
        return label;
    }

    private Label getResultLabel(){
        Label label = new Label("Result:");
        label.setAlignment(Pos.CENTER);
        label.setFont(FONT);
        label.setPrefSize(96, 33);
        return label;

    }

    private Button getButton(){
        Button button = new Button("Search");
        button.setPrefSize(80, 45);
        button.setFont(FONT);
        HBox.setMargin(button, new Insets(5, 0, 0, 10));
        return button;
    }

    private TextField getTextField(String promptText, int prefWidth){
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setFont(FONT);
        textField.setPrefSize(prefWidth, 45);
        HBox.setMargin(textField, new Insets(5, 0,0,0));
        return textField;
    }

    private ListView<String> getListView(){
        ListView<String> listView = new ListView<>();
        listView.setPrefSize(335, 200);
        listView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (Objects.nonNull(newValue)) controller.getNuclidProperties(newValue);
                });
        return listView;
    }

    private HBox getHBox(Label label, TextField textField, Button button) {
        HBox hBox = new HBox(label, textField, button);
        hBox.setPrefSize(580, 60);
        return hBox;
    }
}
