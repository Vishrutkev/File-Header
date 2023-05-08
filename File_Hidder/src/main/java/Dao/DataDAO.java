package Dao;

import Model.Data;
import db.MyConnection;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataDAO {

    public static List<Data> getAllFiles(String email) throws SQLException {
        List<Data> res = new ArrayList<>();
        Connection conn = MyConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from data where email = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int id = rs.getInt(1);
            String fileName = rs.getString(2);
            String path = rs.getString(3);

            Data d = new Data(id, fileName, path);
            res.add(d);
        }

        return res;
    }


    public static int hideFile(Data d) throws SQLException, IOException {
        Connection conn = MyConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("insert into data values(default, ?, ?, ?, ?)");
        ps.setString(1, d.getFileName());
        ps.setString(2, d.getPath());
        ps.setString(3, d.getEmail());
        File f = new File(d.getPath());
        FileReader fr = new FileReader(f);
        ps.setCharacterStream(4, fr, f.length());

        int ans = ps.executeUpdate();
        fr.close();
        f.delete();

        return ans;
    }

    public static void unHide(int id) throws SQLException, IOException{
        Connection conn = MyConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select path, bin_data from data where id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String path = rs.getString("path");
        Clob c = rs.getClob("bin_data");

        Reader r = c.getCharacterStream();
        FileWriter fw = new FileWriter(path);
        int i;
        while ((i = r.read()) != -1){
            fw.write((char) i);
        }
        fw.close();

        ps = conn.prepareStatement("delete from data where id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("File Successfully UnHidden");


    }


}
