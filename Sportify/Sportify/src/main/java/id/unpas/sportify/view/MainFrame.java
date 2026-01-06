package id.unpas.sportify.view;

import id.unpas.sportify.config.DatabaseConfig;
import id.unpas.sportify.util.PDFExporter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main Frame aplikasi Booking Lapangan
 * Menggunakan JTabbedPane untuk navigasi antar panel
 */
public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane;
    private LapanganView lapanganView;
    private PelangganView pelangganView;
    private BookingView bookingView;

    public MainFrame() {
        initComponents();
        createMenuBar();
        setupFrame();
    }

    private void initComponents() {
        // Panel-panel utama
        lapanganView = new LapanganView();
        pelangganView = new PelangganView();
        bookingView = new BookingView();

        // Tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));

        // Tab dengan ikon (menggunakan unicode untuk sederhana)
        tabbedPane.addTab("Booking", bookingView);
        tabbedPane.addTab("Lapangan", lapanganView);
        tabbedPane.addTab("Pelanggan", pelangganView);

        // Listener untuk refresh data saat pindah tab
        tabbedPane.addChangeListener(e -> {
            int index = tabbedPane.getSelectedIndex();
            switch (index) {
                case 0:
                    bookingView.refreshData();
                    break;
                case 1:
                    lapanganView.refreshData();
                    break;
                case 2:
                    pelangganView.refreshData();
                    break;
            }
        });

        add(tabbedPane, BorderLayout.CENTER);

        // Status bar
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        JLabel statusLabel = new JLabel("Sportify - Booking Lapangan");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        statusBar.add(statusLabel);
        add(statusBar, BorderLayout.SOUTH);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menu File
        JMenu menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);

        JMenuItem menuRefresh = new JMenuItem("Refresh Data");
        menuRefresh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        menuRefresh.addActionListener(e -> refreshAllData());
        menuFile.add(menuRefresh);

        menuFile.addSeparator();

        JMenuItem menuExit = new JMenuItem("Keluar");
        menuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        menuExit.addActionListener(e -> exitApplication());
        menuFile.add(menuExit);

        menuBar.add(menuFile);

        // Menu Data
        JMenu menuData = new JMenu("Data");
        menuData.setMnemonic(KeyEvent.VK_D);

        JMenuItem menuBooking = new JMenuItem("Kelola Booking");
        menuBooking.addActionListener(e -> tabbedPane.setSelectedIndex(0));
        menuData.add(menuBooking);

        JMenuItem menuLapangan = new JMenuItem("Kelola Lapangan");
        menuLapangan.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        menuData.add(menuLapangan);

        JMenuItem menuPelanggan = new JMenuItem("Kelola Pelanggan");
        menuPelanggan.addActionListener(e -> tabbedPane.setSelectedIndex(2));
        menuData.add(menuPelanggan);

        menuBar.add(menuData);

        // Menu Laporan (Export PDF)
        JMenu menuLaporan = new JMenu("Laporan");
        menuLaporan.setMnemonic(KeyEvent.VK_L);

        JMenuItem menuExportBooking = new JMenuItem("Export Booking ke PDF");
        menuExportBooking.addActionListener(e -> PDFExporter.exportBooking(this));
        menuLaporan.add(menuExportBooking);

        JMenuItem menuExportLapangan = new JMenuItem("Export Lapangan ke PDF");
        menuExportLapangan.addActionListener(e -> PDFExporter.exportLapangan(this));
        menuLaporan.add(menuExportLapangan);

        JMenuItem menuExportPelanggan = new JMenuItem("Export Pelanggan ke PDF");
        menuExportPelanggan.addActionListener(e -> PDFExporter.exportPelanggan(this));
        menuLaporan.add(menuExportPelanggan);

        menuBar.add(menuLaporan);

        // Menu Bantuan
//        JMenu menuBantuan = new JMenu("Bantuan");
//        menuBantuan.setMnemonic(KeyEvent.VK_B);
//
//        JMenuItem menuTentang = new JMenuItem("Tentang Aplikasi");
//        menuTentang.addActionListener(e -> showAboutDialog());
//        menuBantuan.add(menuTentang);
//
//        menuBar.add(menuBantuan);
//
        setJMenuBar(menuBar);
    }

    private void setupFrame() {
        setTitle("Sportify Aplikasi Booking Lapangan");
        setSize(1200, 700);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        // Window listener untuk konfirmasi keluar
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });

        // Coba set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            // Gunakan default look and feel
        }
    }

    private void refreshAllData() {
        lapanganView.refreshData();
        pelangganView.refreshData();
        bookingView.refreshData();
        JOptionPane.showMessageDialog(this, "Data berhasil di-refresh!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin keluar dari aplikasi?",
                "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            DatabaseConfig.closeConnection();
            System.exit(0);
        }
    }

//    private void showAboutDialog() {
//        String message = "╔════════════════════════════════════════════════╗\n" +
//                "║     APLIKASI BOOKING LAPANGAN                  ║\n" +
//                "║                                                ║\n" +
//                "║     Versi: 1.0                                 ║\n" +
//                "║     Teknologi: Java Swing (JFC)                ║\n" +
//                "║     Database: MySQL                            ║\n" +
//                "║     Arsitektur: MVC Pattern                    ║\n" +
//                "║                                                ║\n" +
//                "║     Fitur:                                     ║\n" +
//                "║     • CRUD Lapangan                            ║\n" +
//                "║     • CRUD Pelanggan                           ║\n" +
//                "║     • CRUD Booking                             ║\n" +
//                "║     • Export ke PDF                            ║\n" +
//                "║     • Validasi Input                           ║\n" +
//                "║                                                ║\n" +
//                "║     © 2025 Booking Lapangan                    ║\n" +
//                "╚════════════════════════════════════════════════╝";
//
//        JOptionPane.showMessageDialog(this, message, "Tentang Aplikasi", JOptionPane.INFORMATION_MESSAGE);
//    }
}

