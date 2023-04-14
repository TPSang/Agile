/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package model;

/**
 *
 * @author trand
 */
public class user {
private String uername;
private String password;;
private boolean role;

    public user() {
    }

    public user(String uername, String password, boolean role) {
        this.uername = uername;
        this.password = password;
        this.role = role;
    }

    public String getUername() {
        return uername;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRole() {
        return role;
    }

    public void setUername(String uername) {
        this.uername = uername;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

  

  
}
