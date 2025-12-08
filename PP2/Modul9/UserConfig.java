/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modul9;

import java.io.Serializable;

/**
 *
 * @author Pongo
 */


public class UserConfig implements Serializable {

    private String username;
    private int fontSize;

    public UserConfig() {
    }

    public UserConfig(String username, int fontSize) {
        this.username = username;
        this.fontSize = fontSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}