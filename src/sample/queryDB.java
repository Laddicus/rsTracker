package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class queryDB {
    public static List query(String search){
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:rs.db");

            Statement statement = conn.createStatement();

            List itemObject = new ArrayList();
            ResultSet resultSet = statement.executeQuery(format("SELECT * FROM [%s]", search));

            int i = 1;
            while (resultSet.next()){
                itemObject.add(new item(i, resultSet.getInt("buy_average"), resultSet.getInt("buy_quantity"),resultSet.getInt("sell_average"), resultSet.getInt("sell_quantity"), resultSet.getInt("overall_average"), resultSet.getInt("overall_quantity")));
                i++;
            }
            System.out.println(itemObject.get(itemObject.size()-1));
            return itemObject;
        }catch (Exception e){
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            return null;
        }
    }
}
