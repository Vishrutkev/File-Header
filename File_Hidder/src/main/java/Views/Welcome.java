package Views;

import Dao.UserDAO;
import Model.User;
import Service.GenerateOTP;
import Service.SendOTPService;
import Service.UserService;
import db.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Welcome {

    public void welcomeScreen() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("----Welcome----");
        System.out.println("Press 1 to login");
        System.out.println("Press 2 to signup");
        System.out.println("Press 0 to exit");
        int choice = 0;
        choice = sc.nextInt();

        switch(choice){
            case 1 -> login();
            case 2 -> signUp();
            case 0 -> System.exit(0);
        }

    }

    private void signUp() throws SQLException {

        System.out.println("Enter Your name: ");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        System.out.println("Enter Your Email: ");
        String email = sc.nextLine();

        Connection conn = MyConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement("select * from users");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String m = rs.getString("email");
                if(m.equals(email)){
                    usrAlreadyExisted();
                    return;
                }
            }

        String genOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email, genOTP);
        System.out.println("Enter the otp");
        String userOTP = sc.nextLine();

        if(userOTP.equals(genOTP)){
            User user = new User(name, email);
            int response = UserService.insertUser(user);
            if(response == 1){
                System.out.println("User Registered Successfully");
            }
        }else{
            System.out.println("Entered OTP is Incorrect");
        }

    }

    private void usrAlreadyExisted(){
        System.out.println("User Already Existed");
    }

    private void login(){

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Your Email: ");
        String email = sc.nextLine();

        try{
            if(UserDAO.isUserExist(email)){
                String genOTP = GenerateOTP.getOTP();
                SendOTPService.sendOTP(email, genOTP);
                System.out.println("Enter the otp");
                String userOTP = sc.nextLine();
                if(userOTP.equals(genOTP)){
                    new UserView(email).home();
                }else{
                    System.out.println("Entered OTP is Incorrect");
                }
            }else{
                System.out.println("User not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
