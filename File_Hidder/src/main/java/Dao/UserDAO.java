package Dao;

import Model.User;
import db.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean isUserExist(String email) throws SQLException {
        Connection conn = MyConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select email from users");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String mail = rs.getString(1);
            if(mail.equals(email)){
                return true;
            }
        }
        return false;
    }

    public static int insertUser(User u) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("insert into users values(default, ?, ?)");
        ps.setString(1, u.getName());
        ps.setString(2, u.getEmail());
        return ps.executeUpdate();
    }


}
