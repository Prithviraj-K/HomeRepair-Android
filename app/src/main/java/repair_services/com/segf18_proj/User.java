package repair_services.com.segf18_proj;

import java.io.Serializable;

public class User{
    public String username, password, userrole;

    public User(String username, String password, String userrole){
        this.username = username;
        this.password = password;
        this.userrole = userrole;
    }
    public User (){}

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getUserrole(){
        return userrole;
    }
}
