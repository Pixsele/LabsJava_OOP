package web.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import web.DTO.PointDTO;
import web.service.MathFunctionsService;
import web.service.PointService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PointControllerTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private MathFunctionsService mathFunctionsService;

    @MockBean
    private PointService pointService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testCreateFunction() throws Exception {

        PointDTO inputDto = new PointDTO();
        PointDTO outputDto = new PointDTO();

        when(pointService.create(any())).thenReturn(outputDto);

        mockMvc.perform(post("/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(outputDto)));
    }

    @Test
    public void testReadFunction() throws Exception {
        long id = 1L;
        PointDTO outputDto = new PointDTO();

        when(pointService.read(id)).thenReturn(outputDto);

        mockMvc.perform(get("/point/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(outputDto)));
    }

    @Test
    public void testUpdateFunction() throws Exception {
        long id = 1L;
        PointDTO inputDto = new PointDTO();
        PointDTO outputDto = new PointDTO();


        when(pointService.update(any())).thenReturn(outputDto);

        mockMvc.perform(put("/point/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(outputDto)));
    }

    @Test
    public void testDeleteFunction() throws Exception {
        long id = 1L;

        when(pointService.getById(id)).thenReturn(new PointDTO());

        mockMvc.perform(delete("/point/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFunction_NotFound() throws Exception {
        long id = 1L;

        when(pointService.getById(id)).thenReturn(null);

        mockMvc.perform(delete("/point/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindPointsByFunction() throws Exception {
        Long functionId = 1L;
        PointDTO point1 = new PointDTO();
        point1.setX(1.0);
        point1.setY(2.0);

        PointDTO point2 = new PointDTO();
        point2.setX(3.0);
        point2.setY(4.0);

        List<PointDTO> points = Arrays.asList(point1, point2);

        when(pointService.findByFunction(functionId)).thenReturn(points);

        mockMvc.perform(get("/point/search")
                        .param("id", functionId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].x", is(1.0)))
                .andExpect(jsonPath("$[0].y", is(2.0)))
                .andExpect(jsonPath("$[1].x", is(3.0)))
                .andExpect(jsonPath("$[1].y", is(4.0)));
    }

    @Test
    void testFindPointsByFunction_NoContent() throws Exception {
        Long functionId = 1L;
        List<PointDTO> emptyList = new ArrayList<>();

        when(pointService.findByFunction(functionId)).thenReturn(emptyList);

        // Выполняем запрос
        mockMvc.perform(get("/point/search")
                        .param("id", functionId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}