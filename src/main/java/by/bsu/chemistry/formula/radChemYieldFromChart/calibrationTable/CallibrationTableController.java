package by.bsu.chemistry.formula.radChemYieldFromChart.calibrationTable;

import by.bsu.chemistry.Controller;
import by.bsu.chemistry.formula.radChemYieldFromChart.calibrationChart.CalibrationChartController;
import by.bsu.chemistry.formula.radChemYieldFromChart.calibrationChart.CalibrationChartView;
import by.bsu.chemistry.formula.radChemYieldFromChart.calibrationChart.CalibrationResult;
import by.bsu.chemistry.util.Helper;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;

/**
 * Created by Ivan on 09.09.2017.
 */
@FXMLController
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CallibrationTableController implements Controller {

    private Integer COUNTER = 1;

    private final ObservableList<CalibrationResult> data = FXCollections.observableArrayList(
            new CalibrationResult(COUNTER++, 5e-5, 0.015),
            new CalibrationResult(COUNTER++,8e-5, 0.025),
            new CalibrationResult(COUNTER++,1e-4, 0.03),
            new CalibrationResult(COUNTER++,4e-4, 0.122)
    );

    @Autowired
    CalibrationChartController chartController;

    ConfigurableApplicationContext context;

    private final StringConverter<Double> converter;

    @Autowired
    public CallibrationTableController(ConfigurableApplicationContext context) {
        this.context = context;
        this.converter = Helper.getConverter();
    }

    @FXML
    TableView<CalibrationResult> table;

    @FXML
    TableColumn<CalibrationResult, Integer> idColumn;

    @FXML
    TableColumn<CalibrationResult, Double> conColumn;

    @FXML
    TableColumn<CalibrationResult, Double> densityColumn;

    @FXML
    TableColumn<CalibrationResult, Boolean> checkColumn;

    @FXML
    TextField conField;

    @FXML
    TextField densityField;

    @FXML
    Button addButton;

    @FXML
    Button nextButton;

    @Override
    public void setEvents() {

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

    private void showCalibrationChart(){
        Stage stage = new Stage();
        chartController.setData(data.filtered(CalibrationResult::isSelected));

        // TODO: 13.09.2017 Подумать насчёт контекста, нужен ли он тут или можно привязать вьюху?
        Scene scene = new Scene(context.getBean(CalibrationChartView.class).getView());
        scene.getStylesheets().add(getClass().getClassLoader().getResource("lineChart.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
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
                    (t.getTableView().getItems()
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
