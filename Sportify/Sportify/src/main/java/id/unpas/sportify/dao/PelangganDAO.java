package id.unpas.sportify.dao;

import id.unpas.sportify.config.DatabaseConfig;
import id.unpas.sportify.model.Pelanggan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object untuk entitas Pelanggan
 * Menyediakan operasi CRUD untuk tabel pelanggan
 */
public class PelangganDAO {

    private Connection connection;

    public PelangganDAO() {
        this.connection = DatabaseConfig.getConnection();
    }

    /**
     * Menambah data pelanggan baru
     * @param pelanggan objek Pelanggan yang akan ditambahkan
     * @return true jika berhasil
     */
    public boolean insert(Pelanggan pelanggan) {
        String sql = "INSERT INTO pelanggan (nama_pelanggan, no_telepon, email, alamat) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pelanggan.getNamaPelanggan());
            stmt.setString(2, pelanggan.getNoTelepon());
            stmt.setString(3, pelanggan.getEmail());
            stmt.setString(4, pelanggan.getAlamat());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menambah pelanggan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengubah data pelanggan
     * @param pelanggan objek Pelanggan yang akan diubah
     * @return true jika berhasil
     */
    public boolean update(Pelanggan pelanggan) {
        String sql = "UPDATE pelanggan SET nama_pelanggan = ?, no_telepon = ?, email = ?, alamat = ? WHERE id_pelanggan = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pelanggan.getNamaPelanggan());
            stmt.setString(2, pelanggan.getNoTelepon());
            stmt.setString(3, pelanggan.getEmail());
            stmt.setString(4, pelanggan.getAlamat());
            stmt.setInt(5, pelanggan.getIdPelanggan());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat mengubah pelanggan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus data pelanggan
     * @param idPelanggan ID pelanggan yang akan dihapus
     * @return true jika berhasil
     */
    public boolean delete(int idPelanggan) {
        String sql = "DELETE FROM pelanggan WHERE id_pelanggan = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPelanggan);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus pelanggan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mendapatkan semua data pelanggan
     * @return List of Pelanggan
     */
    public List<Pelanggan> getAll() {
        List<Pelanggan> pelangganList = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan ORDER BY id_pelanggan";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pelanggan pelanggan = extractPelangganFromResultSet(rs);
                pelangganList.add(pelanggan);
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data pelanggan: " + e.getMessage());
            e.printStackTrace();
        }

        return pelangganList;
    }

    /**
     * Mendapatkan data pelanggan berdasarkan ID
     * @param idPelanggan ID pelanggan
     * @return objek Pelanggan atau null jika tidak ditemukan
     */
    public Pelanggan getById(int idPelanggan) {
        String sql = "SELECT * FROM pelanggan WHERE id_pelanggan = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPelanggan);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractPelangganFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data pelanggan: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Mencari pelanggan berdasarkan nama atau nomor telepon
     * @param keyword kata kunci pencarian
     * @return List of Pelanggan yang sesuai
     */
    public List<Pelanggan> search(String keyword) {
        List<Pelanggan> pelangganList = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan WHERE nama_pelanggan LIKE ? OR no_telepon LIKE ? OR email LIKE ? ORDER BY id_pelanggan";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pelanggan pelanggan = extractPelangganFromResultSet(rs);
                    pelangganList.add(pelanggan);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat mencari pelanggan: " + e.getMessage());
            e.printStackTrace();
        }

        return pelangganList;
    }

    /**
     * Helper method untuk extract Pelanggan dari ResultSet
     */
    private Pelanggan extractPelangganFromResultSet(ResultSet rs) throws SQLException {
        Pelanggan pelanggan = new Pelanggan();
        pelanggan.setIdPelanggan(rs.getInt("id_pelanggan"));
        pelanggan.setNamaPelanggan(rs.getString("nama_pelanggan"));
        pelanggan.setNoTelepon(rs.getString("no_telepon"));
        pelanggan.setEmail(rs.getString("email"));
        pelanggan.setAlamat(rs.getString("alamat"));
        pelanggan.setCreatedAt(rs.getTimestamp("created_at"));
        pelanggan.setUpdatedAt(rs.getTimestamp("updated_at"));
        return pelanggan;
    }
}
