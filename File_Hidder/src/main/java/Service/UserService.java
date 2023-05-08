package Service;

import Dao.UserDAO;
import Model.User;

import java.sql.SQLException;

public class UserService {

    public static Integer insertUser(User u){
        try{
            if(UserDAO.isUserExist(u.getEmail())){
                return 1;
            }else{
                return UserDAO.insertUser(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
