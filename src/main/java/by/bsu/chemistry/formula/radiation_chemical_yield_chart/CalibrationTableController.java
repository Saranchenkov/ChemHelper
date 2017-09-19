package by.bsu.chemistry.formula.radiation_chemical_yield_chart;

import by.bsu.chemistry.AbstractController;
import by.bsu.chemistry.util.Helper;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Created by Ivan on 09.09.2017.
 */
@FXMLController
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class CalibrationTableController extends AbstractController {

    private Integer COUNTER = 1;

    private final ObservableList<CalibrationResult> data = FXCollections.observableArrayList(
            new CalibrationResult(COUNTER++, 5e-5, 0.015),
            new CalibrationResult(COUNTER++,8e-5, 0.025),
            new CalibrationResult(COUNTER++,1e-4, 0.03),
            new CalibrationResult(COUNTER++,4e-4, 0.122)
    );

    private CalibrationChartController calibrationChartController;
    private final StringConverter<Double> converter;

    public CalibrationTableController() {
        this.converter = Helper.getConverter();

    }

    @FXML TabPane tabPane;

    @FXML TableView<CalibrationResult> table;

    @FXML TableColumn<CalibrationResult, Integer> idColumn;
    @FXML TableColumn<CalibrationResult, Double> conColumn;
    @FXML TableColumn<CalibrationResult, Double> densityColumn;
    @FXML TableColumn<CalibrationResult, Boolean> checkColumn;

    @FXML TextField conField;
    @FXML TextField densityField;

    @FXML Button addButton;
    @FXML Button nextButton;

    @Override
    public void setEvents() {
        table.getSelectionModel().setCellSelectionEnabled(true);

        setCellValueFactories();
        setCellFactories();
        setOnEditCommit();

        table.setItems(data);

        addButton.setOnAction(event -> {
            if (Helper.checkDouble(conField)) {
                if (Helper.checkDouble(densityField)) {
                    data.add(new CalibrationResult(COUNTER++, Helper.getDouble(conField), Helper.getDouble(densityField)));
                    conField.clear();
                    densityField.clear();
                } else {
                    densityField.setText("invalid input");
                }
            } else conField.setText("invalid input");
        });

        nextButton.setOnAction(event -> showCalibrationChart());
    }

    // TODO: 16.09.2017 rename method
    private void showCalibrationChart(){
        calibrationChartController = new CalibrationChartController(data, tabPane);
        Tab newTab = ((TabPane)calibrationChartController.getView()).getTabs().get(0);
        tabPane.getTabs().addAll(newTab);
        tabPane.getSelectionModel().select(newTab);
        // TODO: 16.09.2017 сделать нормальный путь вместо lineChart.css
        tabPane.getStylesheets().add(getClass().getClassLoader().getResource("lineChart.css").toExternalForm());
    }

    private void setCellValueFactories(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        conColumn.setCellValueFactory(new PropertyValueFactory<>("concentration"));
        densityColumn.setCellValueFactory(new PropertyValueFactory<>("opticalDensity"));
        checkColumn.setCellValueFactory( new PropertyValueFactory<>( "selected" ));
    }

    private void setCellFactories(){
        conColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        densityColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        checkColumn.setCellFactory( tc -> new CheckBoxTableCell<>());
    }

    private void setOnEditCommit(){
        conColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<CalibrationResult, Double> t) -> {
                    (t.getTableView()
                            .getItems()
                            .get(t.getTablePosition().getRow()))
                            .setConcentration(t.getNewValue());
                });

        densityColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<CalibrationResult, Double> t) -> {
                    (t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setOpticalDensity(t.getNewValue());
                });
    }
}
