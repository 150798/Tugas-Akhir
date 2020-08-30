package com.example.latihan123;

public class DataHistory {

    private String id, saklar, updated_at, updated_by, status;

    // Construktor : kalau membuat object baru bisa langsung memansukkan nilai kedalam objectnya
    public DataHistory(String id, String saklar, String updated_at, String updated_by, String status){
        this.id=id;
        this.saklar=saklar;
        this.updated_at=updated_at;
        this.updated_by=updated_by;
        this.status=status;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaklar() {
        return saklar;
    }

    public void setSaklar(String saklar) {
        this.saklar = saklar;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
