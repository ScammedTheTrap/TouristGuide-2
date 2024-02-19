package com.example.touristguide.Repository;

import com.example.touristguide.Model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class TouristRepository {
    private List<TouristAttraction> touristAttractions;

   /* public TouristRepository(){
        this.touristAttractions = new ArrayList<>();
        touristAttractions.add(new TouristAttraction("The Little Mermaid", "The little mermaid attraction."));
        touristAttractions.add(new TouristAttraction("Møns Klint", "A beautiful view from the Danish coast."));
        touristAttractions.add(new TouristAttraction("Tivoli", "A historic attraction for kids and adults."));
    }*/

    public TouristRepository(){
        this.touristAttractions = new ArrayList<>(Arrays.asList(

                new TouristAttraction("SMK", "Statens Museum for Kunst", "København", Arrays.asList("Kunst", "Museum")),
                new TouristAttraction("Odense Zoo", "Europas bedste zoo", "Odense", Arrays.asList("Børnevenlig")),
                new TouristAttraction("Dyrehaven", "Naturpark med skovområder", "Kongens Lyngby", Arrays.asList("Natur", "Gratis")),
                new TouristAttraction("Tivoli", "Forlystelsespark midt i København centrum", "København", Arrays.asList("Børnevenlig"))
        )); //Arrays.asList = foruddefineret værdier. Rettet så man kan tilføje flere.
    }


    //CRUD metoder
    public List<TouristAttraction> findAllAttractions(){
        return touristAttractions;
    }


    public TouristAttraction save(TouristAttraction touristAttraction){
        touristAttractions.add(touristAttraction);
        return touristAttraction;
    }

    public void delete(String name) {
        touristAttractions.removeIf(attraction -> attraction.getName().equalsIgnoreCase(name));
    }

    public TouristAttraction update(String name, TouristAttraction updatedAttraction) {
        for (int i = 0; i < touristAttractions.size(); i++) {
            TouristAttraction existingAttraction = touristAttractions.get(i);
            if (existingAttraction.getName().equalsIgnoreCase(name)) {
                touristAttractions.set(i, updatedAttraction);
                return updatedAttraction;
            }
        }
        return null;
        // return throw new NoSuchElementException("TouristAttraction with name " + name + " not found");
    }

}
