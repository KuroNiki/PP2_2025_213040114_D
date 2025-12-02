/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modul8.model;

/**
 *
 * @author Pongo
 */
public class PersegiPanjangModel {
        private double panjang;
    private double lebar;
    private double luas;

    // Menghitung luas (Logika Bisnis)
    public void hitungLuas() {
        this.luas = this.panjang * this.lebar;
    }

    // Setter panjang
    public void setPanjang(double panjang) {
        this.panjang = panjang;
    }

    // Setter lebar
    public void setLebar(double lebar) {
        this.lebar = lebar;
    }

    // Getter hasil luas
    public double getLuas() {
        return luas;
    }
    
}
