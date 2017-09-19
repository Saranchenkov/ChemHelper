package by.bsu.chemistry.formula.radiation_chemical_yield_chart;

import by.bsu.chemistry.AbstractController;
import by.bsu.chemistry.util.Helper;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ivan on 11.09.2017.
 */

public class FinalChartController extends AbstractController {

    private final String EXCEL_PATTERN_PATH = "/" + getClass().getPackage().getName().replaceAll("\\.", "/") + "/radChemYield.xlsx";
    private final double CONVERSION_COEFFICIENT = 6.022_140e6 * 1.602_176;

    private final List<FinalResult> data;
    private final double solutionDensity;
    private final double doseRate;
    private final YieldUnit unit;

    FinalChartController(List<FinalResult> data, double solutionDensity, double doseRate, YieldUnit unit) {
        this.data = data;
        this.solutionDensity = solutionDensity;
        this.doseRate = doseRate;
        this.unit = unit;
    }

    /* FXML components */
    @FXML LineChart<Double, Double> chart;
    @FXML Label equation;
    @FXML Label rSquared;
    @FXML Label yield;
    @FXML Button saveButton;

    @Override
    public void setEvents() {

        XYChart.Series<Double, Double> selectedSeries = new XYChart.Series<>();
        XYChart.Series<Double, Double> unselectedSeries = new XYChart.Series<>();
        fillSeries(selectedSeries, unselectedSeries);

        double[][] points = selectedSeries.getData()
                .stream()
                .map(point -> new double[]{point.getXValue(), point.getYValue()})
                .toArray(double[][]::new);

        TrendLine trendLine = new TrendLine(points, true);
        XYChart.Series<Double, Double> trendSeries = trendLine.getTrendlineSeries();

        equation.setText("Equation of trendline: ".concat(trendLine.getEquation()));
        rSquared.setText("R squared = ".concat(String.format(" %.5f", trendLine.getRSquare())));
        yield.setText(getYieldAsString(trendLine));

        chart.getData().setAll(selectedSeries, unselectedSeries, trendSeries);

        saveButton.setOnAction(event -> {
            File selectedFile = new FileChooser().showSaveDialog(saveButton.getScene().getWindow());
            saveChartOnDesktop(getTargetFilePath(selectedFile), calculateYield(trendLine.getSlope()));
        });
    }

    private void saveChartOnDesktop(String filePath, double yield) {
            try(BufferedInputStream inputStream = new BufferedInputStream(getClass().getResourceAsStream(EXCEL_PATTERN_PATH));
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath))) {

                Workbook workBook = new XSSFWorkbook(OPCPackage.open(inputStream));
                Sheet sheet = workBook.getSheetAt(0);

                Font font = workBook.createFont();
                font.setFontName("Times New Roman");
                font.setFontHeightInPoints((short) 14);

                CellStyle cellStyle = workBook.createCellStyle();
                cellStyle.setFont(font);

                Row row;

                for(int i = 0; i < data.size(); i++){
                    row = sheet.createRow(i + 2);

                    row.createCell(1).setCellValue(data.get(i).getTime()*60 * doseRate);
                    row.getCell(1).setCellStyle(cellStyle);

                    row.createCell(2).setCellValue(data.get(i).getConcentration());
                    row.getCell(2).setCellStyle(cellStyle);
                }

                sheet.getRow(2).createCell(4).setCellValue(yield);
                sheet.getRow(2).getCell(4).setCellStyle(cellStyle);

                if (unit == YieldUnit.PER_JOULE) sheet.getRow(1).getCell(4).setCellValue("Yield, mol/J");

                workBook.write(outputStream);
            } catch (FileNotFoundException | InvalidFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private String getTargetFilePath(File targetFile){
        if (Objects.isNull(targetFile)) {
            return new File(Helper.DESKTOP_PATH, "noNameChart.xlsx").getPath();
        } else {
            if (!targetFile.getName().contains(".")){
                return new File(targetFile.getAbsolutePath().concat(".xlsx")).getPath();
            } else return targetFile.getPath();
        }
    }

    private String getYieldAsString(TrendLine trendLine){
        return "G = ".concat(String.format(" %.3E %c %.3E %s",
                                            calculateYield(trendLine.getSlope()),
                                            '\u00B1',
                                            calculateYield(trendLine.getSlopeStandardError()),
                                            unit.toString()));
}

    private double calculateYield(double slope){
        double perJoule = slope / solutionDensity;
        return unit == YieldUnit.PER_100EV ? perJoule * CONVERSION_COEFFICIENT : perJoule;
    }

    private void fillSeries(XYChart.Series<Double, Double> selected, XYChart.Series<Double, Double> unselected){
        for(FinalResult result : data){
            if (result.isSelected()) {
                selected.getData().add(new XYChart.Data<>(
                        result.getTime() * 60 * doseRate,
                        result.getConcentration()
                ));
            } else {
                unselected.getData().add(new XYChart.Data<>(
                        result.getTime() * 60 * doseRate,
                        result.getConcentration()
                ));
            }
        }
    }

}
