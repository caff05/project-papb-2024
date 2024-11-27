package com.example.lokermanager2;

public class Loker {
    private String id;
    private String nama;
    private String status;
    private int capacity;
    private double price;

    // Constructor tanpa parameter (dibutuhkan oleh Firebase)
    public Loker() {
    }

    // Constructor dengan parameter
    public Loker(String nama, String status, int capacity, double price) {
        this.nama = nama;
        this.status = status;
        this.capacity = capacity;
        this.price = price;
    }

    // Getter dan Setter untuk setiap atribut
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
