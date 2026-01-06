package id.unpas.sportify.model;

import java.sql.Timestamp;

/**
 * Model class untuk entitas Pelanggan
 */
public class Pelanggan {

    private int idPelanggan;
    private String namaPelanggan;
    private String noTelepon;
    private String email;
    private String alamat;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructor kosong
    public Pelanggan() {
    }

    // Constructor dengan parameter
    public Pelanggan(int idPelanggan, String namaPelanggan, String noTelepon,
                     String email, String alamat) {
        this.idPelanggan = idPelanggan;
        this.namaPelanggan = namaPelanggan;
        this.noTelepon = noTelepon;
        this.email = email;
        this.alamat = alamat;
    }

    // Getter dan Setter
    public int getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(int idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
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
        return namaPelanggan + " (" + noTelepon + ")";
    }
}

