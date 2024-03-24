package com.example.touristguide.Model;

import java.util.ArrayList;
import java.util.List;

public class TouristAttraction {
    private int id;
    private String name;
    private String description;
    private String city; //måske List<String>  ----> SKAL evt ændres for bedre funktionalitet.
    private List<String> tags;

    public TouristAttraction(int id,String name, String description, String city, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
    }
    public TouristAttraction(String name, String description, String city, List<String> tags) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
    }

    public TouristAttraction() {
        this.tags = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // Getter og setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
