package sample;

import static java.lang.String.format;

public class item {
    public int id, buy_average, buy_quantity, sell_average, sell_quantity, overall_average, overall_quantity;

    public item(int id, int buy_average, int buy_quantity, int sell_average, int sell_quantity, int overall_average, int overall_quantity) {
        this.id = id;
        this.buy_average = buy_average;
        this.buy_quantity = buy_quantity;
        this.sell_average = sell_average;
        this.sell_quantity = sell_quantity;
        this.overall_average = overall_average;
        this.overall_quantity = overall_quantity;
    }

    public int getId() {
        return id;
    }

    public int getBuy_average() {
        return buy_average;
    }

    public int getBuy_quantity() {
        return buy_quantity;
    }

    public int getSell_average() {
        return sell_average;
    }

    public int getSell_quantity() {
        return sell_quantity;
    }

    public int getOverall_average() {
        return overall_average;
    }

    public int getOverall_quantity() {
        return overall_quantity;
    }

    @Override
    public String toString(){
        return format("ID: %d, Buy Average: %d, Buy Quantity: %d, Sell Average: %d, Sell Quantity: %d, Overall Average: %d, Overall Quantity: %d", id, buy_average, buy_quantity, sell_average, sell_quantity, overall_average, overall_quantity);
    }
}
