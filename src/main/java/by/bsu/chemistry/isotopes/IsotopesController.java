package by.bsu.chemistry.isotopes;

import by.bsu.chemistry.AbstractController;
import by.bsu.chemistry.util.Helper;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Ivan on 03.09.2017.
 */

@FXMLController
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IsotopesController extends AbstractController {

    private final Logger LOGGER = LoggerFactory.getLogger(IsotopesController.class);

    private final IsotopesRepository repository;

    @Autowired
    public IsotopesController(IsotopesRepository repository) {
        this.repository = repository;
    }

    @FXML TextField tfBySymbol;
    @FXML Button btnBySymbol;
    @FXML ListView<String> lvBySymbol;

    @FXML TextField tfByMassNumber;
    @FXML Button btnByMassNumber;
    @FXML ListView<String> lvByMassNumber;

    @FXML TextField tfByChargeNumber;
    @FXML Button btnByChargeNumber;
    @FXML ListView<String> lvByChargeNumber;

    @FXML TextField tfById;
    @FXML Button btnById;
    @FXML ListView<String> lvById;

    /* ----- Setting events to view elements ---- */

    public void setEvents(){

        setEventToListViews(lvBySymbol, lvByMassNumber, lvByChargeNumber, lvById);

        /* ----  Searching nuclides by symbol  ---- */
        tfBySymbol.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)) findBySymbol(tfBySymbol, lvBySymbol);
        });
        btnBySymbol.setOnAction(event -> findBySymbol(tfBySymbol, lvBySymbol));

        /* ----  Searching nuclides by mass number  ---- */
        tfByMassNumber.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)) findByMassNumber(tfByMassNumber, lvByMassNumber);
        });
        btnByMassNumber.setOnAction(event -> findByMassNumber(tfByMassNumber, lvByMassNumber));

        /* ----  Searching nuclides by charge number  ---- */
        tfByChargeNumber.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)) findByChargeNumber(tfByChargeNumber, lvByChargeNumber);
        });
        btnByChargeNumber.setOnAction(event -> findByChargeNumber(tfByChargeNumber, lvByChargeNumber));

        /* ----  Searching nuclides by nucid  ---- */
        tfById.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)) findById(tfById, lvById);
        });
        btnById.setOnAction(event -> findById(tfById, lvById));
    }

    private void setEventToListViews(ListView<String>... views){
        Arrays.stream(views).forEach(listView ->
                listView.getSelectionModel()
                        .selectedItemProperty()
                        .addListener((observable, oldValue, newValue) -> {
                            if (Objects.nonNull(newValue)) getNuclidProperties(newValue);
                        }));

    }

    /* ----  Searching nuclides by symbol  ---- */

    private void findBySymbol(TextField textField, ListView<String> resultListView){
        textField.setStyle("-fx-text-fill: black");
        resultListView.getItems().clear();
        if (Helper.checkElementSymbol(textField)) {
            String symbol = textField.getText();
            LOGGER.info("Finding nuclide with symbol: {} .", symbol);
            setDistinctItems(resultListView, repository.findBySymbolOnlyId(symbol));
            if(resultListView.getItems().size() == 0) {
                textField.setStyle("-fx-text-fill: red");
                textField.setText("Not Found");
                LOGGER.info("Nuclide with symbol: {} is not found!", symbol);
            } else LOGGER.info("{} results found.", resultListView.getItems().size());
        } else {
            textField.setStyle("-fx-text-fill: red");
            textField.setText("Invalid input");
        }
    }

    /* ----  Searching nuclides by mass number  ---- */

    private void findByMassNumber(TextField textField, ListView<String> resultListView){
        textField.setStyle("-fx-text-fill: black");
        resultListView.getItems().clear();
        if (Helper.checkNumber(textField)) {
            int massNumber = Integer.parseInt(textField.getText());
            LOGGER.info("Finding nuclide with mass number: {} .", massNumber);
            setDistinctItems(resultListView, repository.findByMassNumberOnlyId(massNumber));
            if(resultListView.getItems().size() == 0) {
                textField.setStyle("-fx-text-fill: red");
                textField.setText("Not Found");
                LOGGER.info("Nuclide with mass number: {} is not found!", massNumber);
            }
        } else {
            textField.setStyle("-fx-text-fill: red");
            textField.setText("Invalid input");
        }
    }

    /* ----  Searching nuclides by charge number  ---- */

    private void findByChargeNumber(TextField textField, ListView<String> resultListView){
        textField.setStyle("-fx-text-fill: black");
        resultListView.getItems().clear();
        if (Helper.checkNumber(textField)) {
            int chargeNumber = Integer.parseInt(textField.getText());
            LOGGER.info("Finding nuclide with charge number: {} .", chargeNumber);
            setDistinctItems(resultListView, repository.findByZOnlyId(chargeNumber));
            if(resultListView.getItems().size() == 0) {
                textField.setStyle("-fx-text-fill: red");
                textField.setText("Not Found");
                LOGGER.info("Nuclide with charge number: {} is not found!", chargeNumber);
            }
        } else {
            textField.setStyle("-fx-text-fill: red");
            textField.setText("Invalid input");
        }
    }

    /* ----  Searching nuclides by id  ---- */

    private void findById(TextField textField, ListView<String> resultListView){
        textField.setStyle("-fx-text-fill: black");
        resultListView.getItems().clear();
        if (Helper.checkNuclideId(textField)) {
            String id = textField.getText();
            LOGGER.info("Finding nuclide with id: {} .", id);
            setDistinctItems(resultListView, repository.findByIdOnlyId(id));
            if(resultListView.getItems().size() == 0) {
                textField.setStyle("-fx-text-fill: red");
                textField.setText("Not Found");
                LOGGER.info("Nuclide with id: {} is not found!", id);
            }
        } else {
            textField.setStyle("-fx-text-fill: red");
            textField.setText("Invalid input");
        }
    }

   /*---------------------------------------------------------------*/

    private void getNuclidProperties(String nucId){
        String text = repository.findById(nucId)
                .stream()
                .map(Isotope::toString)
                .reduce(String::concat).orElse("Error");
        TextArea textArea = new TextArea(text);
        Stage stage = new Stage();
        stage.setScene(new Scene(textArea));
        stage.show();
    }

    private void setDistinctItems(ListView<String> listView, List<String> nucIds){
        listView.setItems(
                FXCollections.observableArrayList(
                        nucIds.stream()
                                .distinct()
                                .collect(Collectors.toList()))
        );

    }
}
