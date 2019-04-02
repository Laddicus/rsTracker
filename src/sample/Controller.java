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
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.valueOf;


public class Controller implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private LineChart<Number, Number> priceChart;
    @FXML
    private LineChart<Number, Number> quantityChart;
    @FXML
    private LineChart<Number, Number> priceCompareChart;
    @FXML
    private LineChart<Number, Number> quantityCompareChart;
    @FXML
    private NumberAxis quantityYAxis;
    @FXML
    private NumberAxis priceYAxis;
    @FXML
    private NumberAxis quantityCompareYAxis;
    @FXML
    private NumberAxis priceCompareYAxis;
    @FXML
    private TextField searchField;
    @FXML
    private TextField compareField;
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
    @FXML
    private Label buyPriceCompare;
    @FXML
    private Label sellPriceCompare;
    @FXML
    private Label overallPriceCompare;
    @FXML
    private Label buyQuantityCompare;
    @FXML
    private Label sellQuantityCompare;
    @FXML
    private Label overallQuantityCompare;
    @FXML
    private ListView list;
    @FXML
    private TabPane tabPane;
    @FXML private void addTab(){
        int numTabs = tabPane.getTabs().size();
        Tab tab = new Tab("tab "+(numTabs+1));
        tabPane.getTabs().add(tab);
    }
    @FXML
    private void listTabs(){
        tabPane.getTabs().forEach(tab -> System.out.println(tab.getText()));
        System.out.println();
    }
    @FXML
    private Tab tabTemplate;

    public void newTab(String title) throws IOException {
        tabPane.getTabs().add(new Tab(title));
        tabPane.getTabs().addAll((Tab)FXMLLoader.load(this.getClass().getResource("tabTemplate.fxml")));
    }

    public void initialize() throws IOException {
        priceYAxis.setAutoRanging(false);
        quantityYAxis.setAutoRanging(false);
    }
    public XYChart.Series<Number,Number> makeSeries(List itemList, boolean price, boolean compare, Double highest, Double lowest){

        XYChart.Series<Number,Number> series = new XYChart.Series<>();
        int i = 0;
        while (true) {
            try {
                    item temp = (item) itemList.get(i);
                    if (temp.buy_average != 0) {
                        if (price) {
                            if (price){
                                series.getData().add(new XYChart.Data<>(i, temp.buy_average));
                            }
                            else {
                                series.getData().add(new XYChart.Data<>(i, temp.sell_average));
                            }
                        }
                        else{
                            if (price){
                                series.getData().add(new XYChart.Data<>(i, temp.buy_quantity));
                            }
                            else{
                                series.getData().add(new XYChart.Data<>(i, temp.sell_quantity));
                            }
                        }
                        if (price) {
                            if (temp.buy_average > highest) {
                                highest = Double.valueOf(temp.buy_average);
                            }
                            if (temp.sell_average > highest) {
                                highest = Double.valueOf(temp.sell_average);
                            }
                            if (temp.buy_average < lowest) {
                                lowest = Double.valueOf(temp.buy_average);
                            }
                            if (temp.sell_average < lowest) {
                                lowest = Double.valueOf(temp.sell_average);
                            }
                        }
                        else{
                            if (temp.buy_quantity > highest) {
                                highest = Double.valueOf(temp.buy_quantity);
                            }
                            if (temp.sell_quantity > highest) {
                                highest = Double.valueOf(temp.sell_quantity);
                            }
                            if (temp.buy_quantity < lowest) {
                                lowest = Double.valueOf(temp.buy_quantity);
                            }
                            if (temp.sell_quantity < lowest) {
                                lowest = Double.valueOf(temp.sell_quantity);
                            }
                        }
                    }
                    i++;
            } catch (Exception e) {
                break;
            }
        }
        if (!compare) {
            if (price) {
                priceYAxis.setLowerBound(lowest - 10);
                priceYAxis.setUpperBound(highest + 10);
            } else {
                quantityYAxis.setLowerBound(lowest - 10);
                quantityYAxis.setUpperBound(highest + 10);
            }
        }
        else {
            if (price) {
                priceCompareYAxis.setLowerBound(lowest - 10);
                priceCompareYAxis.setUpperBound(highest + 10);
            } else {
                quantityCompareYAxis.setLowerBound(lowest - 10);
                quantityCompareYAxis.setUpperBound(highest + 10);
            }
        }
        return series;

    }
    public void search() throws IOException {

        priceYAxis.setAutoRanging(false);
        priceYAxis.setTickUnit(MAX_VALUE);
        quantityYAxis.setAutoRanging(false);
        quantityYAxis.setTickUnit(MAX_VALUE);

        priceCompareYAxis.setAutoRanging(false);
        priceCompareYAxis.setTickUnit(MAX_VALUE);
        quantityCompareYAxis.setAutoRanging(false);
        quantityCompareYAxis.setTickUnit(MAX_VALUE);


        String searchText = searchField.getText();
        System.out.println(searchText);
        // everything else
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
        //Ensure charts are empty
        priceChart.getData().clear();
        quantityChart.getData().clear();

        //Price Chart
        // Labels
        // Data
        XYChart.Series<Number, Number> bpSeries = makeSeries(itemList, true, false, 0.0, MAX_VALUE);
        XYChart.Series<Number, Number> spSeries = makeSeries(itemList, true, false, 0.0, MAX_VALUE);

        bpSeries.setName("Buy");
        spSeries.setName("Sell");

        priceChart.getData().add(bpSeries);
        priceChart.getData().add(spSeries);

        //Quantity Chart
        // Data
        XYChart.Series<Number, Number> bqSeries = makeSeries(itemList, false,false, 0.0, MAX_VALUE);
        XYChart.Series<Number, Number> sqSeries = makeSeries(itemList, false, false, 0.0, MAX_VALUE);

        bqSeries.setName("Buy");
        sqSeries.setName("Sell");

        quantityChart.getData().add(bqSeries);
        quantityChart.getData().add(sqSeries);

        System.out.println("Price:\nUpper: " + priceYAxis.getUpperBound()+"\nLower: "+priceYAxis.getLowerBound());
        System.out.println(("Quantity:\nUpper: ") + quantityYAxis.getUpperBound()+ "\nLower: "+quantityYAxis.getLowerBound());
    }
    public void compareSearch() {
        System.out.println(priceYAxis.getUpperBound());
        Double highest;
        Double lowest;
        if (priceYAxis.getUpperBound() == 150.0){
            priceYAxis.setUpperBound(0.0);
            priceYAxis.setLowerBound(MAX_VALUE);
            quantityYAxis.setUpperBound(0.0);
            quantityYAxis.setLowerBound(MAX_VALUE);
        }

        String searchText = compareField.getText();
        System.out.println(searchText);

        priceCompareYAxis.setAutoRanging(false);
        priceCompareYAxis.setTickUnit(MAX_VALUE);
        quantityCompareYAxis.setAutoRanging(false);
        quantityCompareYAxis.setTickUnit(MAX_VALUE);

        List itemList = queryDB.query(searchText);
        if (itemList != null) {
            item temp = (item) itemList.get(itemList.size() - 1);

            buyPriceCompare.setText(String.valueOf(temp.getBuy_average()));
            sellPriceCompare.setText(String.valueOf(temp.getSell_average()));
            overallPriceCompare.setText(String.valueOf(temp.getOverall_average()));
            buyQuantityCompare.setText(String.valueOf(temp.getBuy_quantity()));
            sellQuantityCompare.setText(String.valueOf(temp.getSell_quantity()));
            overallQuantityCompare.setText(String.valueOf(temp.getOverall_quantity()));
        }

        //Price Chart
        // Labels
        // Data
        highest = priceCompareYAxis.getUpperBound();
        lowest = priceCompareYAxis.getLowerBound();

        XYChart.Series<Number, Number> bpSeries = makeSeries(itemList, true, true, highest, lowest);
        XYChart.Series<Number, Number> spSeries = makeSeries(itemList, true, true, highest, lowest);

        bpSeries.setName("Buy");
        spSeries.setName("Sell");

        priceCompareChart.getData().add(bpSeries);
        priceCompareChart.getData().add(spSeries);

        //Quantity Chart
        highest = quantityCompareYAxis.getUpperBound();
        lowest = quantityCompareYAxis.getLowerBound();
        System.out.println("Upper: " + quantityCompareYAxis.getUpperBound());
        // Data
        XYChart.Series<Number, Number> bqSeries = makeSeries(itemList, false, true, highest, lowest);
        XYChart.Series<Number, Number> sqSeries = makeSeries(itemList, false, true, highest, lowest);

        bqSeries.setName("Buy");
        sqSeries.setName("Sell");

        quantityCompareChart.getData().add(bqSeries);
        quantityCompareChart.getData().add(sqSeries);
        System.out.println("Upper: " + quantityCompareYAxis.getUpperBound());
    }
    public void clear(){
        buyPriceCompare.setText("");
        sellPriceCompare.setText("");
        overallPriceCompare.setText("");
        buyQuantityCompare.setText("");
        sellQuantityCompare.setText("");
        overallQuantityCompare.setText("");

        priceCompareYAxis.setUpperBound(0);
        priceCompareYAxis.setLowerBound(MAX_VALUE);
        quantityCompareYAxis.setUpperBound(0);
        quantityCompareYAxis.setLowerBound(MAX_VALUE);
        priceCompareChart.getData().clear();
        quantityCompareChart.getData().clear();

    }
    public void changed(ActionEvent event){

    }
}
