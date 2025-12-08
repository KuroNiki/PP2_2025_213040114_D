/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modul9;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.*;
import javax.swing.*;

/**
 *
 * @author Pongo
 */

public class AplikasiFileIO extends JFrame {

    private JTextArea textArea;
    private JFileChooser fileChooser;

    private JTextField txtUsername;

    private JButton btnOpenText;
    private JButton btnSaveText;
    private JButton btnAppendText;
    private JButton btnSaveBinary;
    private JButton btnLoadBinary;
    private JButton btnSaveUserConfig;
    private JButton btnLoadUserConfig;

    public AplikasiFileIO() {
        super("Tutorial File IO & Exception Handling");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ---------- AREA TULIS ----------
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        fileChooser = new JFileChooser();

        // ---------- PANEL USERNAME (UTARA) ----------
        txtUsername = new JTextField(20);
        JPanel userPanel = new JPanel();
        userPanel.add(new JLabel("Username:"));
        userPanel.add(txtUsername);
        add(userPanel, BorderLayout.NORTH);

        // ---------- PANEL TOMBOL (SELATAN) ----------
        JPanel buttonPanel = new JPanel();
        btnOpenText = new JButton("Buka Text");
        btnSaveText = new JButton("Simpan Text");
        btnAppendText = new JButton("Append Text");
        btnSaveBinary = new JButton("Simpan Config (Binary)");
        btnLoadBinary = new JButton("Muat Config (Binary)");
        btnSaveUserConfig = new JButton("Simpan UserConfig");
        btnLoadUserConfig = new JButton("Muat UserConfig");

        buttonPanel.add(btnOpenText);
        buttonPanel.add(btnSaveText);
        buttonPanel.add(btnAppendText);
        buttonPanel.add(btnSaveBinary);
        buttonPanel.add(btnLoadBinary);
        buttonPanel.add(btnSaveUserConfig);
        buttonPanel.add(btnLoadUserConfig);

        // ---------- LAYOUT UTAMA ----------
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // ---------- EVENT HANDLER ----------
        btnOpenText.addActionListener(e -> bukaFileTeks());
        btnSaveText.addActionListener(e -> simpanFileTeks());
        btnAppendText.addActionListener(e -> appendFileTeks());
        btnSaveBinary.addActionListener(e -> simpanConfigBinary());
        btnLoadBinary.addActionListener(e -> muatConfigBinary());
        btnSaveUserConfig.addActionListener(e -> simpanUserConfig());
        btnLoadUserConfig.addActionListener(e -> muatUserConfig());

        // Latihan 2: auto load last_notes.txt jika ada
        bacaLastNotes();
    }

    // ======================================================
    // LATIHAN 2 : Membaca last_notes.txt saat aplikasi dibuka
    // ======================================================
    private void bacaLastNotes() {
        File file = new File("last_notes.txt");
        if (!file.exists()) {
            // kalau tidak ada, diam saja
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            textArea.setText("");
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
        } catch (IOException ex) {
            System.err.println("Gagal membaca last_notes.txt: " + ex.getMessage());
        }
    }

    // ======================================================
    // LATIHAN 1 : Membaca file teks (Try-Catch-Finally)
    // ======================================================
    private void bukaFileTeks() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new FileReader(file));

                textArea.setText("");
                String line;

                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }

                JOptionPane.showMessageDialog(this, "File berhasil dibuka!");

            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this,
                        "File tidak ditemukan: " + ex.getMessage());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Gagal membaca file: " + ex.getMessage());
            } finally {
                try {
                    if (reader != null) reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // ======================================================
    // LATIHAN 1 + 2 : Menyimpan file teks (dan last_notes.txt)
    // ======================================================
    private void simpanFileTeks() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(textArea.getText());
                JOptionPane.showMessageDialog(this, "File berhasil disimpan!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Gagal menyimpan file: " + ex.getMessage());
            }

            // Sekaligus simpan ke last_notes.txt (Latihan 2, opsional tapi berguna)
            try (BufferedWriter writerLast =
                         new BufferedWriter(new FileWriter("last_notes.txt"))) {
                writerLast.write(textArea.getText());
            } catch (IOException ex2) {
                System.err.println("Gagal menyimpan last_notes.txt: " + ex2.getMessage());
            }
        }
    }

    // ======================================================
    // LATIHAN 4 : Append text ke file (tidak overwrite)
    // ======================================================
    private void appendFileTeks() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            // true -> mode append
            try (BufferedWriter writer =
                         new BufferedWriter(new FileWriter(file, true))) {

                writer.write(textArea.getText());
                writer.newLine(); // kasih baris baru di akhir
                JOptionPane.showMessageDialog(this,
                        "Text berhasil di-append ke file!");

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Gagal append ke file: " + ex.getMessage());
            }
        }
    }

    // ======================================================
    // LATIHAN 1 : Simpan config ukuran font (binary)
    // ======================================================
    private void simpanConfigBinary() {
        try (DataOutputStream dos =
                     new DataOutputStream(new FileOutputStream("config.bin"))) {

            int fontSize = textArea.getFont().getSize();
            dos.writeInt(fontSize);

            JOptionPane.showMessageDialog(this,
                    "Ukuran font " + fontSize + " disimpan ke config.bin");

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal menyimpan binary: " + ex.getMessage());
        }
    }

    // ======================================================
    // LATIHAN 1 : Muat config ukuran font (binary)
    // ======================================================
    private void muatConfigBinary() {
        try (DataInputStream dis =
                     new DataInputStream(new FileInputStream("config.bin"))) {

            int fontSize = dis.readInt();
            textArea.setFont(new Font("Monospaced", Font.PLAIN, fontSize));

            JOptionPane.showMessageDialog(this,
                    "Font diubah menjadi ukuran: " + fontSize);

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this,
                    "File config.bin tidak ditemukan.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal membaca binary: " + ex.getMessage());
        }
    }

    // ======================================================
    // LATIHAN 3 : Simpan objek UserConfig (ObjectOutputStream)
    // ======================================================
    private void simpanUserConfig() {
        String username = txtUsername.getText();
        int fontSize = textArea.getFont().getSize();

        UserConfig config = new UserConfig(username, fontSize);

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream("userconfig.dat"))) {

            oos.writeObject(config);
            JOptionPane.showMessageDialog(this,
                    "UserConfig berhasil disimpan ke userconfig.dat");

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal menyimpan UserConfig: " + ex.getMessage());
        }
    }

    // ======================================================
    // LATIHAN 3 : Muat objek UserConfig (ObjectInputStream)
    // ======================================================
    private void muatUserConfig() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream("userconfig.dat"))) {

            Object obj = ois.readObject();

            if (obj instanceof UserConfig) {
                UserConfig config = (UserConfig) obj;

                txtUsername.setText(config.getUsername());
                textArea.setFont(new Font("Monospaced",
                        Font.PLAIN, config.getFontSize()));

                JOptionPane.showMessageDialog(this,
                        "UserConfig berhasil dimuat.");
            } else {
                JOptionPane.showMessageDialog(this,
                        "File userconfig.dat tidak berisi UserConfig!");
            }

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this,
                    "File userconfig.dat tidak ditemukan.");
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal membaca UserConfig: " + ex.getMessage());
        }
    }

    // ======================================================
    // MAIN
    // ======================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AplikasiFileIO().setVisible(true));
    }
}