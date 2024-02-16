package com.example.touristguide.Controller;


import com.example.touristguide.Model.TouristAttraction;
import com.example.touristguide.Service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/attractions")
public class TouristController {

    private final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping("")
    public ModelAndView allAttractions() {
        List<TouristAttraction> attractions = touristService.getAllAttractions();
        ModelAndView modelAndView = new ModelAndView("attractionList");
        modelAndView.addObject("attractions", attractions);
        return modelAndView;
    }
    @GetMapping("/attractions")
    public String allAttractions(Model model) {
        List<TouristAttraction> attractions = touristService.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "attractionList"; // Navnet på din HTML-skabelon uden .html
    }



    @GetMapping("/test")
    public ResponseEntity<List<TouristAttraction>> getAllAttractions() {
        List<TouristAttraction> attractions = touristService.getAllAttractions();
        return new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> getAttractionByName(@PathVariable String name) {
        Optional<TouristAttraction> attraction = touristService.findAttractionByName(name);
        if (attraction.isPresent()) {
            return ResponseEntity.ok(attraction.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<TouristAttraction> addAttraction(@RequestBody TouristAttraction touristAttraction) {
        TouristAttraction newAttraction = touristService.createAttraction(touristAttraction);
        return new ResponseEntity<>(newAttraction, HttpStatus.CREATED);
    }

    @PutMapping("/update/{name}")
    public ResponseEntity<?> updateAttraction(@PathVariable String name, @RequestBody TouristAttraction touristAttraction) {
        TouristAttraction updatedAttraction = touristService.updateAttraction(name, touristAttraction);
        if (updatedAttraction != null) {
            return new ResponseEntity<>(updatedAttraction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> deleteAttraction(@PathVariable String name) {
        if (touristService.deleteAttraction(name)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}