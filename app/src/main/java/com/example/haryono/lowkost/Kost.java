package com.example.haryono.lowkost;

import java.io.Serializable;

/**
 * Created by haryono on 3/4/2018.
 */

public class Kost implements Serializable {
    private String kostId;
    private String kostName;
    private String kostGenre;
    private String kostLokasi;
    private String kostFasilitas;
    private int kostHarga;

    public Kost(){

    }

    public Kost(String kostId, String kostName, String kostGenre, String kostLokasi, String kostFasilitas, int kostHarga) {
        this.kostId = kostId;
        this.kostName = kostName;
        this.kostGenre = kostGenre;
        this.kostLokasi = kostLokasi;
        this.kostFasilitas = kostFasilitas;
        this.kostHarga = kostHarga;
    }

    public String getKostId() {
        return kostId;
    }

    public String getKostName() {
        return kostName;
    }

    public String getKostGenre() {
        return kostGenre;
    }

    public String getKostLokasi() {
        return kostLokasi;
    }

    public String getKostFasilitas() {
        return kostFasilitas;
    }

    public int getKostHarga() {
        return kostHarga;
    }

    public void setKostId(String kostId) {
        this.kostId = kostId;
    }

    public void setKostName(String kostName) {
        this.kostName = kostName;
    }

    public void setKostGenre(String kostGenre) {
        this.kostGenre = kostGenre;
    }

    public void setKostLokasi(String kostLokasi) {
        this.kostLokasi = kostLokasi;
    }

    public void setKostFasilitas(String kostFasilitas) {
        this.kostFasilitas = kostFasilitas;
    }

    public void setKostHarga(int kostHarga) {
        this.kostHarga = kostHarga;
    }
}