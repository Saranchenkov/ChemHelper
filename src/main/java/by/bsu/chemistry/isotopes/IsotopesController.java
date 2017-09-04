package by.bsu.chemistry.isotopes;

import by.bsu.chemistry.Controller;
import by.bsu.chemistry.util.Helper;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ivan on 03.09.2017.
 */

@FXMLController
public class IsotopesController implements Controller{

    private final Logger LOGGER = LoggerFactory.getLogger(IsotopesController.class);

    private final IsotopesRepository repository;

    @Autowired
    public IsotopesController(IsotopesRepository repository) {
        this.repository = repository;
    }

    public void findBySymbol(TextField textField, ListView<String> resultListView){
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
            // TODO: 04.09.2017 Делать красным promptText
            textField.setStyle("-fx-text-fill: red");
            textField.setText("Invalid input");
        }
    }

    public void findByMassNumber(TextField textField, ListView<String> resultListView){
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

    public void findByChargeNumber(TextField textField, ListView<String> resultListView){
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

    public void findById(TextField textField, ListView<String> resultListView){
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

    public void getNuclidProperties(String nucId){
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
