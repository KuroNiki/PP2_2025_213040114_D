package id.unpas.sportify.view;

import id.unpas.sportify.controller.BookingController;
import id.unpas.sportify.controller.LapanganController;
import id.unpas.sportify.controller.PelangganController;
import id.unpas.sportify.model.Booking;
import id.unpas.sportify.model.Lapangan;
import id.unpas.sportify.model.Pelanggan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Panel untuk mengelola data Booking
 * Menggunakan komponen Swing JFC
 */
public class BookingView extends JPanel {

    private BookingController bookingController;
    private LapanganController lapanganController;
    private PelangganController pelangganController;

    // Komponen form
    private JComboBox<LapanganItem> cmbLapangan;
    private JComboBox<PelangganItem> cmbPelanggan;
    private JTextField txtTanggalBooking;
    private JComboBox<String> cmbJamMulai;
    private JComboBox<String> cmbJamSelesai;
    private JLabel lblTotalHarga;
    private JComboBox<String> cmbStatusBooking;
    private JTextArea txtKeterangan;
    private JTextField txtSearch;

    // Tabel
    private JTable tableBooking;
    private DefaultTableModel tableModel;

    // Tombol
    private JButton btnTambah, btnUbah, btnHapus, btnBersihkan, btnCari, btnHitungHarga;

    // ID yang sedang dipilih
    private int selectedId = -1;

    // Format
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public BookingView() {
        bookingController = new BookingController();
        lapanganController = new LapanganController();
        pelangganController = new PelangganController();
        initComponents();
        loadComboBoxData();
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
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.35);

        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Form Booking"));

        // Form fields
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Lapangan
        gbc.gridx = 0; gbc.gridy = 0;
        fieldsPanel.add(new JLabel("Lapangan:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        cmbLapangan = new JComboBox<>();
        fieldsPanel.add(cmbLapangan, gbc);

        // Pelanggan
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Pelanggan:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        cmbPelanggan = new JComboBox<>();
        fieldsPanel.add(cmbPelanggan, gbc);

        // Tanggal Booking
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Tanggal (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        txtTanggalBooking = new JTextField(20);
        txtTanggalBooking.setText(dateFormat.format(new java.util.Date())); // Default hari ini
        fieldsPanel.add(txtTanggalBooking, gbc);

        // Jam Mulai
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Jam Mulai:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        cmbJamMulai = new JComboBox<>(generateTimeOptions());
        cmbJamMulai.addActionListener(e -> hitungTotalHarga());
        fieldsPanel.add(cmbJamMulai, gbc);

        // Jam Selesai
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Jam Selesai:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0;
        cmbJamSelesai = new JComboBox<>(generateTimeOptions());
        cmbJamSelesai.setSelectedIndex(1); // Default 1 jam setelah mulai
        cmbJamSelesai.addActionListener(e -> hitungTotalHarga());
        fieldsPanel.add(cmbJamSelesai, gbc);

        // Total Harga
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Total Harga:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.weightx = 1.0;
        JPanel hargaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        lblTotalHarga = new JLabel("Rp 0");
        lblTotalHarga.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalHarga.setForeground(new Color(39, 174, 96));
        hargaPanel.add(lblTotalHarga);
        btnHitungHarga = new JButton("Hitung");
        btnHitungHarga.addActionListener(e -> hitungTotalHarga());
        hargaPanel.add(Box.createHorizontalStrut(10));
        hargaPanel.add(btnHitungHarga);
        fieldsPanel.add(hargaPanel, gbc);

        // Status Booking
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6; gbc.weightx = 1.0;
        cmbStatusBooking = new JComboBox<>(new String[]{
                "Pending", "Confirmed", "Cancelled", "Completed"
        });
        fieldsPanel.add(cmbStatusBooking, gbc);

        // Keterangan
        gbc.gridx = 0; gbc.gridy = 7; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Keterangan:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7; gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtKeterangan = new JTextArea(3, 20);
        txtKeterangan.setLineWrap(true);
        txtKeterangan.setWrapStyleWord(true);
        JScrollPane scrollKeterangan = new JScrollPane(txtKeterangan);
        fieldsPanel.add(scrollKeterangan, gbc);

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
        panel.setBorder(BorderFactory.createTitledBorder("Data Booking"));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Cari:"));
        txtSearch = new JTextField(15);
        searchPanel.add(txtSearch);
        btnCari = new JButton("Cari");
        btnCari.addActionListener(e -> cariData());
        searchPanel.add(btnCari);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> {
            loadComboBoxData();
            loadData();
        });
        searchPanel.add(btnRefresh);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Tabel
        String[] columns = {"ID", "Lapangan", "Pelanggan", "Tanggal", "Jam", "Total Harga", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableBooking = new JTable(tableModel);
        tableBooking.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableBooking.setRowHeight(25);
        tableBooking.getTableHeader().setReorderingAllowed(false);

        // Set column widths
        tableBooking.getColumnModel().getColumn(0).setPreferredWidth(40);
        tableBooking.getColumnModel().getColumn(1).setPreferredWidth(120);
        tableBooking.getColumnModel().getColumn(2).setPreferredWidth(120);
        tableBooking.getColumnModel().getColumn(3).setPreferredWidth(90);
        tableBooking.getColumnModel().getColumn(4).setPreferredWidth(100);
        tableBooking.getColumnModel().getColumn(5).setPreferredWidth(100);
        tableBooking.getColumnModel().getColumn(6).setPreferredWidth(80);

        // Selection listener
        tableBooking.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tableBooking.getSelectedRow();
                if (row >= 0) {
                    pilihData(row);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableBooking);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private String[] generateTimeOptions() {
        String[] times = new String[24];
        for (int i = 0; i < 24; i++) {
            times[i] = String.format("%02d:00", i);
        }
        return times;
    }

    private void loadComboBoxData() {
        // Load Lapangan
        cmbLapangan.removeAllItems();
        List<Lapangan> lapanganList = lapanganController.getAvailableLapangan();
        for (Lapangan lap : lapanganList) {
            cmbLapangan.addItem(new LapanganItem(lap.getIdLapangan(), lap.getNamaLapangan(), lap.getHargaPerJam()));
        }

        // Load Pelanggan
        cmbPelanggan.removeAllItems();
        List<Pelanggan> pelangganList = pelangganController.getAllPelanggan();
        for (Pelanggan pel : pelangganList) {
            cmbPelanggan.addItem(new PelangganItem(pel.getIdPelanggan(), pel.getNamaPelanggan()));
        }

        // Listener untuk auto hitung harga saat lapangan dipilih
        cmbLapangan.addActionListener(e -> hitungTotalHarga());
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Booking> bookingList = bookingController.getAllBooking();

        for (Booking book : bookingList) {
            Object[] row = {
                    book.getIdBooking(),
                    book.getNamaLapangan(),
                    book.getNamaPelanggan(),
                    book.getTanggalBooking().toString(),
                    book.getJamMulai().toString().substring(0, 5) + " - " + book.getJamSelesai().toString().substring(0, 5),
                    currencyFormat.format(book.getTotalHarga()),
                    book.getStatusBooking()
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
        List<Booking> bookingList = bookingController.searchBooking(keyword);

        for (Booking book : bookingList) {
            Object[] row = {
                    book.getIdBooking(),
                    book.getNamaLapangan(),
                    book.getNamaPelanggan(),
                    book.getTanggalBooking().toString(),
                    book.getJamMulai().toString().substring(0, 5) + " - " + book.getJamSelesai().toString().substring(0, 5),
                    currencyFormat.format(book.getTotalHarga()),
                    book.getStatusBooking()
            };
            tableModel.addRow(row);
        }
    }

    private void pilihData(int row) {
        selectedId = (int) tableModel.getValueAt(row, 0);

        // Get booking by ID to fill form
        Booking booking = bookingController.getBookingById(selectedId);
        if (booking != null) {
            // Set Lapangan
            for (int i = 0; i < cmbLapangan.getItemCount(); i++) {
                if (cmbLapangan.getItemAt(i).getId() == booking.getIdLapangan()) {
                    cmbLapangan.setSelectedIndex(i);
                    break;
                }
            }

            // Set Pelanggan
            for (int i = 0; i < cmbPelanggan.getItemCount(); i++) {
                if (cmbPelanggan.getItemAt(i).getId() == booking.getIdPelanggan()) {
                    cmbPelanggan.setSelectedIndex(i);
                    break;
                }
            }

            txtTanggalBooking.setText(booking.getTanggalBooking().toString());
            cmbJamMulai.setSelectedItem(booking.getJamMulai().toString().substring(0, 5));
            cmbJamSelesai.setSelectedItem(booking.getJamSelesai().toString().substring(0, 5));
            lblTotalHarga.setText(currencyFormat.format(booking.getTotalHarga()));
            cmbStatusBooking.setSelectedItem(booking.getStatusBooking());
            txtKeterangan.setText(booking.getKeterangan());
        }

        btnUbah.setEnabled(true);
        btnHapus.setEnabled(true);
        btnTambah.setEnabled(false);
    }

    private void hitungTotalHarga() {
        LapanganItem selectedLapangan = (LapanganItem) cmbLapangan.getSelectedItem();
        if (selectedLapangan == null) {
            lblTotalHarga.setText("Rp 0");
            return;
        }

        String jamMulai = (String) cmbJamMulai.getSelectedItem();
        String jamSelesai = (String) cmbJamSelesai.getSelectedItem();

        BigDecimal totalHarga = bookingController.calculateTotalPrice(
                selectedLapangan.getId(), jamMulai, jamSelesai
        );

        lblTotalHarga.setText(currencyFormat.format(totalHarga));
    }

    private void tambahData() {
        LapanganItem selectedLapangan = (LapanganItem) cmbLapangan.getSelectedItem();
        PelangganItem selectedPelanggan = (PelangganItem) cmbPelanggan.getSelectedItem();

        if (selectedLapangan == null) {
            JOptionPane.showMessageDialog(this, "Lapangan harus dipilih!", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedPelanggan == null) {
            JOptionPane.showMessageDialog(this, "Pelanggan harus dipilih!", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String validationError = bookingController.validateInput(
                selectedLapangan.getId(),
                selectedPelanggan.getId(),
                txtTanggalBooking.getText(),
                (String) cmbJamMulai.getSelectedItem(),
                (String) cmbJamSelesai.getSelectedItem(),
                0 // excludeBookingId = 0 untuk insert baru
        );

        if (validationError != null) {
            JOptionPane.showMessageDialog(this, validationError, "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = bookingController.addBooking(
                selectedLapangan.getId(),
                selectedPelanggan.getId(),
                txtTanggalBooking.getText(),
                (String) cmbJamMulai.getSelectedItem(),
                (String) cmbJamSelesai.getSelectedItem(),
                (String) cmbStatusBooking.getSelectedItem(),
                txtKeterangan.getText()
        );

        if (success) {
            JOptionPane.showMessageDialog(this, "Data booking berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            bersihkanForm();
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan data booking!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ubahData() {
        if (selectedId < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diubah!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LapanganItem selectedLapangan = (LapanganItem) cmbLapangan.getSelectedItem();
        PelangganItem selectedPelanggan = (PelangganItem) cmbPelanggan.getSelectedItem();

        if (selectedLapangan == null || selectedPelanggan == null) {
            JOptionPane.showMessageDialog(this, "Lapangan dan Pelanggan harus dipilih!", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String validationError = bookingController.validateInput(
                selectedLapangan.getId(),
                selectedPelanggan.getId(),
                txtTanggalBooking.getText(),
                (String) cmbJamMulai.getSelectedItem(),
                (String) cmbJamSelesai.getSelectedItem(),
                selectedId // excludeBookingId untuk update
        );

        if (validationError != null) {
            JOptionPane.showMessageDialog(this, validationError, "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin mengubah data ini?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = bookingController.updateBooking(
                    selectedId,
                    selectedLapangan.getId(),
                    selectedPelanggan.getId(),
                    txtTanggalBooking.getText(),
                    (String) cmbJamMulai.getSelectedItem(),
                    (String) cmbJamSelesai.getSelectedItem(),
                    (String) cmbStatusBooking.getSelectedItem(),
                    txtKeterangan.getText()
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "Data booking berhasil diubah!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                bersihkanForm();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengubah data booking!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void hapusData() {
        if (selectedId < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus data booking ini?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = bookingController.deleteBooking(selectedId);

            if (success) {
                JOptionPane.showMessageDialog(this, "Data booking berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                bersihkanForm();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data booking!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void bersihkanForm() {
        selectedId = -1;
        if (cmbLapangan.getItemCount() > 0) cmbLapangan.setSelectedIndex(0);
        if (cmbPelanggan.getItemCount() > 0) cmbPelanggan.setSelectedIndex(0);
        txtTanggalBooking.setText(dateFormat.format(new java.util.Date()));
        cmbJamMulai.setSelectedIndex(0);
        cmbJamSelesai.setSelectedIndex(1);
        cmbStatusBooking.setSelectedIndex(0);
        txtKeterangan.setText("");
        txtSearch.setText("");
        lblTotalHarga.setText("Rp 0");

        tableBooking.clearSelection();

        btnTambah.setEnabled(true);
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
    }

    /**
     * Refresh data dari database
     */
    public void refreshData() {
        loadComboBoxData();
        loadData();
    }

    // Inner class untuk item ComboBox Lapangan
    private static class LapanganItem {
        private int id;
        private String nama;
        private BigDecimal hargaPerJam;

        public LapanganItem(int id, String nama, BigDecimal hargaPerJam) {
            this.id = id;
            this.nama = nama;
            this.hargaPerJam = hargaPerJam;
        }

        public int getId() { return id; }
        public String getNama() { return nama; }
        public BigDecimal getHargaPerJam() { return hargaPerJam; }

        @Override
        public String toString() {
            return nama;
        }
    }

    // Inner class untuk item ComboBox Pelanggan
    private static class PelangganItem {
        private int id;
        private String nama;

        public PelangganItem(int id, String nama) {
            this.id = id;
            this.nama = nama;
        }

        public int getId() { return id; }
        public String getNama() { return nama; }

        @Override
        public String toString() {
            return nama;
        }
    }
}
