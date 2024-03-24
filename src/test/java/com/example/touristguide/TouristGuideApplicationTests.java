package com.example.touristguide;

import com.example.touristguide.Model.TouristAttraction;
import com.example.touristguide.Service.TouristService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class TouristGuideApplicationTests {

    private MockMvc mockMvc;


    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    void contextLoads() {
    }


    @Test
    public void addAttraction_ShouldAddAttractionAndRedirect() throws Exception {
        TouristAttraction attraction = new TouristAttraction("Tivoli", "A historic amusement park in the center of Copenhagen", "Copenhagen", Arrays.asList("Family-Friendly", "Historic"));

        when(TouristService.createAttraction(any(TouristAttraction.class))).thenReturn(attraction);

        mockMvc.perform(post("/attractions/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Tivoli")
                        .param("description", "A historic amusement park in the center of Copenhagen")
                        .param("city", "Copenhagen"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));
    }

    @Test
    public void deleteAttraction_ShouldDeleteAttractionAndRedirect() throws Exception {
        TouristService touristService = null;  //intet at gøre i denne test, problemer konstant.
        doNothing().when(touristService).deleteAttraction("Tivoli");

        mockMvc.perform(post("/attractions/delete/Tivoli"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));
    }
    @Test
    public void testAllAttractionsPage() throws Exception {
        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractions"))
                .andExpect(model().attributeExists("attractions"));
    }

}
