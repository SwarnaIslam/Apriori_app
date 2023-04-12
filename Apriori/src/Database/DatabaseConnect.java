package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    public static Connection connection(){
        Connection con=null;
        try{
            Class.forName("org.sqlite.JDBC");
            con= DriverManager.getConnection("jdbc:sqlite:apriori.db");
        }
        catch (ClassNotFoundException| SQLException e){
            System.out.println(e+"");
        }
        return con;
    }
}
