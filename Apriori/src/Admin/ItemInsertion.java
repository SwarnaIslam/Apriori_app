package Admin;

import Database.DatabaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemInsertion {
    public static void insert(String name, String details, int price) throws SQLException {
        String tableName="items";
        Connection con= DatabaseConnect.connection();
        PreparedStatement ps;
        try{
            String sql="INSERT INTO "+tableName+" (name, details, price) VALUES(?,?,?)";
            ps=con.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,details);
            ps.setInt(3,price);
            ps.execute();
        }
        catch (SQLException e){
            System.out.println(e+"Insert");
        }
        finally {
            con.close();

        }
    }
}
