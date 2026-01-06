package id.unpas.sportify;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import id.unpas.sportify.config.DatabaseConfig;
import id.unpas.sportify.view.MainFrame;

/**
 * Main class - Entry point aplikasi Booking Lapangan
 */
public class Main {

    public static void main(String[] args) {
        // Set Look and Feel
        try {
            // Coba gunakan system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Jika gagal, gunakan default
            System.out.println("Menggunakan default Look and Feel");
        }

        // Test koneksi database
        System.out.println("Mencoba koneksi ke database...");
        if (!DatabaseConfig.testConnection()) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null,
                        "Gagal terhubung ke database MySQL!\n\n" +
                                "Pastikan:\n" +
                                "1. MySQL Server sudah berjalan\n" +
                                "2. Database 'booking_lapangan' sudah dibuat\n" +
                                "3. Username dan password sudah benar\n\n" +
                                "Lihat file DatabaseConfig.java untuk konfigurasi.",
                        "Error Koneksi Database",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            });
            return;
        }

        System.out.println("Koneksi database berhasil!");

        // Jalankan GUI di Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                System.out.println("Aplikasi Booking Lapangan berhasil dijalankan!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Gagal menjalankan aplikasi: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
