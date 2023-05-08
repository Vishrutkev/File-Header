package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

    static Connection connection;

    public static Connection getConnection()  {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fhproject?useSSL=false", "root", "");
            //System.out.println("Connection Successfully");
        }catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }

        return connection;

    }

    public static void closeConnection() throws SQLException {
        if(connection != null){
            connection.close();
        }
    }


}
