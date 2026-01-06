package id.unpas.sportify.dao;

import id.unpas.sportify.config.DatabaseConfig;
import id.unpas.sportify.model.Lapangan;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object untuk entitas Lapangan
 * Menyediakan operasi CRUD untuk tabel lapangan
 */
public class LapanganDAO {

    private Connection connection;

    public LapanganDAO() {
        this.connection = DatabaseConfig.getConnection();
    }

    /**
     * Menambah data lapangan baru
     * @param lapangan objek Lapangan yang akan ditambahkan
     * @return true jika berhasil
     */
    public boolean insert(Lapangan lapangan) {
        String sql = "INSERT INTO lapangan (nama_lapangan, jenis_lapangan, harga_per_jam, status_lapangan, deskripsi) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, lapangan.getNamaLapangan());
            stmt.setString(2, lapangan.getJenisLapangan());
            stmt.setBigDecimal(3, lapangan.getHargaPerJam());
            stmt.setString(4, lapangan.getStatusLapangan());
            stmt.setString(5, lapangan.getDeskripsi());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menambah lapangan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengubah data lapangan
     * @param lapangan objek Lapangan yang akan diubah
     * @return true jika berhasil
     */
    public boolean update(Lapangan lapangan) {
        String sql = "UPDATE lapangan SET nama_lapangan = ?, jenis_lapangan = ?, harga_per_jam = ?, status_lapangan = ?, deskripsi = ? WHERE id_lapangan = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, lapangan.getNamaLapangan());
            stmt.setString(2, lapangan.getJenisLapangan());
            stmt.setBigDecimal(3, lapangan.getHargaPerJam());
            stmt.setString(4, lapangan.getStatusLapangan());
            stmt.setString(5, lapangan.getDeskripsi());
            stmt.setInt(6, lapangan.getIdLapangan());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat mengubah lapangan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus data lapangan
     * @param idLapangan ID lapangan yang akan dihapus
     * @return true jika berhasil
     */
    public boolean delete(int idLapangan) {
        String sql = "DELETE FROM lapangan WHERE id_lapangan = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idLapangan);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus lapangan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mendapatkan semua data lapangan
     * @return List of Lapangan
     */
    public List<Lapangan> getAll() {
        List<Lapangan> lapanganList = new ArrayList<>();
        String sql = "SELECT * FROM lapangan ORDER BY id_lapangan";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Lapangan lapangan = extractLapanganFromResultSet(rs);
                lapanganList.add(lapangan);
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data lapangan: " + e.getMessage());
            e.printStackTrace();
        }

        return lapanganList;
    }

    /**
     * Mendapatkan data lapangan berdasarkan ID
     * @param idLapangan ID lapangan
     * @return objek Lapangan atau null jika tidak ditemukan
     */
    public Lapangan getById(int idLapangan) {
        String sql = "SELECT * FROM lapangan WHERE id_lapangan = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idLapangan);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractLapanganFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data lapangan: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Mendapatkan lapangan yang tersedia
     * @return List of Lapangan yang tersedia
     */
    public List<Lapangan> getAvailable() {
        List<Lapangan> lapanganList = new ArrayList<>();
        String sql = "SELECT * FROM lapangan WHERE status_lapangan = 'Tersedia' ORDER BY id_lapangan";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Lapangan lapangan = extractLapanganFromResultSet(rs);
                lapanganList.add(lapangan);
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data lapangan tersedia: " + e.getMessage());
            e.printStackTrace();
        }

        return lapanganList;
    }

    /**
     * Mencari lapangan berdasarkan nama
     * @param keyword kata kunci pencarian
     * @return List of Lapangan yang sesuai
     */
    public List<Lapangan> searchByName(String keyword) {
        List<Lapangan> lapanganList = new ArrayList<>();
        String sql = "SELECT * FROM lapangan WHERE nama_lapangan LIKE ? OR jenis_lapangan LIKE ? ORDER BY id_lapangan";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Lapangan lapangan = extractLapanganFromResultSet(rs);
                    lapanganList.add(lapangan);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat mencari lapangan: " + e.getMessage());
            e.printStackTrace();
        }

        return lapanganList;
    }

    /**
     * Helper method untuk extract Lapangan dari ResultSet
     */
    private Lapangan extractLapanganFromResultSet(ResultSet rs) throws SQLException {
        Lapangan lapangan = new Lapangan();
        lapangan.setIdLapangan(rs.getInt("id_lapangan"));
        lapangan.setNamaLapangan(rs.getString("nama_lapangan"));
        lapangan.setJenisLapangan(rs.getString("jenis_lapangan"));
        lapangan.setHargaPerJam(rs.getBigDecimal("harga_per_jam"));
        lapangan.setStatusLapangan(rs.getString("status_lapangan"));
        lapangan.setDeskripsi(rs.getString("deskripsi"));
        lapangan.setCreatedAt(rs.getTimestamp("created_at"));
        lapangan.setUpdatedAt(rs.getTimestamp("updated_at"));
        return lapangan;
    }
}
