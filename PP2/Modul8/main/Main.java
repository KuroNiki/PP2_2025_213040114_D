/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modul8.main;


import Modul8.controller.PersegiPanjangController;
import Modul8.model.PersegiPanjangModel;
import Modul8.view.PersegiPanjangView;

/**
 *
 * @author Pongo
 */
public class Main {
    public static void main(String[] args) {
        // Instansiasi Model
        PersegiPanjangModel model = new PersegiPanjangModel();

        // Instansiasi View
        PersegiPanjangView view = new PersegiPanjangView();

        // Instansiasi Controller (menghubungkan Model & View)
        PersegiPanjangController controller = new PersegiPanjangController(model, view);

        // Tampilkan View
        view.setVisible(true);
    }
    
}
