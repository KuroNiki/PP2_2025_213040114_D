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
public class Latihan1 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Latihan 1 - Kalkulator (Layout)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(320, 420);
            frame.setLayout(new BorderLayout(8, 8));

            // Layar kalkulator
            JTextField display = new JTextField();
            display.setEditable(false);
            display.setHorizontalAlignment(JTextField.RIGHT);
            display.setFont(display.getFont().deriveFont(Font.BOLD, 22f));
            frame.add(display, BorderLayout.NORTH);

            // Panel tombol 4x4 (16 tombol)
            String[] keys = {
                    "7", "8", "9", "/",
                    "4", "5", "6", "*",
                    "1", "2", "3", "-",
                    "0", "C", "=", "+"
            };
            JPanel keypad = new JPanel(new GridLayout(4, 4, 6, 6));
            for (String k : keys) {
                keypad.add(new JButton(k)); // hanya layout, tanpa aksi
            }
            frame.add(keypad, BorderLayout.CENTER);

            // Opsional: status bar kosong di SOUTH
            frame.add(new JLabel("  "), BorderLayout.SOUTH);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
