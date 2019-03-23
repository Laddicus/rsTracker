package sample;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javax.swing.plaf.SliderUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Controller implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private LineChart<Number, Number> priceChart;
    @FXML
    private LineChart<Number, Number> quantityChart;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private TextArea textArea;
    @FXML
    private Label buyPrice;
    @FXML
    private Label sellPrice;
    @FXML
    private Label overallPrice;
    @FXML
    private Label buyQuantity;
    @FXML
    private Label sellQuantity;
    @FXML
    private Label overallQuantity;

    public void buttonClick() {
        String searchText = searchField.getText();
        System.out.println(searchText);
        List itemList = queryDB.query(searchText);
        if (itemList != null) {
            item temp = (item) itemList.get(itemList.size()-1);

            buyPrice.setText(String.valueOf(temp.getBuy_average()));
            sellPrice.setText(String.valueOf(temp.getSell_average()));
            overallPrice.setText(String.valueOf(temp.getOverall_average()));
            buyQuantity.setText(String.valueOf(temp.getBuy_quantity()));
            sellQuantity.setText(String.valueOf(temp.getSell_quantity()));
            overallQuantity.setText(String.valueOf(temp.getOverall_quantity()));
        }
        // Charts

        //Price Chart
        // Labels
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Time");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Price");

        // Data
        XYChart.Series<Number, Number> bpSeries = new XYChart.Series<Number, Number>();
        XYChart.Series<Number, Number> spSeries = new XYChart.Series<Number, Number>();

        bpSeries.setName("Buy");
        spSeries.setName("Sell");

        int i = 0;
        while (true) {
            try {
                item temp = (item) itemList.get(i);
                bpSeries.getData().add(new XYChart.Data<>(i, temp.buy_average));
                spSeries.getData().add(new XYChart.Data<>(i, temp.sell_average));
                i++;
            } catch (Exception e) {
                break;
            }
        }

        priceChart.getData().add(bpSeries);
        priceChart.getData().add(spSeries);



        //Quantity Chart
        // Labels
        //NumberAxis xAxis = new NumberAxis(213, 253,10);
        //xAxis.setLabel("Time");

        //NumberAxis yAxis = new NumberAxis(0,7,1);
        //yAxis.setLabel("Quantity");

        // Data
        XYChart.Series<Number, Number> bqSeries = new XYChart.Series<Number, Number>();
        XYChart.Series<Number, Number> sqSeries = new XYChart.Series<Number, Number>();

        bqSeries.setName("Buy");
        sqSeries.setName("Sell");

        int j = 0;
        while (true) {// the original entry was at 2019 03 07 02:23
            try {
                item temp = (item) itemList.get(j);
                bqSeries.getData().add(new XYChart.Data<>(j, temp.buy_quantity));
                sqSeries.getData().add(new XYChart.Data<>(j, temp.sell_quantity));
                j++;
            } catch (Exception e) {
                break;
            }
        }

        quantityChart.getData().add(bqSeries);
        quantityChart.getData().add(sqSeries);
    }
    public void changed(ActionEvent event){

    }
}
