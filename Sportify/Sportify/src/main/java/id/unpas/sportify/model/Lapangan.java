package id.unpas.sportify.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Model class untuk entitas Lapangan
 */
public class Lapangan {

    private int idLapangan;
    private String namaLapangan;
    private String jenisLapangan;
    private BigDecimal hargaPerJam;
    private String statusLapangan;
    private String deskripsi;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructor kosong
    public Lapangan() {
    }

    // Constructor dengan parameter
    public Lapangan(int idLapangan, String namaLapangan, String jenisLapangan,
                    BigDecimal hargaPerJam, String statusLapangan, String deskripsi) {
        this.idLapangan = idLapangan;
        this.namaLapangan = namaLapangan;
        this.jenisLapangan = jenisLapangan;
        this.hargaPerJam = hargaPerJam;
        this.statusLapangan = statusLapangan;
        this.deskripsi = deskripsi;
    }

    // Getter dan Setter
    public int getIdLapangan() {
        return idLapangan;
    }

    public void setIdLapangan(int idLapangan) {
        this.idLapangan = idLapangan;
    }

    public String getNamaLapangan() {
        return namaLapangan;
    }

    public void setNamaLapangan(String namaLapangan) {
        this.namaLapangan = namaLapangan;
    }

    public String getJenisLapangan() {
        return jenisLapangan;
    }

    public void setJenisLapangan(String jenisLapangan) {
        this.jenisLapangan = jenisLapangan;
    }

    public BigDecimal getHargaPerJam() {
        return hargaPerJam;
    }

    public void setHargaPerJam(BigDecimal hargaPerJam) {
        this.hargaPerJam = hargaPerJam;
    }

    public String getStatusLapangan() {
        return statusLapangan;
    }

    public void setStatusLapangan(String statusLapangan) {
        this.statusLapangan = statusLapangan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
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

    @Override
    public String toString() {
        return namaLapangan + " (" + jenisLapangan + ")";
    }
}
