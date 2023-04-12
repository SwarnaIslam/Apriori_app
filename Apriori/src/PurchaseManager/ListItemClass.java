package PurchaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListItemClass {
    ObservableList<Product> list = FXCollections.observableArrayList();

    public ListItemClass() {

        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:apriori.db");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e + "");
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Product> itemsList = new ArrayList<>();

        try {
            String sql = "SELECT* FROM items";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String details = rs.getString("details");
                int price = rs.getInt("price");

                itemsList.add(new Product(name, 0, price, details));

                System.out.println(id + ". " + name + " " + details + " " + price);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e + "");
            }

            list = FXCollections.observableArrayList(itemsList);


        }

    }
}