package id.unpas.sportify.view;

import id.unpas.sportify.controller.PelangganController;
import id.unpas.sportify.model.Pelanggan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel untuk mengelola data Pelanggan
 * Menggunakan komponen Swing JFC
 */
public class PelangganView extends JPanel {

    private PelangganController controller;

    // Komponen form
    private JTextField txtNamaPelanggan;
    private JTextField txtNoTelepon;
    private JTextField txtEmail;
    private JTextArea txtAlamat;
    private JTextField txtSearch;

    // Tabel
    private JTable tablePelanggan;
    private DefaultTableModel tableModel;

    // Tombol
    private JButton btnTambah, btnUbah, btnHapus, btnBersihkan, btnCari;

    // ID yang sedang dipilih
    private int selectedId = -1;

    public PelangganView() {
        controller = new PelangganController();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel Form (Kiri)
        JPanel formPanel = createFormPanel();

        // Panel Tabel (Kanan)
        JPanel tablePanel = createTablePanel();

        // Split pane untuk form dan tabel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tablePanel);
        splitPane.setDividerLocation(350);
        splitPane.setResizeWeight(0.3);

        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Form Pelanggan"));

        // Form fields
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Nama Pelanggan
        gbc.gridx = 0; gbc.gridy = 0;
        fieldsPanel.add(new JLabel("Nama Pelanggan:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtNamaPelanggan = new JTextField(20);
        fieldsPanel.add(txtNamaPelanggan, gbc);

        // Nomor Telepon
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("No. Telepon:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtNoTelepon = new JTextField(20);
        fieldsPanel.add(txtNoTelepon, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        txtEmail = new JTextField(20);
        fieldsPanel.add(txtEmail, gbc);

        // Alamat
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtAlamat = new JTextArea(4, 20);
        txtAlamat.setLineWrap(true);
        txtAlamat.setWrapStyleWord(true);
        JScrollPane scrollAlamat = new JScrollPane(txtAlamat);
        fieldsPanel.add(scrollAlamat, gbc);

        panel.add(fieldsPanel, BorderLayout.CENTER);

        // Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

        btnTambah = new JButton("Tambah");
        btnTambah.setForeground(Color.BLACK);
        btnTambah.addActionListener(e -> tambahData());

        btnUbah = new JButton("Ubah");
        btnUbah.setForeground(Color.BLACK);
        btnUbah.setEnabled(false);
        btnUbah.addActionListener(e -> ubahData());

        btnHapus = new JButton("Hapus");
        btnHapus.setForeground(Color.BLACK);
        btnHapus.setEnabled(false);
        btnHapus.addActionListener(e -> hapusData());

        btnBersihkan = new JButton("Bersihkan");
        btnBersihkan.addActionListener(e -> bersihkanForm());

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUbah);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnBersihkan);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Data Pelanggan"));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Cari:"));
        txtSearch = new JTextField(20);
        searchPanel.add(txtSearch);
        btnCari = new JButton("Cari");
        btnCari.addActionListener(e -> cariData());
        searchPanel.add(btnCari);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadData());
        searchPanel.add(btnRefresh);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Tabel
        String[] columns = {"ID", "Nama Pelanggan", "No. Telepon", "Email", "Alamat"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablePelanggan = new JTable(tableModel);
        tablePelanggan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePelanggan.setRowHeight(25);
        tablePelanggan.getTableHeader().setReorderingAllowed(false);

        // Set column widths
        tablePelanggan.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablePelanggan.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablePelanggan.getColumnModel().getColumn(2).setPreferredWidth(120);
        tablePelanggan.getColumnModel().getColumn(3).setPreferredWidth(150);
        tablePelanggan.getColumnModel().getColumn(4).setPreferredWidth(200);

        // Selection listener
        tablePelanggan.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tablePelanggan.getSelectedRow();
                if (row >= 0) {
                    pilihData(row);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablePelanggan);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Pelanggan> pelangganList = controller.getAllPelanggan();

        for (Pelanggan pel : pelangganList) {
            Object[] row = {
                    pel.getIdPelanggan(),
                    pel.getNamaPelanggan(),
                    pel.getNoTelepon(),
                    pel.getEmail(),
                    pel.getAlamat()
            };
            tableModel.addRow(row);
        }
    }

    private void cariData() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            loadData();
            return;
        }

        tableModel.setRowCount(0);
        List<Pelanggan> pelangganList = controller.searchPelanggan(keyword);

        for (Pelanggan pel : pelangganList) {
            Object[] row = {
                    pel.getIdPelanggan(),
                    pel.getNamaPelanggan(),
                    pel.getNoTelepon(),
                    pel.getEmail(),
                    pel.getAlamat()
            };
            tableModel.addRow(row);
        }
    }

    private void pilihData(int row) {
        selectedId = (int) tableModel.getValueAt(row, 0);
        txtNamaPelanggan.setText((String) tableModel.getValueAt(row, 1));
        txtNoTelepon.setText((String) tableModel.getValueAt(row, 2));
        txtEmail.setText(tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "");
        txtAlamat.setText(tableModel.getValueAt(row, 4) != null ? tableModel.getValueAt(row, 4).toString() : "");

        btnUbah.setEnabled(true);
        btnHapus.setEnabled(true);
        btnTambah.setEnabled(false);
    }

    private void tambahData() {
        String validationError = controller.validateInput(
                txtNamaPelanggan.getText(),
                txtNoTelepon.getText(),
                txtEmail.getText()
        );

        if (validationError != null) {
            JOptionPane.showMessageDialog(this, validationError, "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = controller.addPelanggan(
                txtNamaPelanggan.getText(),
                txtNoTelepon.getText(),
                txtEmail.getText(),
                txtAlamat.getText()
        );

        if (success) {
            JOptionPane.showMessageDialog(this, "Data pelanggan berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            bersihkanForm();
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan data pelanggan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ubahData() {
        if (selectedId < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diubah!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String validationError = controller.validateInput(
                txtNamaPelanggan.getText(),
                txtNoTelepon.getText(),
                txtEmail.getText()
        );

        if (validationError != null) {
            JOptionPane.showMessageDialog(this, validationError, "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin mengubah data ini?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = controller.updatePelanggan(
                    selectedId,
                    txtNamaPelanggan.getText(),
                    txtNoTelepon.getText(),
                    txtEmail.getText(),
                    txtAlamat.getText()
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "Data pelanggan berhasil diubah!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                bersihkanForm();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengubah data pelanggan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void hapusData() {
        if (selectedId < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus data ini?\nData booking terkait juga akan terhapus!",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = controller.deletePelanggan(selectedId);

            if (success) {
                JOptionPane.showMessageDialog(this, "Data pelanggan berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                bersihkanForm();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data pelanggan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void bersihkanForm() {
        selectedId = -1;
        txtNamaPelanggan.setText("");
        txtNoTelepon.setText("");
        txtEmail.setText("");
        txtAlamat.setText("");
        txtSearch.setText("");

        tablePelanggan.clearSelection();

        btnTambah.setEnabled(true);
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
    }

    /**
     * Refresh data dari database
     */
    public void refreshData() {
        loadData();
    }
}
