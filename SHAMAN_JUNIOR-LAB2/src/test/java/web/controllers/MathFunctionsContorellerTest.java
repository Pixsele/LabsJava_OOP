package web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import web.DTO.MathFunctionsDTO;
import web.service.MathFunctionsService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class MathFunctionsContorellerTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private MathFunctionsService mathFunctionsService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testCreateFunction() throws Exception {
        MathFunctionsDTO inputDto = new MathFunctionsDTO();
        MathFunctionsDTO outputDto = new MathFunctionsDTO();

        when(mathFunctionsService.create(any())).thenReturn(outputDto);

        mockMvc.perform(post("/functions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(outputDto)));
    }

    @Test
    public void testReadFunction() throws Exception {
        long id = 1L;
        MathFunctionsDTO outputDto = new MathFunctionsDTO();

        when(mathFunctionsService.read(id)).thenReturn(outputDto);

        mockMvc.perform(get("/functions/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(outputDto)));
    }

    @Test
    public void testUpdateFunction() throws Exception {
        long id = 1L;
        MathFunctionsDTO inputDto = new MathFunctionsDTO();
        MathFunctionsDTO outputDto = new MathFunctionsDTO();

        when(mathFunctionsService.update(any())).thenReturn(outputDto);

        mockMvc.perform(put("/functions/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(outputDto)));
    }

    @Test
    public void testDeleteFunction() throws Exception {
        long id = 1L;

        when(mathFunctionsService.getById(id)).thenReturn(new MathFunctionsDTO());

        mockMvc.perform(delete("/functions/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFunction_NotFound() throws Exception {
        long id = 1L;

        when(mathFunctionsService.getById(id)).thenReturn(null);

        mockMvc.perform(delete("/functions/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindByName() throws Exception {
        String name = "add";
        MathFunctionsDTO dto1 = new MathFunctionsDTO();
        dto1.setName("add");
        MathFunctionsDTO dto2 = new MathFunctionsDTO();
        dto2.setName("add");

        List<MathFunctionsDTO> responseList = Arrays.asList(dto1, dto2);

        when(mathFunctionsService.findsByName(name)).thenReturn(responseList);

        mockMvc.perform(get("/functions/search")
                        .param("name", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testFindByNameNoResults() throws Exception {
        String name = "subtract";

        // Мокаем поведение сервиса
        when(mathFunctionsService.findsByName(name)).thenReturn(Collections.emptyList());

        // Выполняем запрос
        mockMvc.perform(get("/functions/search")
                        .param("name", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


}