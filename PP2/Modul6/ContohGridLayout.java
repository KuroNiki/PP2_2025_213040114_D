/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modul6;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Pongo
 */
public class ContohGridLayout {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Contoh GridLayout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        
        
        frame.setLayout(new GridLayout(3, 2, 5, 5));
        
        frame.add(new JLabel("Label 1:"));
        frame.add(new JTextField());
        frame.add(new JLabel("Label 2:"));
        frame.add(new JPasswordField());
        frame.add(new JButton("Login"));
        frame.add(new JButton("Batal"));
        
        frame.setVisible(true);
    }
    
}
