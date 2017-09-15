package by.bsu.chemistry.formula.radChemYieldFromChart.finalChart;

import by.bsu.chemistry.Controller;
import by.bsu.chemistry.formula.radChemYieldFromChart.TrendLine;
import by.bsu.chemistry.formula.radChemYieldFromChart.YieldUnit;
import by.bsu.chemistry.formula.radChemYieldFromChart.finalTable.FinalResult;
import by.bsu.chemistry.util.Helper;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static by.bsu.chemistry.formula.radChemYieldFromChart.YieldUnit.PER_100EV;

/**
 * Created by Ivan on 11.09.2017.
 */

@FXMLController
public class FinalChartController implements Controller{

    private final List<FinalResult> data = new ArrayList<>();

    private final String EXCEL_PATTERN_PATH = "/" + getClass().getPackage().getName().replaceAll("\\.", "/") + "/radChemYield.xlsx";

    private final double CONVERSION_COEFFICIENT = 6.022_140e6/1.602_176;

    private double solutionDensity;

    private double doseRate;

    private YieldUnit unit;

    @FXML
    LineChart<Double, Double> chart;

    @FXML
    Label equation;

    @FXML
    Label rSquared;

    @FXML
    Label yield;

    @FXML
    Button saveButton;

    @Override
    public void setEvents() {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        data.forEach(result -> series.getData()
                .add(new XYChart.Data<Double, Double>(
                        result.getTime() * 60 * doseRate,
                        result.getConcentration()
                )));

        TrendLine trendLine = new TrendLine(data.stream()
                .map(result -> new Double[]{result.getTime() * 60 * doseRate, result.getConcentration()})
                .collect(Collectors.toList()));
        XYChart.Series<Double, Double> trendSeries = trendLine.getTrendlineSeries();

        equation.setText(equation.getText().concat(trendLine.getEquation()));
        rSquared.setText(rSquared.getText().concat(String.format(" %.5f", trendLine.getRSquared())));
        yield.setText(yield.getText().concat(String.format(" %.3f %s", calculateYield(trendLine.getSlope(), unit), unit.toString())));
        chart.getData().addAll(series, trendSeries);

        saveButton.setOnAction(event -> {
            File selectedFile = new FileChooser().showSaveDialog(saveButton.getScene().getWindow());
            saveChartOnDesktop(getTargetFilePath(selectedFile), calculateYield(trendLine.getSlope(), unit));
        });
    }

    public void setData(List<FinalResult> data){
        this.data.clear();
        this.data.addAll(data);
    }

    private void saveChartOnDesktop(String filePath, double yield) {
            try(InputStream inputStream = getClass().getResourceAsStream(EXCEL_PATTERN_PATH);
                FileOutputStream fos = new FileOutputStream(filePath)) {

                Workbook workBook = new XSSFWorkbook(OPCPackage.open(inputStream));
                Sheet sheet = workBook.getSheetAt(0);

                for(int i = 0; i < data.size(); i++){
                    sheet.createRow(i + 2)
                            .createCell(1)
                            .setCellValue(data.get(i).getTime()*60 * doseRate);
                    sheet.getRow(i + 2)
                            .createCell(2)
                            .setCellValue(data.get(i).getConcentration());
                }

                sheet.getRow(2).createCell(4).setCellValue(yield);

                workBook.write(fos);
            } catch (FileNotFoundException | InvalidFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private String getTargetFilePath(File targetFile){
        if (Objects.isNull(targetFile)) {
            return new File(Helper.DESKTOP_PATH, "finalChart.xlsx").getPath();
        } else {
            if (!targetFile.getName().contains(".")){
                return new File(targetFile.getAbsolutePath().concat(".xlsx")).getPath();
            } else return targetFile.getPath();
        }
    }

    private double calculateYield(double slope, YieldUnit unit){
        double per100eV = (9.65e8 * slope) / solutionDensity;
        return unit == PER_100EV ? per100eV : per100eV * CONVERSION_COEFFICIENT;
    }

    public void setYieldParameters(double solutionDensity, double doseRate, YieldUnit unit){
        this.solutionDensity = solutionDensity;
        this.doseRate = doseRate;
        this.unit = unit;
    }
}
