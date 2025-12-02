/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modul7.Tugas;


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


/**
 *
 * @author Pongo
 */
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

    // Tab Input Data
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Label & TextField Nama
        panel.add(new JLabel("Nama Siswa:"));
        txtNama = new JTextField();
        panel.add(txtNama);

        // Label & ComboBox Mata Pelajaran
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

        // Tombol Reset & Simpan
        JButton btnReset = new JButton("Reset");
        JButton btnSimpan = new JButton("Simpan Data");

        panel.add(btnReset);
        panel.add(btnSimpan);

        // Event tombol Simpan
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prosesSimpan();
            }
        });

        // Event tombol Reset
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });

        return panel;
    }

    // Tab Daftar Nilai
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

        // ===== TUGAS 2: Tombol Hapus di bawah tabel =====
        JPanel panelBawah = new JPanel(); // FlowLayout default
        JButton btnHapus = new JButton("Hapus Data Terpilih");
        panelBawah.add(btnHapus);

        // Event tombol Hapus
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableData.getSelectedRow();

                if (selectedRow > -1) {
                    int konfirmasi = JOptionPane.showConfirmDialog(
                            ManajemenNilaiSiswaApp.this,
                            "Yakin ingin menghapus data baris ini?",
                            "Konfirmasi Hapus",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (konfirmasi == JOptionPane.YES_OPTION) {
                        tableModel.removeRow(selectedRow);
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            ManajemenNilaiSiswaApp.this,
                            "Pilih dulu baris yang akan dihapus!",
                            "Tidak ada baris dipilih",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        panel.add(panelBawah, BorderLayout.SOUTH);

        return panel;
    }

    // ===== TUGAS 4: Method Reset Form =====
    private void resetForm() {
        txtNama.setText("");
        txtNilai.setText("");
        cmbMakul.setSelectedIndex(0);
        txtNama.requestFocus();
    }

    // Logika validasi dan penyimpanan data
    private void prosesSimpan() {
        // 1. Ambil data input (nama langsung di-trim)
        String nama = txtNama.getText().trim();
        String makul = (String) cmbMakul.getSelectedItem();
        String strNilai = txtNilai.getText();

        // ===== TUGAS 3: Validasi minimal 3 karakter =====

        // Validasi 1: nama tidak boleh kosong
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nama tidak boleh kosong!",
                    "Error Validasi",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Validasi 2: minimal 3 karakter
        if (nama.length() < 3) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nama minimal terdiri dari 3 karakter!",
                    "Error Validasi",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int nilai;

        // Validasi nilai harus angka dan 0–100
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

        // ===== TUGAS 1: Tentukan grade pakai switch case =====
        String grade;
        int kategori = nilai / 10; // 80-89 -> 8, 90-100 -> 9/10, dst.

        switch (kategori) {
            case 10:
            case 9:
            case 8:
                grade = "A";
                break;
            case 7:
                grade = "B";
                break;
            case 6:
                grade = "C";
                break;
            case 5:
                grade = "D";
                break;
            default:
                grade = "E";
                break;
        }

        // 4. Masukkan ke tabel
        Object[] dataBaris = {nama, makul, nilai, grade};
        tableModel.addRow(dataBaris);

        // 5. Reset form & pindah tab
        resetForm();

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