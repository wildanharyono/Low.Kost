package com.example.haryono.lowkost.Model;

/**
 * Created by haryono on 4/2/2018.
 */


import java.io.Serializable;

//Class model untuk Menerima output dari json Photo di firebase
//implements Serializable berfungsi supaya model dapat di passing menggunakan putExtra
public class PhotoModel implements Serializable {
    //Deklarasi variable
    public String key;
    public String image_url;
    public String kostName;
    public String desc;
    public String name;
    public String email;
    public String lokasi;
    public String kostGenre;
    public String kostPrice;
    public String kostPhone;

    //konstruktor kosong *diperlukan oleh firebase
    public PhotoModel() {
    }

    //konstruktor
    public PhotoModel(String key, String image_url, String kostName,String kostGenre ,String desc, String lokasi, String kostPrice, String kostPhone, String name, String email) {
        this.key = key;
        this.image_url = image_url;
        this.kostName = kostName;
        this.kostGenre = kostGenre;
        this.desc = desc;
        this.kostPrice = kostPrice;
        this.kostPhone = kostPhone;
        this.name = name;
        this.email = email;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setKostName(String kostName) {
        this.kostName = kostName;
    }

    public String getKostGenre() {
        return kostGenre;
    }

    public void setKostGenre(String kostGenre) {
        this.kostGenre = kostGenre;
    }

    public String getKostPrice() {
        return kostPrice;
    }

    public void setKostPrice(String kostPrice) {
        this.kostPrice = kostPrice;
    }

    public String getKostPhone() {
        return kostPhone;
    }

    public void setKostPhone(String kostPhone) {
        this.kostPhone = kostPhone;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    //getter setter semua variable
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getKostName() {
        return kostName;
    }

    public void setTitle(String title) {
        this.kostName = kostName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
