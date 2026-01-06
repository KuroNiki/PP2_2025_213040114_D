package id.unpas.sportify.controller;

import id.unpas.sportify.dao.PelangganDAO;
import id.unpas.sportify.model.Pelanggan;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Controller untuk mengelola operasi Pelanggan
 * Menangani validasi dan logika bisnis
 */
public class PelangganController {

    private PelangganDAO pelangganDAO;

    // Pattern untuk validasi email
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    // Pattern untuk validasi nomor telepon (hanya angka, 10-15 digit)
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^[0-9]{10,15}$"
    );

    public PelangganController() {
        this.pelangganDAO = new PelangganDAO();
    }

    /**
     * Validasi input pelanggan
     * @param namaPelanggan nama pelanggan
     * @param noTelepon nomor telepon
     * @param email email
     * @return pesan error atau null jika valid
     */
    public String validateInput(String namaPelanggan, String noTelepon, String email) {
        // Validasi nama pelanggan
        if (namaPelanggan == null || namaPelanggan.trim().isEmpty()) {
            return "Nama pelanggan tidak boleh kosong!";
        }
        if (namaPelanggan.trim().length() < 3) {
            return "Nama pelanggan minimal 3 karakter!";
        }
        if (namaPelanggan.trim().length() > 100) {
            return "Nama pelanggan maksimal 100 karakter!";
        }

        // Validasi nomor telepon
        if (noTelepon == null || noTelepon.trim().isEmpty()) {
            return "Nomor telepon tidak boleh kosong!";
        }
        String cleanPhone = noTelepon.trim().replaceAll("[\\s-]", "");
        if (!PHONE_PATTERN.matcher(cleanPhone).matches()) {
            return "Format nomor telepon tidak valid! Gunakan 10-15 digit angka.";
        }

        // Validasi email (opsional, tapi jika diisi harus valid)
        if (email != null && !email.trim().isEmpty()) {
            if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
                return "Format email tidak valid!";
            }
            if (email.trim().length() > 100) {
                return "Email maksimal 100 karakter!";
            }
        }

        return null; // Valid
    }

    /**
     * Menambah pelanggan baru
     */
    public boolean addPelanggan(String namaPelanggan, String noTelepon,
                                String email, String alamat) {
        Pelanggan pelanggan = new Pelanggan();
        pelanggan.setNamaPelanggan(namaPelanggan.trim());
        pelanggan.setNoTelepon(noTelepon.trim().replaceAll("[\\s-]", ""));
        pelanggan.setEmail(email != null ? email.trim() : "");
        pelanggan.setAlamat(alamat != null ? alamat.trim() : "");

        return pelangganDAO.insert(pelanggan);
    }

    /**
     * Mengubah data pelanggan
     */
    public boolean updatePelanggan(int idPelanggan, String namaPelanggan,
                                   String noTelepon, String email, String alamat) {
        Pelanggan pelanggan = new Pelanggan();
        pelanggan.setIdPelanggan(idPelanggan);
        pelanggan.setNamaPelanggan(namaPelanggan.trim());
        pelanggan.setNoTelepon(noTelepon.trim().replaceAll("[\\s-]", ""));
        pelanggan.setEmail(email != null ? email.trim() : "");
        pelanggan.setAlamat(alamat != null ? alamat.trim() : "");

        return pelangganDAO.update(pelanggan);
    }

    /**
     * Menghapus pelanggan
     */
    public boolean deletePelanggan(int idPelanggan) {
        return pelangganDAO.delete(idPelanggan);
    }

    /**
     * Mendapatkan semua pelanggan
     */
    public List<Pelanggan> getAllPelanggan() {
        return pelangganDAO.getAll();
    }

    /**
     * Mendapatkan pelanggan berdasarkan ID
     */
    public Pelanggan getPelangganById(int idPelanggan) {
        return pelangganDAO.getById(idPelanggan);
    }

    /**
     * Mencari pelanggan
     */
    public List<Pelanggan> searchPelanggan(String keyword) {
        return pelangganDAO.search(keyword);
    }
}

