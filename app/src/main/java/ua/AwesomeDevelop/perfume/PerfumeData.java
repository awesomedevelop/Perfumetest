package ua.AwesomeDevelop.perfume;

/**
 * Created by Taras on 17.12.2014.
 */
public class PerfumeData {
    String name;
    String image;
    String notes;
    String description;
    int favorite;
    String brand;
    public PerfumeData(String name, String image,String notes,String description, int favorite, String brand) {
        this.name = name;
        this.description=description;
        this.image = image;
        this.notes = notes;
        this.favorite=favorite;
        this.brand = brand;
    }

    public int getFavorite(){return favorite;}
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
    public String getBrand() {return brand;}

}
