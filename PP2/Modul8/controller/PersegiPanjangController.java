/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modul8.controller;


import Modul8.model.PersegiPanjangModel;
import Modul8.view.PersegiPanjangView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Pongo
 */
public class PersegiPanjangController {
    
    private PersegiPanjangModel model;
    private PersegiPanjangView view;

    public PersegiPanjangController(PersegiPanjangModel model, PersegiPanjangView view) {
        this.model = model;
        this.view = view;

        // Menghubungkan tombol pada View dengan Controller
        this.view.addHitungListener(new HitungListener());
    }

    // Inner class untuk menangani event klik tombol
    class HitungListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Ambil data dari View
                double p = view.getPanjang();
                double l = view.getLebar();

                // Kirim ke Model
                model.setPanjang(p);
                model.setLebar(l);

                // Jalankan logika Model
                model.hitungLuas();

                // Tampilkan ke View
                double hasil = model.getLuas();
                view.setHasil(hasil);

            } catch (NumberFormatException ex) {
                view.tampilkanPesanError("Masukkan angka yang valid!");
            }
        }
    }
    
}
