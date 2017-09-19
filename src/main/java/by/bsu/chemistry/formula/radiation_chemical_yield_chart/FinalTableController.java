package by.bsu.chemistry.formula.radiation_chemical_yield_chart;

import by.bsu.chemistry.AbstractController;
import by.bsu.chemistry.util.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import static by.bsu.chemistry.formula.radiation_chemical_yield_chart.YieldUnit.PER_100EV;
import static by.bsu.chemistry.formula.radiation_chemical_yield_chart.YieldUnit.PER_JOULE;

/**
 * Created by Ivan on 11.09.2017.
 */
public class FinalTableController extends AbstractController {

    private Integer COUNTER = 1;

    private final ObservableList<FinalResult> data = FXCollections.observableArrayList(
            new FinalResult(COUNTER++, 0d, 0.005),
            new FinalResult(COUNTER++,5, 0.004),
            new FinalResult(COUNTER++,10, 0.009),
            new FinalResult(COUNTER++,15, 0.013),
            new FinalResult(COUNTER++,31, 0.028),
            new FinalResult(COUNTER++,31, 0.025)
    );

    private FinalChartController finalChartController;

    private final StringConverter<Double> converter;
    private final TrendLine trendLine;
    private final TabPane tabPane;

    FinalTableController(TrendLine trendLine, TabPane tabPane) {
        this.converter = Helper.getConverter();
        this.trendLine = trendLine;
        this.tabPane = tabPane;
    }

    @FXML TableView<FinalResult> table;

    @FXML TableColumn<FinalResult, Integer> idColumn;
    @FXML TableColumn<FinalResult, Double> timeColumn;
    @FXML TableColumn<FinalResult, Double> densityColumn;
    @FXML TableColumn<FinalResult, Double> conColumn;
    @FXML TableColumn<FinalResult, Boolean> checkColumn;

    @FXML TextField timeField;
    @FXML TextField densityField;

    @FXML Button addButton;
    @FXML Button nextButton;
    @FXML Button calculateCons;

    @FXML TextField solutionDensityField;
    @FXML TextField doseRateField;

    @FXML ChoiceBox<String> yieldUnitChoice;

    @Override
    public void setEvents() {

        table.getSelectionModel().setCellSelectionEnabled(true);

        setCellValueFactories();
        setCellFactories();
        setOnEditCommit();
        setOnAction();
        yieldUnitChoice.setItems(FXCollections.observableArrayList(PER_100EV.toString(), PER_JOULE.toString()));
        yieldUnitChoice.getSelectionModel().selectFirst();
        table.setItems(data);
    }

    private void showFinalChart(){
        if (Helper.checkDouble(solutionDensityField)){
            if (Helper.checkDouble(doseRateField)){
                finalChartController = new FinalChartController(
                                            data,
                                            Helper.getDouble(solutionDensityField),
                                            Helper.getDouble(doseRateField),
                                            YieldUnit.getBykey(yieldUnitChoice.getSelectionModel().getSelectedItem())
                );
                Tab newTab = ((TabPane)finalChartController.getView()).getTabs().get(0);
                tabPane.getTabs().addAll(newTab);
                tabPane.getSelectionModel().select(newTab);
            } else {doseRateField.setText("invalid input!");}
        } else {solutionDensityField.setText("invalid input!");}
    }

    private void setCellValueFactories(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        densityColumn.setCellValueFactory(new PropertyValueFactory<>("opticalDensity"));
        conColumn.setCellValueFactory(new PropertyValueFactory<>("concentration"));
        checkColumn.setCellValueFactory( new PropertyValueFactory<>( "selected" ));
    }

    private void setCellFactories(){
        timeColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        densityColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        conColumn.setCellFactory(column -> new TextFieldTableCell<FinalResult, Double>(converter){
                @Override
                public void updateItem(Double item, boolean empty) {
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

        nextButton.setOnAction(event -> showFinalChart());

        calculateCons.setOnAction(event -> {
            data.forEach(result -> result.setConcentration(trendLine.getXFromEquation(result.getOpticalDensity())));
        });
    }
}