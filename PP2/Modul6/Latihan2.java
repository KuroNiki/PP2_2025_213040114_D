/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modul6;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Pongo
 */
public class Latihan2 {
     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Latihan 2 - Konverter Suhu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(360, 160);

            // Gunakan GridLayout 3x2 agar rapi
            JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));

            JLabel lblC = new JLabel("Celcius:");
            JTextField tfC = new JTextField();
            JButton btnKonversi = new JButton("Konversi");
            JLabel lblF = new JLabel("Fahrenheit:");
            JLabel lblHasil = new JLabel(""); // hasil tampil di sini

            panel.add(lblC);
            panel.add(tfC);
            panel.add(new JLabel());   // spacer agar tombol di kolom kanan
            panel.add(btnKonversi);
            panel.add(lblF);
            panel.add(lblHasil);

            // Aksi tombol
            btnKonversi.addActionListener(e -> {
                String teks = tfC.getText().trim();
                try {
                    double c = Double.parseDouble(teks);
                    double f = (c * 9.0 / 5.0) + 32.0;
                    lblHasil.setText(String.format("%.2f °F", f));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Input bukan angka yang valid.",
                            "Kesalahan Input",
                            JOptionPane.WARNING_MESSAGE
                    );
                    lblHasil.setText("");
                    tfC.requestFocus();
                    tfC.selectAll();
                }
            });

            frame.setContentPane(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
