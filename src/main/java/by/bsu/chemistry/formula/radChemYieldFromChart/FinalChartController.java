package by.bsu.chemistry.formula.radChemYieldFromChart;

import by.bsu.chemistry.AbstractController;
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
public class FinalChartController extends AbstractController {

    private final String EXCEL_PATTERN_PATH = "/" + getClass().getPackage().getName().replaceAll("\\.", "/") + "/radChemYield.xlsx";
    private final double CONVERSION_COEFFICIENT = 6.022_140e6/1.602_176;

    private final List<FinalResult> data = new ArrayList<>();

    private double solutionDensity;
    private double doseRate;
    private YieldUnit unit;

    @FXML LineChart<Double, Double> chart;

    @FXML Label equation;
    @FXML Label rSquared;
    @FXML Label yield;

    @FXML Button saveButton;

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

        equation.setText("Equation of trendline: ".concat(trendLine.getEquation()));
        rSquared.setText("R squared = ".concat(String.format(" %.5f", trendLine.getRSquared())));
        yield.setText("G = ".concat(String.format(" %.3e %s", calculateYield(trendLine.getSlope(), unit), unit.toString())));
        chart.getData().setAll(series, trendSeries);

        saveButton.setOnAction(event -> {
            File selectedFile = new FileChooser().showSaveDialog(saveButton.getScene().getWindow());
            saveChartOnDesktop(getTargetFilePath(selectedFile), calculateYield(trendLine.getSlope(), unit));
        });
    }

    void setData(List<FinalResult> data){
        this.data.clear();
        this.data.addAll(data);
    }

    private void saveChartOnDesktop(String filePath, double yield) {
            try(BufferedInputStream inputStream = new BufferedInputStream(getClass().getResourceAsStream(EXCEL_PATTERN_PATH));
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath))) {

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

                workBook.write(outputStream);
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
        double per100eV = (9.65e6 * slope) / solutionDensity;
        return unit == PER_100EV ? per100eV : per100eV / CONVERSION_COEFFICIENT;
    }

    public void setYieldParameters(double solutionDensity, double doseRate, YieldUnit unit){
        this.solutionDensity = solutionDensity;
        this.doseRate = doseRate;
        this.unit = unit;
    }
}
