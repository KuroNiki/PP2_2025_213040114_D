package id.unpas.sportify.controller;

import id.unpas.sportify.dao.LapanganDAO;
import id.unpas.sportify.model.Lapangan;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controller untuk mengelola operasi Lapangan
 * Menangani validasi dan logika bisnis
 */
public class LapanganController {

    private LapanganDAO lapanganDAO;

    public LapanganController() {
        this.lapanganDAO = new LapanganDAO();
    }

    /**
     * Validasi input lapangan
     * @param namaLapangan nama lapangan
     * @param jenisLapangan jenis lapangan
     * @param hargaPerJam harga per jam
     * @param statusLapangan status lapangan
     * @return pesan error atau null jika valid
     */
    public String validateInput(String namaLapangan, String jenisLapangan,
                                String hargaPerJam, String statusLapangan) {
        // Validasi nama lapangan
        if (namaLapangan == null || namaLapangan.trim().isEmpty()) {
            return "Nama lapangan tidak boleh kosong!";
        }
        if (namaLapangan.trim().length() < 3) {
            return "Nama lapangan minimal 3 karakter!";
        }
        if (namaLapangan.trim().length() > 100) {
            return "Nama lapangan maksimal 100 karakter!";
        }

        // Validasi jenis lapangan
        if (jenisLapangan == null || jenisLapangan.trim().isEmpty()) {
            return "Jenis lapangan tidak boleh kosong!";
        }

        // Validasi harga per jam
        if (hargaPerJam == null || hargaPerJam.trim().isEmpty()) {
            return "Harga per jam tidak boleh kosong!";
        }
        try {
            BigDecimal harga = new BigDecimal(hargaPerJam.trim());
            if (harga.compareTo(BigDecimal.ZERO) <= 0) {
                return "Harga per jam harus lebih dari 0!";
            }
            if (harga.compareTo(new BigDecimal("99999999.99")) > 0) {
                return "Harga per jam terlalu besar!";
            }
        } catch (NumberFormatException e) {
            return "Format harga tidak valid! Gunakan angka saja.";
        }

        // Validasi status lapangan
        if (statusLapangan == null || statusLapangan.trim().isEmpty()) {
            return "Status lapangan tidak boleh kosong!";
        }
        if (!statusLapangan.equals("Tersedia") &&
                !statusLapangan.equals("Tidak Tersedia") &&
                !statusLapangan.equals("Maintenance")) {
            return "Status lapangan tidak valid!";
        }

        return null; // Valid
    }

    /**
     * Menambah lapangan baru
     */
    public boolean addLapangan(String namaLapangan, String jenisLapangan,
                               String hargaPerJam, String statusLapangan, String deskripsi) {
        Lapangan lapangan = new Lapangan();
        lapangan.setNamaLapangan(namaLapangan.trim());
        lapangan.setJenisLapangan(jenisLapangan.trim());
        lapangan.setHargaPerJam(new BigDecimal(hargaPerJam.trim()));
        lapangan.setStatusLapangan(statusLapangan);
        lapangan.setDeskripsi(deskripsi != null ? deskripsi.trim() : "");

        return lapanganDAO.insert(lapangan);
    }

    /**
     * Mengubah data lapangan
     */
    public boolean updateLapangan(int idLapangan, String namaLapangan, String jenisLapangan,
                                  String hargaPerJam, String statusLapangan, String deskripsi) {
        Lapangan lapangan = new Lapangan();
        lapangan.setIdLapangan(idLapangan);
        lapangan.setNamaLapangan(namaLapangan.trim());
        lapangan.setJenisLapangan(jenisLapangan.trim());
        lapangan.setHargaPerJam(new BigDecimal(hargaPerJam.trim()));
        lapangan.setStatusLapangan(statusLapangan);
        lapangan.setDeskripsi(deskripsi != null ? deskripsi.trim() : "");

        return lapanganDAO.update(lapangan);
    }

    /**
     * Menghapus lapangan
     */
    public boolean deleteLapangan(int idLapangan) {
        return lapanganDAO.delete(idLapangan);
    }

    /**
     * Mendapatkan semua lapangan
     */
    public List<Lapangan> getAllLapangan() {
        return lapanganDAO.getAll();
    }

    /**
     * Mendapatkan lapangan berdasarkan ID
     */
    public Lapangan getLapanganById(int idLapangan) {
        return lapanganDAO.getById(idLapangan);
    }

    /**
     * Mendapatkan lapangan yang tersedia
     */
    public List<Lapangan> getAvailableLapangan() {
        return lapanganDAO.getAvailable();
    }

    /**
     * Mencari lapangan
     */
    public List<Lapangan> searchLapangan(String keyword) {
        return lapanganDAO.searchByName(keyword);
    }
}
