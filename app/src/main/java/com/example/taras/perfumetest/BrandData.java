package com.example.taras.perfumetest;

/**
 * Created by Taras on 17.12.2014.
 */
public class BrandData {
    String name;

    String image;
    int id_;

    public BrandData(String name, String image, int id_) {
        this.name = name;
        this.image = image;

        this.id_ = id_;
    }


    public String getName() {
        return name;
    }


    public String getImage() {
        return image;
    }


        public int getId() {
        return id_;
    }
}
