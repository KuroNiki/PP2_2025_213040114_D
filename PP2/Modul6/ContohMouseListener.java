/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modul6;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Pongo
 */
public class ContohMouseListener {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Contoh MouseListener");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setPreferredSize(new Dimension(200, 200));
        
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(Color.CYAN);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.LIGHT_GRAY);
            }
            
//            @Override
//            public void mouseCliked(MouseEvent e) {
//                System.out.println("Mouse Diklik di: x=" + e.getX() + ", y=" + e.getY());
//            }
        };
        
        panel.addMouseListener(adapter);
        
        frame.add(panel);
        frame.setVisible(true);
    }
    
}
