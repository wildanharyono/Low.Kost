package com.example.haryono.lowkost;

/**
 * Created by haryono on 3/4/2018.
 */

public class Kost {
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
}