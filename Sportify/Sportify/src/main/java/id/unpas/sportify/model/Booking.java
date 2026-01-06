package id.unpas.sportify.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Model class untuk entitas Booking
 */
public class Booking {

    private int idBooking;
    private int idLapangan;
    private int idPelanggan;
    private Date tanggalBooking;
    private Time jamMulai;
    private Time jamSelesai;
    private BigDecimal totalHarga;
    private String statusBooking;
    private String keterangan;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Relasi untuk join table
    private String namaLapangan;
    private String namaPelanggan;
    private String jenisLapangan;

    // Constructor kosong
    public Booking() {
    }

    // Constructor dengan parameter
    public Booking(int idBooking, int idLapangan, int idPelanggan, Date tanggalBooking,
                   Time jamMulai, Time jamSelesai, BigDecimal totalHarga,
                   String statusBooking, String keterangan) {
        this.idBooking = idBooking;
        this.idLapangan = idLapangan;
        this.idPelanggan = idPelanggan;
        this.tanggalBooking = tanggalBooking;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.totalHarga = totalHarga;
        this.statusBooking = statusBooking;
        this.keterangan = keterangan;
    }

    // Getter dan Setter
    public int getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(int idBooking) {
        this.idBooking = idBooking;
    }

    public int getIdLapangan() {
        return idLapangan;
    }

    public void setIdLapangan(int idLapangan) {
        this.idLapangan = idLapangan;
    }

    public int getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(int idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public Date getTanggalBooking() {
        return tanggalBooking;
    }

    public void setTanggalBooking(Date tanggalBooking) {
        this.tanggalBooking = tanggalBooking;
    }

    public Time getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(Time jamMulai) {
        this.jamMulai = jamMulai;
    }

    public Time getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(Time jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public BigDecimal getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(BigDecimal totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getStatusBooking() {
        return statusBooking;
    }

    public void setStatusBooking(String statusBooking) {
        this.statusBooking = statusBooking;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getNamaLapangan() {
        return namaLapangan;
    }

    public void setNamaLapangan(String namaLapangan) {
        this.namaLapangan = namaLapangan;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getJenisLapangan() {
        return jenisLapangan;
    }

    public void setJenisLapangan(String jenisLapangan) {
        this.jenisLapangan = jenisLapangan;
    }

    @Override
    public String toString() {
        return "Booking #" + idBooking + " - " + namaLapangan;
    }
}
