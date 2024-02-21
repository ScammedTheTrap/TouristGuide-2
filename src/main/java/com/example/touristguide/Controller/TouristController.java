package com.example.touristguide.Controller;


import com.example.touristguide.Model.TouristAttraction;
import com.example.touristguide.Service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/attractions")
public class TouristController {

    private final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }


    @PostMapping("/save")
    public ResponseEntity<TouristAttraction> saveAttraction(@RequestBody TouristAttraction touristAttraction) {
        TouristAttraction savedAttraction = touristService.saveAttraction(touristAttraction);
        if (savedAttraction != null) {
            return new ResponseEntity<>(savedAttraction, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<List<TouristAttraction>> getAllAttractions() {
        List<TouristAttraction> attractions = touristService.getAllAttractions();
        return new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    @GetMapping("") //_____
    public String allAttractions(Model model) {
        List<TouristAttraction> attractions = touristService.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "attractions";
    }

//Opgave 7
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("touristAttraction", new TouristAttraction());
        return "AddAttractions"; // Navnet på din Thymeleaf-skabelon for tilføjelse af en ny attraktion
    }

    //opgave 7
    @PostMapping("/add")
    public String addAttraction(@ModelAttribute TouristAttraction touristAttraction, Model model) {
        TouristAttraction newAttraction = touristService.createAttraction(touristAttraction);
        model.addAttribute("touristAttraction", newAttraction);
        return "redirect:/attractions";
    }
    /* @PostMapping("/add")
      public ResponseEntity<TouristAttraction> addAttraction(@RequestBody TouristAttraction touristAttraction) {
          TouristAttraction newAttraction = touristService.createAttraction(touristAttraction);
          return new ResponseEntity<>(newAttraction, HttpStatus.CREATED);
      } */

    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> getAttractionByName(@PathVariable String name) {
        Optional<TouristAttraction> attraction = touristService.findAttractionByName(name);
        if (attraction.isPresent()) {
            return ResponseEntity.ok(attraction.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

 @PostMapping("/delete/{name}")
 public String deleteAttraction(@PathVariable String name, RedirectAttributes redirectAttributes) {
     boolean isDeleted = touristService.deleteAttraction(name);
     if (isDeleted) {
         redirectAttributes.addFlashAttribute("successMessage", "Attraktionen blev slettet.");
     } else {
         redirectAttributes.addFlashAttribute("errorMessage", "Attraktionen kunne ikke findes.");
     }
     return "redirect:/attractions";
 }

   /* @GetMapping("/{name}/edit") //____
    public ResponseEntity<?> updateAttraction(@PathVariable String name, @RequestBody TouristAttraction touristAttraction) {
        TouristAttraction updatedAttraction = touristService.updateAttraction(name, touristAttraction);
        if (updatedAttraction != null) {
            return new ResponseEntity<>(updatedAttraction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } */
    @PostMapping("/edit")
    public String updateAttraction(@ModelAttribute TouristAttraction touristAttraction) {

        try {
            TouristAttraction updatedAttraction = touristService.updateAttraction(touristAttraction.getName(), touristAttraction);
            //redirectAttributes.addFlashAttribute("successMessage", "Attraktionen '" + updatedAttraction.getName() + "' blev opdateret.");
            return "redirect:/attractions";
        } catch (NoSuchElementException e) {
            //redirectAttributes.addFlashAttribute("errorMessage", "Attraktionen kunne ikke findes og opdateres.");
            return "redirect:/attractions";
        }
    }


//____________________________________
    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute TouristAttraction touristAttraction, RedirectAttributes redirectAttributes) {
        try {
            TouristAttraction updatedAttraction = touristService.updateAttraction(touristAttraction.getName(), touristAttraction);
            redirectAttributes.addFlashAttribute("successMessage", "Attraktionen '" + updatedAttraction.getName() + "' blev opdateret.");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Attraktionen kunne ikke findes og opdateres.");
        }
        return "redirect:/attractions";
    }

    @GetMapping("/{name}/edit")
    public String showUpdateForm(@PathVariable String name, Model model) {
        Optional<TouristAttraction> attraction = touristService.findAttractionByName(name);
        if (attraction.isPresent()) {
            model.addAttribute("attraction", attraction.get());
            return "UpdateAttraction"; // Navnet på Thymeleaf-skabelon for opdatering
        } else {
            return "redirect:/attractions";
        }
    }

//__________________________




//Endpoint til vores tags
    @GetMapping("/{name}/tags")
    public String getAttractionTags(@PathVariable String name, Model model) {
        Optional<TouristAttraction> attraction = touristService.findAttractionByName(name);
        if (attraction.isPresent()) {
            model.addAttribute("attraction", attraction.get());
            // Antager at du har en liste af tags i din TouristAttraction model
            model.addAttribute("tags", attraction.get().getTags());
            return "tags"; // tags.html
        } else {
            return "redirect:/attractions";
        }
    }


}
