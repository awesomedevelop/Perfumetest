package com.example.taras.perfumetest;

/**
 * Created by Taras on 17.12.2014.
 */
public class PerfumeData {
    String name;
    String image;
    String notes;
    String description;
    public PerfumeData(String name, String image,String notes,String description) {
        this.name = name;
        this.description=description;
        this.image = image;
        this.notes = notes;
    }


    public String getName() {
        return name;
    }
    public  String getDescription(){return description;}
    public String getImage() {
        return image;
    }

    public String getNotes(){
        return  notes;
    }

}
