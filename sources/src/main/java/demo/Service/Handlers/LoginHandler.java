package demo.Service.Handlers;

import demo.Model.User;
import demo.Repository.UserRepository;

public class LoginHandler {
    private static UserRepository repository = new UserRepository();

    public static Boolean findUser(String username,String password){
        String[] params = new String[2];
        params[0] = username;
        params[1] = password;
        User user = repository.executeQueryAndGetValue("User.findByPassAndUser",params);
        if(user == null)
            return false;
        else return true;
    }

}
