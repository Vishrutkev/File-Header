import Views.Welcome;

import java.sql.SQLException;

public class Main {


    public static void main(String[] args) throws SQLException {
        Welcome wc = new Welcome();
        do{
            wc.welcomeScreen();
        }while (true);

    }
}
