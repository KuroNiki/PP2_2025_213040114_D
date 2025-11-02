package com.mycompany.modul5;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Modul5 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Jendela Pertamaku");
                frame.setSize(400, 300);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.setLayout(new BorderLayout());

                JLabel label = new JLabel("Label Ada Di Atas (NORTH)", JLabel.CENTER);
                JButton buttonSouth = new JButton("Tombol Ada Dibawah (SOUTH)");
                JButton buttonWest = new JButton("WEST");
                JButton buttonEast = new JButton("EAST");
                JButton buttonCenter = new JButton("CENTER");

                // Aksi tombol SOUTH
                buttonSouth.addActionListener(e -> {
                    label.setText("Tombol di SOUTH berhasil diklik!");
                });

                // Aksi tombol WEST
                buttonWest.addActionListener(e -> {
                    label.setText("Tombol di WEST berhasil diklik!");
                });

                // Aksi tombol EAST
                buttonEast.addActionListener(e -> {
                    label.setText("Tombol di EAST berhasil diklik!");
                });

                // Aksi tombol CENTER
                buttonCenter.addActionListener(e -> {
                    label.setText("Tombol di CENTER berhasil diklik!");
                });

                frame.add(label, BorderLayout.NORTH);
                frame.add(buttonSouth, BorderLayout.SOUTH);
                frame.add(buttonWest, BorderLayout.WEST);
                frame.add(buttonEast, BorderLayout.EAST);
                frame.add(buttonCenter, BorderLayout.CENTER);

                frame.setVisible(true);
            }
        });
    }
}
