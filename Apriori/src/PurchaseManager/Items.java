package PurchaseManager;

import Database.DatabaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Items {
    private String name;
    private String details;
    private int price;
    public Items(String name, String details, int price){
        this.name=name;
        this.details=details;
        this.price=price;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public int getPrice() {
        return price;
    }
}
