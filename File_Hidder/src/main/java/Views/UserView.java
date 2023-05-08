package Views;

import Dao.DataDAO;
import Model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {

    private String email;

    public UserView(String email){
        this.email = email;
    }

    public void home()  {

        System.out.println("Welcome " + this.email );
        do{
            System.out.println("Press 1 to show hidden files");
            System.out.println("Press 2 to hide a file");
            System.out.println("Press 3 to Unhidden a file");
            System.out.println("Press 0 to exit");
            Scanner sc = new Scanner(System.in);

            int ch = Integer.parseInt(sc.nextLine());

            switch (ch){
                case 1 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(this.email);
                        System.out.println("ID - FIle name");
                        for(Data d: files){
                            System.out.println(d.getId() + " - " + d.getFileName());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.println("Enter the file path");
                    String path = sc.nextLine();
                    File f = new File(path);
                    Data file = new Data(0, f.getName(), path, this.email);
                    try {
                        DataDAO.hideFile(file);
                        System.out.println("Your File has been Successfully Hidden");
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }

                }
                case 3 ->{
                    List<Data> files = null;
                    try {
                        files = DataDAO.getAllFiles(this.email);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.out.println("ID - FIle name");
                    for(Data d: files){
                        System.out.println(d.getId() + " - " + d.getFileName());
                    }
                    System.out.println("Enter the ID of file to unHide");
                    int id = Integer.parseInt(sc.nextLine());
                    boolean isValidID = false;
                    for(Data file : files){
                        if(file.getId() == id){
                            isValidID = true;
                            break;
                        }
                    }
                    if(isValidID){
                        try {
                            DataDAO.unHide(id);
                        } catch (SQLException | IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        System.out.println("Wrong ID");
                    }

                }
                case 0 ->{
                    System.exit(0);
                }
            }
        }while(true);
    }
}
