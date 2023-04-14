/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package model;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author trand
 */
public class userDAO {
    List<user> ls = new ArrayList<>();

    public userDAO() {
        ls.add(new user("trandinhnha", "12345", true));
        ls.add(new user("lyhoangut", "572003", true));
        ls.add(new user("tranvanteo", "67890", true));
        ls.add(new user("dieunhatquang", "12345", true));
        ls.add(new user("tranvanhung", "35667", true));
        
        
    }
    public boolean checkLogin(String username,String pass)
    {
        for (user u : ls){
            if (u.getUername().equals(username)&& u.getPassword().equals(pass))
            {
                return true;
            }
        }
        return false;
    }
}
