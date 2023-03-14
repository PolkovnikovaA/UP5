package com.example.up;

import java.io.Serializable;

public class Users implements Serializable {

    private String login;
    private String password;

    public Users(String login,String password)
    {
        this.login=login;
        this.password=password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

