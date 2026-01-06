package id.unpas.sportify.dao;

import id.unpas.sportify.config.DatabaseConfig;
import id.unpas.sportify.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object untuk entitas Booking
 * Menyediakan operasi CRUD untuk tabel booking
 */
public class BookingDAO {

    private Connection connection;

    public BookingDAO() {
        this.connection = DatabaseConfig.getConnection();
    }

    /**
     * Menambah data booking baru
     * @param booking objek Booking yang akan ditambahkan
     * @return true jika berhasil
     */
    public boolean insert(Booking booking) {
        String sql = "INSERT INTO booking (id_lapangan, id_pelanggan, tanggal_booking, jam_mulai, jam_selesai, total_harga, status_booking, keterangan) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, booking.getIdLapangan());
            stmt.setInt(2, booking.getIdPelanggan());
            stmt.setDate(3, booking.getTanggalBooking());
            stmt.setTime(4, booking.getJamMulai());
            stmt.setTime(5, booking.getJamSelesai());
            stmt.setBigDecimal(6, booking.getTotalHarga());
            stmt.setString(7, booking.getStatusBooking());
            stmt.setString(8, booking.getKeterangan());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menambah booking: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengubah data booking
     * @param booking objek Booking yang akan diubah
     * @return true jika berhasil
     */
    public boolean update(Booking booking) {
        String sql = "UPDATE booking SET id_lapangan = ?, id_pelanggan = ?, tanggal_booking = ?, jam_mulai = ?, jam_selesai = ?, total_harga = ?, status_booking = ?, keterangan = ? WHERE id_booking = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, booking.getIdLapangan());
            stmt.setInt(2, booking.getIdPelanggan());
            stmt.setDate(3, booking.getTanggalBooking());
            stmt.setTime(4, booking.getJamMulai());
            stmt.setTime(5, booking.getJamSelesai());
            stmt.setBigDecimal(6, booking.getTotalHarga());
            stmt.setString(7, booking.getStatusBooking());
            stmt.setString(8, booking.getKeterangan());
            stmt.setInt(9, booking.getIdBooking());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat mengubah booking: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus data booking
     * @param idBooking ID booking yang akan dihapus
     * @return true jika berhasil
     */
    public boolean delete(int idBooking) {
        String sql = "DELETE FROM booking WHERE id_booking = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idBooking);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus booking: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mendapatkan semua data booking dengan join ke tabel lapangan dan pelanggan
     * @return List of Booking
     */
    public List<Booking> getAll() {
        List<Booking> bookingList = new ArrayList<>();
        String sql = "SELECT b.*, l.nama_lapangan, l.jenis_lapangan, p.nama_pelanggan " +
                "FROM booking b " +
                "JOIN lapangan l ON b.id_lapangan = l.id_lapangan " +
                "JOIN pelanggan p ON b.id_pelanggan = p.id_pelanggan " +
                "ORDER BY b.tanggal_booking DESC, b.jam_mulai";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Booking booking = extractBookingFromResultSet(rs);
                booking.setNamaLapangan(rs.getString("nama_lapangan"));
                booking.setJenisLapangan(rs.getString("jenis_lapangan"));
                booking.setNamaPelanggan(rs.getString("nama_pelanggan"));
                bookingList.add(booking);
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data booking: " + e.getMessage());
            e.printStackTrace();
        }

        return bookingList;
    }

    /**
     * Mendapatkan data booking berdasarkan ID
     * @param idBooking ID booking
     * @return objek Booking atau null jika tidak ditemukan
     */
    public Booking getById(int idBooking) {
        String sql = "SELECT b.*, l.nama_lapangan, l.jenis_lapangan, p.nama_pelanggan " +
                "FROM booking b " +
                "JOIN lapangan l ON b.id_lapangan = l.id_lapangan " +
                "JOIN pelanggan p ON b.id_pelanggan = p.id_pelanggan " +
                "WHERE b.id_booking = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idBooking);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Booking booking = extractBookingFromResultSet(rs);
                    booking.setNamaLapangan(rs.getString("nama_lapangan"));
                    booking.setJenisLapangan(rs.getString("jenis_lapangan"));
                    booking.setNamaPelanggan(rs.getString("nama_pelanggan"));
                    return booking;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data booking: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Mendapatkan booking berdasarkan tanggal
     * @param tanggal tanggal booking
     * @return List of Booking
     */
    public List<Booking> getByDate(Date tanggal) {
        List<Booking> bookingList = new ArrayList<>();
        String sql = "SELECT b.*, l.nama_lapangan, l.jenis_lapangan, p.nama_pelanggan " +
                "FROM booking b " +
                "JOIN lapangan l ON b.id_lapangan = l.id_lapangan " +
                "JOIN pelanggan p ON b.id_pelanggan = p.id_pelanggan " +
                "WHERE b.tanggal_booking = ? " +
                "ORDER BY b.jam_mulai";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, tanggal);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = extractBookingFromResultSet(rs);
                    booking.setNamaLapangan(rs.getString("nama_lapangan"));
                    booking.setJenisLapangan(rs.getString("jenis_lapangan"));
                    booking.setNamaPelanggan(rs.getString("nama_pelanggan"));
                    bookingList.add(booking);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data booking by date: " + e.getMessage());
            e.printStackTrace();
        }

        return bookingList;
    }

    /**
     * Mendapatkan booking berdasarkan status
     * @param status status booking
     * @return List of Booking
     */
    public List<Booking> getByStatus(String status) {
        List<Booking> bookingList = new ArrayList<>();
        String sql = "SELECT b.*, l.nama_lapangan, l.jenis_lapangan, p.nama_pelanggan " +
                "FROM booking b " +
                "JOIN lapangan l ON b.id_lapangan = l.id_lapangan " +
                "JOIN pelanggan p ON b.id_pelanggan = p.id_pelanggan " +
                "WHERE b.status_booking = ? " +
                "ORDER BY b.tanggal_booking DESC, b.jam_mulai";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = extractBookingFromResultSet(rs);
                    booking.setNamaLapangan(rs.getString("nama_lapangan"));
                    booking.setJenisLapangan(rs.getString("jenis_lapangan"));
                    booking.setNamaPelanggan(rs.getString("nama_pelanggan"));
                    bookingList.add(booking);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data booking by status: " + e.getMessage());
            e.printStackTrace();
        }

        return bookingList;
    }

    /**
     * Cek apakah lapangan sudah dibooking pada waktu tertentu
     * @param idLapangan ID lapangan
     * @param tanggal tanggal booking
     * @param jamMulai jam mulai
     * @param jamSelesai jam selesai
     * @param excludeBookingId ID booking yang dikecualikan (untuk update)
     * @return true jika terjadi konflik jadwal
     */
    public boolean isScheduleConflict(int idLapangan, Date tanggal, Time jamMulai, Time jamSelesai, int excludeBookingId) {
        String sql = "SELECT COUNT(*) FROM booking " +
                "WHERE id_lapangan = ? AND tanggal_booking = ? " +
                "AND status_booking != 'Cancelled' " +
                "AND id_booking != ? " +
                "AND ((jam_mulai < ? AND jam_selesai > ?) OR " +
                "(jam_mulai >= ? AND jam_mulai < ?) OR " +
                "(jam_selesai > ? AND jam_selesai <= ?))";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idLapangan);
            stmt.setDate(2, tanggal);
            stmt.setInt(3, excludeBookingId);
            stmt.setTime(4, jamSelesai);
            stmt.setTime(5, jamMulai);
            stmt.setTime(6, jamMulai);
            stmt.setTime(7, jamSelesai);
            stmt.setTime(8, jamMulai);
            stmt.setTime(9, jamSelesai);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat cek konflik jadwal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Mencari booking berdasarkan nama pelanggan atau lapangan
     * @param keyword kata kunci pencarian
     * @return List of Booking yang sesuai
     */
    public List<Booking> search(String keyword) {
        List<Booking> bookingList = new ArrayList<>();
        String sql = "SELECT b.*, l.nama_lapangan, l.jenis_lapangan, p.nama_pelanggan " +
                "FROM booking b " +
                "JOIN lapangan l ON b.id_lapangan = l.id_lapangan " +
                "JOIN pelanggan p ON b.id_pelanggan = p.id_pelanggan " +
                "WHERE l.nama_lapangan LIKE ? OR p.nama_pelanggan LIKE ? " +
                "ORDER BY b.tanggal_booking DESC, b.jam_mulai";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = extractBookingFromResultSet(rs);
                    booking.setNamaLapangan(rs.getString("nama_lapangan"));
                    booking.setJenisLapangan(rs.getString("jenis_lapangan"));
                    booking.setNamaPelanggan(rs.getString("nama_pelanggan"));
                    bookingList.add(booking);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat mencari booking: " + e.getMessage());
            e.printStackTrace();
        }

        return bookingList;
    }

    /**
     * Helper method untuk extract Booking dari ResultSet
     */
    private Booking extractBookingFromResultSet(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setIdBooking(rs.getInt("id_booking"));
        booking.setIdLapangan(rs.getInt("id_lapangan"));
        booking.setIdPelanggan(rs.getInt("id_pelanggan"));
        booking.setTanggalBooking(rs.getDate("tanggal_booking"));
        booking.setJamMulai(rs.getTime("jam_mulai"));
        booking.setJamSelesai(rs.getTime("jam_selesai"));
        booking.setTotalHarga(rs.getBigDecimal("total_harga"));
        booking.setStatusBooking(rs.getString("status_booking"));
        booking.setKeterangan(rs.getString("keterangan"));
        booking.setCreatedAt(rs.getTimestamp("created_at"));
        booking.setUpdatedAt(rs.getTimestamp("updated_at"));
        return booking;
    }
}
