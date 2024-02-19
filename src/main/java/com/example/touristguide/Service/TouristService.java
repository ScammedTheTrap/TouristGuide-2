package com.example.touristguide.Service;


import com.example.touristguide.Model.TouristAttraction;
import com.example.touristguide.Repository.TouristRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TouristService {
    private final TouristRepository touristRepository;


    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    public List<TouristAttraction> getAllAttractions() {
        return touristRepository.findAllAttractions();
    }

    public TouristAttraction createAttraction(TouristAttraction touristAttraction) {
        return touristRepository.save(touristAttraction);
    }

    public boolean deleteAttraction(String name) {
        touristRepository.delete(name);
        return false;
    }


    //gemmer attraktioner
    public TouristAttraction saveAttraction(TouristAttraction touristAttraction) {
        return touristRepository.save(touristAttraction);
    }



    public TouristAttraction updateAttraction(String name, TouristAttraction updatedAttraction) {
        TouristAttraction updated = touristRepository.update(name, updatedAttraction);
        if (updated == null) {
            throw new NoSuchElementException("TouristAttraction with name " + name + " not found");
        }
        return updated;
    }

    public Optional<TouristAttraction> findAttractionByName(String name) {
        return touristRepository.findAllAttractions().stream()
                .filter(attraction -> attraction.getName().equalsIgnoreCase(name))
                .findFirst();
    }


    public List<TouristAttraction> findAllAttractions() {
        return touristRepository.findAllAttractions();
    }
}
