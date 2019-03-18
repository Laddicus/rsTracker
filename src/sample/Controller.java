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
    private LineChart priceChart;
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

        NumberAxis xAxis = new NumberAxis(213, 253,10);
        xAxis.setLabel("Random Units");

        NumberAxis yAxis = new NumberAxis(0,7,1);
        yAxis.setLabel("Other Unit");

        LineChart lineChart = new LineChart(xAxis,yAxis);

        XYChart.Series series = new XYChart.Series();
        series.setName("Yo");
        int i = 0;
        /*
        while (true) {
            try {
                item temp = (item) itemList.get(i);
                series.getData().add(new XYChart.Data(i, 2));
                i++;
            } catch (Exception e) {
                break;
            }
        }
        */
        //quantityChart.getData().add(series);
        series.getData().add(new XYChart.Data(213,2));
        series.getData().add(new XYChart.Data(223,3));
        series.getData().add(new XYChart.Data(233,5));
        series.getData().add(new XYChart.Data(243,5));
        series.getData().add(new XYChart.Data(253,2));

        quantityChart.getData().add(series);
    }
    public void changed(ActionEvent event){

    }
}
