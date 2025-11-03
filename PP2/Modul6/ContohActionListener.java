/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modul6;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Pongo
 */
public class ContohActionListener {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Contoh ActionListener");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());
        
        
        JLabel label = new JLabel ("Halo, Klik Tombol Dibawah!");
        JButton button = new JButton("Klik Saya !");
        
        
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("Tombol Telah Diklik!");
            }
        };
        
        button.addActionListener(listener);
        
        frame.add(label);
        frame.add(button);
        frame.setVisible(true);
    }
    
}
