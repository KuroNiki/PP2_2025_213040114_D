package id.unpas.sportify.view;

import id.unpas.sportify.controller.LapanganController;
import id.unpas.sportify.model.Lapangan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Panel untuk mengelola data Lapangan
 * Menggunakan komponen Swing JFC
 */
public class LapanganView extends JPanel {

    private LapanganController controller;

    // Komponen form
    private JTextField txtNamaLapangan;
    private JComboBox<String> cmbJenisLapangan;
    private JTextField txtHargaPerJam;
    private JComboBox<String> cmbStatusLapangan;
    private JTextArea txtDeskripsi;
    private JTextField txtSearch;

    // Tabel
    private JTable tableLapangan;
    private DefaultTableModel tableModel;

    // Tombol
    private JButton btnTambah, btnUbah, btnHapus, btnBersihkan, btnCari;

    // ID yang sedang dipilih
    private int selectedId = -1;

    // Format mata uang
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public LapanganView() {
        controller = new LapanganController();
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
        panel.setBorder(BorderFactory.createTitledBorder("Form Lapangan"));

        // Form fields
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Nama Lapangan
        gbc.gridx = 0; gbc.gridy = 0;
        fieldsPanel.add(new JLabel("Nama Lapangan:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtNamaLapangan = new JTextField(20);
        fieldsPanel.add(txtNamaLapangan, gbc);

        // Jenis Lapangan
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Jenis Lapangan:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        cmbJenisLapangan = new JComboBox<>(new String[]{
                "Futsal", "Basket", "Badminton", "Voli", "Tenis", "Sepak Bola", "Padel"
        });
        fieldsPanel.add(cmbJenisLapangan, gbc);

        // Harga Per Jam
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Harga Per Jam:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        txtHargaPerJam = new JTextField(20);
        fieldsPanel.add(txtHargaPerJam, gbc);

        // Status Lapangan
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        cmbStatusLapangan = new JComboBox<>(new String[]{
                "Tersedia", "Tidak Tersedia", "Maintenance"
        });
        fieldsPanel.add(cmbStatusLapangan, gbc);

        // Deskripsi
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Deskripsi:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtDeskripsi = new JTextArea(3, 20);
        txtDeskripsi.setLineWrap(true);
        txtDeskripsi.setWrapStyleWord(true);
        JScrollPane scrollDeskripsi = new JScrollPane(txtDeskripsi);
        fieldsPanel.add(scrollDeskripsi, gbc);

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
        panel.setBorder(BorderFactory.createTitledBorder("Data Lapangan"));

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
        String[] columns = {"ID", "Nama Lapangan", "Jenis", "Harga/Jam", "Status", "Deskripsi"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableLapangan = new JTable(tableModel);
        tableLapangan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableLapangan.setRowHeight(25);
        tableLapangan.getTableHeader().setReorderingAllowed(false);

        // Set column widths
        tableLapangan.getColumnModel().getColumn(0).setPreferredWidth(40);
        tableLapangan.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableLapangan.getColumnModel().getColumn(2).setPreferredWidth(80);
        tableLapangan.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableLapangan.getColumnModel().getColumn(4).setPreferredWidth(100);
        tableLapangan.getColumnModel().getColumn(5).setPreferredWidth(200);

        // Selection listener
        tableLapangan.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tableLapangan.getSelectedRow();
                if (row >= 0) {
                    pilihData(row);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableLapangan);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Lapangan> lapanganList = controller.getAllLapangan();

        for (Lapangan lap : lapanganList) {
            Object[] row = {
                    lap.getIdLapangan(),
                    lap.getNamaLapangan(),
                    lap.getJenisLapangan(),
                    currencyFormat.format(lap.getHargaPerJam()),
                    lap.getStatusLapangan(),
                    lap.getDeskripsi()
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
        List<Lapangan> lapanganList = controller.searchLapangan(keyword);

        for (Lapangan lap : lapanganList) {
            Object[] row = {
                    lap.getIdLapangan(),
                    lap.getNamaLapangan(),
                    lap.getJenisLapangan(),
                    currencyFormat.format(lap.getHargaPerJam()),
                    lap.getStatusLapangan(),
                    lap.getDeskripsi()
            };
            tableModel.addRow(row);
        }
    }

    private void pilihData(int row) {
        selectedId = (int) tableModel.getValueAt(row, 0);
        txtNamaLapangan.setText((String) tableModel.getValueAt(row, 1));
        cmbJenisLapangan.setSelectedItem(tableModel.getValueAt(row, 2));

        // Parse harga dari format currency
        String hargaStr = tableModel.getValueAt(row, 3).toString();
        hargaStr = hargaStr.replaceAll("[^\\d]", "");
        txtHargaPerJam.setText(hargaStr);

        cmbStatusLapangan.setSelectedItem(tableModel.getValueAt(row, 4));
        txtDeskripsi.setText((String) tableModel.getValueAt(row, 5));

        btnUbah.setEnabled(true);
        btnHapus.setEnabled(true);
        btnTambah.setEnabled(false);
    }

    private void tambahData() {
        String validationError = controller.validateInput(
                txtNamaLapangan.getText(),
                (String) cmbJenisLapangan.getSelectedItem(),
                txtHargaPerJam.getText(),
                (String) cmbStatusLapangan.getSelectedItem()
        );

        if (validationError != null) {
            JOptionPane.showMessageDialog(this, validationError, "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = controller.addLapangan(
                txtNamaLapangan.getText(),
                (String) cmbJenisLapangan.getSelectedItem(),
                txtHargaPerJam.getText(),
                (String) cmbStatusLapangan.getSelectedItem(),
                txtDeskripsi.getText()
        );

        if (success) {
            JOptionPane.showMessageDialog(this, "Data lapangan berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            bersihkanForm();
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan data lapangan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ubahData() {
        if (selectedId < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diubah!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String validationError = controller.validateInput(
                txtNamaLapangan.getText(),
                (String) cmbJenisLapangan.getSelectedItem(),
                txtHargaPerJam.getText(),
                (String) cmbStatusLapangan.getSelectedItem()
        );

        if (validationError != null) {
            JOptionPane.showMessageDialog(this, validationError, "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin mengubah data ini?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = controller.updateLapangan(
                    selectedId,
                    txtNamaLapangan.getText(),
                    (String) cmbJenisLapangan.getSelectedItem(),
                    txtHargaPerJam.getText(),
                    (String) cmbStatusLapangan.getSelectedItem(),
                    txtDeskripsi.getText()
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "Data lapangan berhasil diubah!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                bersihkanForm();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengubah data lapangan!", "Error", JOptionPane.ERROR_MESSAGE);
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
            boolean success = controller.deleteLapangan(selectedId);

            if (success) {
                JOptionPane.showMessageDialog(this, "Data lapangan berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                bersihkanForm();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data lapangan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void bersihkanForm() {
        selectedId = -1;
        txtNamaLapangan.setText("");
        cmbJenisLapangan.setSelectedIndex(0);
        txtHargaPerJam.setText("");
        cmbStatusLapangan.setSelectedIndex(0);
        txtDeskripsi.setText("");
        txtSearch.setText("");

        tableLapangan.clearSelection();

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
