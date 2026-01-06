package id.unpas.sportify.controller;

import id.unpas.sportify.dao.BookingDAO;
import id.unpas.sportify.dao.LapanganDAO;
import id.unpas.sportify.model.Booking;
import id.unpas.sportify.model.Lapangan;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Controller untuk mengelola operasi Booking
 * Menangani validasi dan logika bisnis
 */
public class BookingController {

    private BookingDAO bookingDAO;
    private LapanganDAO lapanganDAO;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    public BookingController() {
        this.bookingDAO = new BookingDAO();
        this.lapanganDAO = new LapanganDAO();
    }

    /**
     * Validasi input booking
     */
    public String validateInput(int idLapangan, int idPelanggan, String tanggalBooking,
                                String jamMulai, String jamSelesai, int excludeBookingId) {
        // Validasi lapangan
        if (idLapangan <= 0) {
            return "Lapangan harus dipilih!";
        }

        // Validasi pelanggan
        if (idPelanggan <= 0) {
            return "Pelanggan harus dipilih!";
        }

        // Validasi tanggal booking
        if (tanggalBooking == null || tanggalBooking.trim().isEmpty()) {
            return "Tanggal booking tidak boleh kosong!";
        }

        Date tglBooking;
        try {
            java.util.Date parsed = DATE_FORMAT.parse(tanggalBooking);
            tglBooking = new Date(parsed.getTime());

            // Cek apakah tanggal sudah lewat
            Date today = new Date(System.currentTimeMillis());
            if (tglBooking.before(today)) {
                return "Tanggal booking tidak boleh kurang dari hari ini!";
            }
        } catch (ParseException e) {
            return "Format tanggal tidak valid! Gunakan format YYYY-MM-DD";
        }

        // Validasi jam mulai
        if (jamMulai == null || jamMulai.trim().isEmpty()) {
            return "Jam mulai tidak boleh kosong!";
        }

        Time timeMulai;
        try {
            java.util.Date parsed = TIME_FORMAT.parse(jamMulai);
            timeMulai = new Time(parsed.getTime());
        } catch (ParseException e) {
            return "Format jam mulai tidak valid! Gunakan format HH:mm";
        }

        // Validasi jam selesai
        if (jamSelesai == null || jamSelesai.trim().isEmpty()) {
            return "Jam selesai tidak boleh kosong!";
        }

        Time timeSelesai;
        try {
            java.util.Date parsed = TIME_FORMAT.parse(jamSelesai);
            timeSelesai = new Time(parsed.getTime());
        } catch (ParseException e) {
            return "Format jam selesai tidak valid! Gunakan format HH:mm";
        }

        // Validasi jam selesai harus lebih besar dari jam mulai
        if (!timeSelesai.after(timeMulai)) {
            return "Jam selesai harus lebih besar dari jam mulai!";
        }

        // Cek minimum durasi 1 jam
        long durationMillis = timeSelesai.getTime() - timeMulai.getTime();
        long durationHours = durationMillis / (60 * 60 * 1000);
        if (durationHours < 1) {
            return "Durasi booking minimal 1 jam!";
        }

        // Cek maksimum durasi 5 jam
        if (durationHours > 5) {
            return "Durasi booking maksimal 5 jam!";
        }

        // Cek konflik jadwal
        if (bookingDAO.isScheduleConflict(idLapangan, tglBooking, timeMulai, timeSelesai, excludeBookingId)) {
            return "Jadwal sudah terisi! Silakan pilih waktu lain.";
        }

        return null; // Valid
    }

    /**
     * Menghitung total harga booking
     */
    public BigDecimal calculateTotalPrice(int idLapangan, String jamMulai, String jamSelesai) {
        try {
            Lapangan lapangan = lapanganDAO.getById(idLapangan);
            if (lapangan == null) {
                return BigDecimal.ZERO;
            }

            java.util.Date startTime = TIME_FORMAT.parse(jamMulai);
            java.util.Date endTime = TIME_FORMAT.parse(jamSelesai);

            long durationMillis = endTime.getTime() - startTime.getTime();
            long durationHours = durationMillis / (60 * 60 * 1000);

            return lapangan.getHargaPerJam().multiply(new BigDecimal(durationHours));
        } catch (ParseException e) {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Menambah booking baru
     */
    public boolean addBooking(int idLapangan, int idPelanggan, String tanggalBooking,
                              String jamMulai, String jamSelesai, String statusBooking,
                              String keterangan) {
        try {
            Booking booking = new Booking();
            booking.setIdLapangan(idLapangan);
            booking.setIdPelanggan(idPelanggan);

            java.util.Date parsedDate = DATE_FORMAT.parse(tanggalBooking);
            booking.setTanggalBooking(new Date(parsedDate.getTime()));

            java.util.Date parsedStartTime = TIME_FORMAT.parse(jamMulai);
            booking.setJamMulai(new Time(parsedStartTime.getTime()));

            java.util.Date parsedEndTime = TIME_FORMAT.parse(jamSelesai);
            booking.setJamSelesai(new Time(parsedEndTime.getTime()));

            booking.setTotalHarga(calculateTotalPrice(idLapangan, jamMulai, jamSelesai));
            booking.setStatusBooking(statusBooking);
            booking.setKeterangan(keterangan != null ? keterangan.trim() : "");

            return bookingDAO.insert(booking);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengubah data booking
     */
    public boolean updateBooking(int idBooking, int idLapangan, int idPelanggan,
                                 String tanggalBooking, String jamMulai, String jamSelesai,
                                 String statusBooking, String keterangan) {
        try {
            Booking booking = new Booking();
            booking.setIdBooking(idBooking);
            booking.setIdLapangan(idLapangan);
            booking.setIdPelanggan(idPelanggan);

            java.util.Date parsedDate = DATE_FORMAT.parse(tanggalBooking);
            booking.setTanggalBooking(new Date(parsedDate.getTime()));

            java.util.Date parsedStartTime = TIME_FORMAT.parse(jamMulai);
            booking.setJamMulai(new Time(parsedStartTime.getTime()));

            java.util.Date parsedEndTime = TIME_FORMAT.parse(jamSelesai);
            booking.setJamSelesai(new Time(parsedEndTime.getTime()));

            booking.setTotalHarga(calculateTotalPrice(idLapangan, jamMulai, jamSelesai));
            booking.setStatusBooking(statusBooking);
            booking.setKeterangan(keterangan != null ? keterangan.trim() : "");

            return bookingDAO.update(booking);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus booking
     */
    public boolean deleteBooking(int idBooking) {
        return bookingDAO.delete(idBooking);
    }

    /**
     * Mendapatkan semua booking
     */
    public List<Booking> getAllBooking() {
        return bookingDAO.getAll();
    }

    /**
     * Mendapatkan booking berdasarkan ID
     */
    public Booking getBookingById(int idBooking) {
        return bookingDAO.getById(idBooking);
    }

    /**
     * Mendapatkan booking berdasarkan tanggal
     */
    public List<Booking> getBookingByDate(String tanggal) {
        try {
            java.util.Date parsed = DATE_FORMAT.parse(tanggal);
            return bookingDAO.getByDate(new Date(parsed.getTime()));
        } catch (ParseException e) {
            return bookingDAO.getAll();
        }
    }

    /**
     * Mendapatkan booking berdasarkan status
     */
    public List<Booking> getBookingByStatus(String status) {
        return bookingDAO.getByStatus(status);
    }

    /**
     * Mencari booking
     */
    public List<Booking> searchBooking(String keyword) {
        return bookingDAO.search(keyword);
    }
}
