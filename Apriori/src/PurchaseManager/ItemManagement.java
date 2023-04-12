package PurchaseManager;

import Database.DatabaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemManagement {
    private static List<Items>itemsList=new ArrayList<>();
    public static void showAllItems() throws SQLException {
        Connection con= DatabaseConnect.connection();
        PreparedStatement ps=null;
        ResultSet rs=null;

        try{
            String sql="SELECT* FROM items";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();


            while(rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                String details=rs.getString("details");
                int price=rs.getInt("price");

                itemsList.add(new Items(name,details,price));

                System.out.println(id+". "+name+" "+details+" "+price);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        finally {
            try{
                rs.close();
                ps.close();
                con.close();
            }
            catch (SQLException e){
                System.out.println(e+"");
            }
        }
    }
}
