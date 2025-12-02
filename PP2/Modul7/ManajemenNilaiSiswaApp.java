/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modul7;

/**
 *
 * @author Pongo
 */
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ManajemenNilaiSiswaApp extends JFrame {

    // Komponen input
    private JTextField txtNama;
    private JTextField txtNilai;
    private JComboBox<String> cmbMakul;

    // Komponen output (tabel)
    private JTable tableData;
    private DefaultTableModel tableModel;

    // TabbedPane
    private JTabbedPane tabbedPane;

    public ManajemenNilaiSiswaApp() {
        // 1. Konfigurasi Frame Utama
        setTitle("Aplikasi Manajemen Nilai Siswa");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // posisi di tengah layar
        setLayout(new BorderLayout());

        // 2. Inisialisasi TabbedPane
        tabbedPane = new JTabbedPane();

        // 3. Buat panel-panel untuk setiap tab
        JPanel panelInput = createInputPanel();
        JPanel panelTabel = createTablePanel();

        // 4. Tambahkan ke TabbedPane
        tabbedPane.addTab("Input Data", panelInput);
        tabbedPane.addTab("Daftar Nilai", panelTabel);

        // 5. Tambahkan TabbedPane ke Frame
        add(tabbedPane, BorderLayout.CENTER);
    }

    // Method untuk membuat desain Tab Input
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Label & TextField Nama
        panel.add(new JLabel("Nama Siswa:"));
        txtNama = new JTextField();
        panel.add(txtNama);

        // Label & ComboBox Mata Kuliah
        panel.add(new JLabel("Mata Pelajaran:"));
        cmbMakul = new JComboBox<>(new String[]{
                "Matematika Dasar",
                "Bahasa Indonesia",
                "Algoritma dan Pemrograman I",
                "Praktikum Pemrograman II"
        });
        panel.add(cmbMakul);

        // Label & TextField Nilai
        panel.add(new JLabel("Nilai (0-100):"));
        txtNilai = new JTextField();
        panel.add(txtNilai);

        // Tombol Simpan Data
        JButton btnSimpan = new JButton("Simpan Data");
        // Kolom kosong di kiri supaya tombol ada di kanan
        panel.add(new JLabel()); 
        panel.add(btnSimpan);

        // Event tombol Simpan
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prosesSimpan();
            }
        });

        return panel;
    }

    // Method untuk membuat desain Tab Daftar Nilai (tabel)
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Model tabel
        tableModel = new DefaultTableModel(
                new Object[]{"Nama Siswa", "Mata Pelajaran", "Nilai", "Grade"}, 0
        );
        tableData = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tableData);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Logika validasi dan penyimpanan data
    private void prosesSimpan() {
        // 1. Ambil data input
        String nama = txtNama.getText();
        String makul = (String) cmbMakul.getSelectedItem();
        String strNilai = txtNilai.getText();

        // 2. Validasi input

        // Validasi 1: nama tidak boleh kosong
        if (nama.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nama tidak boleh kosong!",
                    "Error Validasi",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int nilai;

        // Validasi 2: nilai harus angka dan dalam range 0-100
        try {
            nilai = Integer.parseInt(strNilai);

            if (nilai < 0 || nilai > 100) {
                JOptionPane.showMessageDialog(
                        this,
                        "Nilai harus antara 0 - 100!",
                        "Error Validasi",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nilai harus berupa angka!",
                    "Error Validasi",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // 3. Logika menentukan grade (if-else sesuai latihan 1)
        String grade;
        if (nilai >= 80) {
            grade = "A";
        } else if (nilai >= 70) {
            grade = "B";
        } else if (nilai >= 60) {
            grade = "C";
        } else if (nilai >= 50) {
            grade = "D";
        } else {
            grade = "E";
        }

        // 4. Masukkan ke tabel
        Object[] dataBaris = {nama, makul, nilai, grade};
        tableModel.addRow(dataBaris);

        // 5. Reset form & pindah tab
        txtNama.setText("");
        txtNilai.setText("");
        cmbMakul.setSelectedIndex(0);

        JOptionPane.showMessageDialog(
                this,
                "Data berhasil disimpan!"
        );

        // Pindah ke tab daftar nilai
        tabbedPane.setSelectedIndex(1);
    }

    // Method main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ManajemenNilaiSiswaApp().setVisible(true);
        });
    }
}
