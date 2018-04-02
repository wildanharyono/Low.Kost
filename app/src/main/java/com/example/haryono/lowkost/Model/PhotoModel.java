package com.example.haryono.lowkost.Model;

/**
 * Created by haryono on 4/2/2018.
 */


import java.io.Serializable;
import java.util.ArrayList;

//Class model untuk Menerima output dari json Photo di firebase
//implements Serializable berfungsi supaya model dapat di passing menggunakan putExtra
public class PhotoModel implements Serializable {
    //Deklarasi variable
    public String key;
    public String image_url;
    public String title;
    public String desc;
    public String name;
    public String email;
    public String lokasi;

    //konstruktor kosong *diperlukan oleh firebase
    public PhotoModel() {
    }

    //konstruktor
    public PhotoModel(String key, String image_url, String title, String desc, String lokasi, String name, String email) {
        this.key = key;
        this.image_url = image_url;
        this.title = title;
        this.desc = desc;
        this.lokasi = lokasi;
        this.name = name;
        this.email = email;
    }

    public String getLokasi() {
        return lokasi;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
