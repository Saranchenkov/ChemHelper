package by.bsu.chemistry.formula.radChemYieldFromChart.finalTable;

import by.bsu.chemistry.Controller;
import by.bsu.chemistry.formula.radChemYieldFromChart.YieldUnit;
import by.bsu.chemistry.formula.radChemYieldFromChart.finalChart.FinalChartController;
import by.bsu.chemistry.formula.radChemYieldFromChart.finalChart.FinalChartView;
import by.bsu.chemistry.formula.radChemYieldFromChart.TrendLine;
import by.bsu.chemistry.util.Helper;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import static by.bsu.chemistry.formula.radChemYieldFromChart.YieldUnit.PER_100EV;
import static by.bsu.chemistry.formula.radChemYieldFromChart.YieldUnit.PER_JOULE;

/**
 * Created by Ivan on 11.09.2017.
 */
@FXMLController
public class FinalTableController implements Controller {

    private Integer COUNTER = 1;

    private final ObservableList<FinalResult> data = FXCollections.observableArrayList(
            new FinalResult(COUNTER++, 0d, 0.005),
            new FinalResult(COUNTER++,5, 0.004),
            new FinalResult(COUNTER++,10, 0.009),
            new FinalResult(COUNTER++,15, 0.013),
            new FinalResult(COUNTER++,31, 0.028),
            new FinalResult(COUNTER++,31, 0.025)
    );

    private TrendLine trendLine;

    @Autowired
    FinalChartController chartController;

    ConfigurableApplicationContext context;

    private final StringConverter<Double> converter;

    @Autowired
    public FinalTableController(ConfigurableApplicationContext context) {
        this.context = context;
        this.converter = Helper.getConverter();
    }

    @FXML
    TableView<FinalResult> table;

    @FXML
    TableColumn<FinalResult, Integer> idColumn;

    @FXML
    TableColumn<FinalResult, Double> timeColumn;

    @FXML
    TableColumn<FinalResult, Double> densityColumn;

    @FXML
    TableColumn<FinalResult, Double> conColumn;

    @FXML
    TableColumn<FinalResult, Boolean> checkColumn;

    @FXML
    TextField timeField;

    @FXML
    TextField densityField;

    @FXML
    Button addButton;

    @FXML
    Button nextButton;

    @FXML
    Button calculateCons;

    @FXML
    TextField solutionDensityField;

    @FXML
    TextField doseRateField;

    @FXML
    ChoiceBox<String> yieldUnitChoice;


    @Override
    public void setEvents() {
        setCellValueFactory();
        setCellFactory();
        setOnEditCommit();
        setOnAction();
        yieldUnitChoice.setItems(FXCollections.observableArrayList(PER_100EV.toString(), PER_JOULE.toString()));
        yieldUnitChoice.getSelectionModel().selectFirst();
        table.setItems(data);
    }

    private void showFinalChart(){
        Stage stage = new Stage();
        chartController.setData(data.filtered(FinalResult::isSelected));
        Scene scene = new Scene(context.getBean(FinalChartView.class).getView());
        scene.getStylesheets().add(getClass().getClassLoader().getResource("lineChart.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void setCellValueFactory(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        densityColumn.setCellValueFactory(new PropertyValueFactory<>("opticalDensity"));
        conColumn.setCellValueFactory(new PropertyValueFactory<>("concentration"));
        checkColumn.setCellValueFactory( new PropertyValueFactory<>( "selected" ));
    }

    private void setCellFactory(){
        timeColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        densityColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        conColumn.setCellFactory(column -> new TableCell<FinalResult, Double>(){
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty){
                    setText(null);
                } else if(item == 0d) {
                    setText(item.toString());
                } else {
                    setText(String.format("%.5E", item));
                }
            }
        });
        checkColumn.setCellFactory( tc -> new CheckBoxTableCell<>());
    }

    private void setOnEditCommit(){
        timeColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<FinalResult, Double> t) -> {
                    (t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setTime(t.getNewValue());
                });

        densityColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<FinalResult, Double> t) -> {
                    (t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setOpticalDensity(t.getNewValue());
                });
    }

    private void setOnAction(){
        addButton.setOnAction(event -> {
            if (Helper.checkDouble(timeField)) {
                if (Helper.checkDouble(densityField)) {
                    data.add(new FinalResult(COUNTER++, Helper.getDouble(timeField), Helper.getDouble(densityField)));
                    timeField.clear();
                    densityField.clear();
                } else {
                    densityField.setText("invalid input");
                }
            } else timeField.setText("invalid input");
        });

        nextButton.setOnAction(event -> {
            if (Helper.checkDouble(solutionDensityField)){
                if (Helper.checkDouble(doseRateField)){
                    chartController.setYieldParameters(
                            Helper.getDouble(solutionDensityField),
                            Helper.getDouble(doseRateField),
                            YieldUnit.getBykey(yieldUnitChoice.getSelectionModel().getSelectedItem())
                    );
                    showFinalChart();
                } else {doseRateField.setText("invalid input!");}
            } else {solutionDensityField.setText("invalid input!");}
        });

        calculateCons.setOnAction(event -> {
            data.forEach(result -> result.setConcentration(trendLine.getXFromEquation(result.getOpticalDensity())));
        });
    }

    void setTrendLine(TrendLine trendLine) {
        this.trendLine = trendLine;
    }
}