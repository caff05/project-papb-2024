package zi.mobile.loker;

import android.net.Uri;

public class Item {
    private String uid;
    private String namaBarang;
    private String namaPenitip;
    private String alamatPenitip;
    private String periodeMulai;
    private String periodeSelesai;
    private String kategoriItem;
    private String gambar;

    public Item(){

    }

    // Constructor
    public Item(String uid,String namaBarang, String namaPenitip, String alamatPenitip, String periodeMulai, String periodeSelesai, String kategoriItem, String gambar) {
        this.uid = uid;
        this.namaBarang = namaBarang;
        this.namaPenitip = namaPenitip;
        this.alamatPenitip = alamatPenitip;
        this.periodeMulai = periodeMulai;
        this.periodeSelesai = periodeSelesai;
        this.kategoriItem = kategoriItem;
        this.gambar = gambar;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    // Getter methods
    public String getNamaBarang() {
        return namaBarang;
    }

    public String getNamaPenitip() {
        return namaPenitip;
    }

    public String getAlamatPenitip() {
        return alamatPenitip;
    }

    public String getPeriodeMulai() {
        return periodeMulai;
    }

    public String getPeriodeSelesai() {
        return periodeSelesai;
    }

    public String getKategoriItem() {
        return kategoriItem;
    }

    public String getGambar() {
        return gambar;
    }

    // Setter methods
    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public void setNamaPenitip(String namaPenitip) {
        this.namaPenitip = namaPenitip;
    }

    public void setAlamatPenitip(String alamatPenitip) {
        this.alamatPenitip = alamatPenitip;
    }

    public void setPeriodeMulai(String periodeMulai) {
        this.periodeMulai = periodeMulai;
    }

    public void setPeriodeSelesai(String periodeSelesai) {
        this.periodeSelesai = periodeSelesai;
    }

    public void setKategoriItem(String kategoriItem) {
        this.kategoriItem = kategoriItem;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}


