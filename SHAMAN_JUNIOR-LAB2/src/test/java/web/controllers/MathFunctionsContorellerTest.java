//package db.controllers;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.is;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import db.DTO.MathFunctionsDTO;
//import db.security.Role;
//import db.service.MathFunctionsService;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class MathFunctionsContorellerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @MockBean
//    private MathFunctionsService mathFunctionsService;
//
//    private String token;
//
//    @BeforeEach
//    public void setUp() {
//        token = jwtUtil.generateToken("username", Role.ADMIN);
//    }
//
//    @Test
//    public void testCreateFunction() throws Exception {
//
//        MathFunctionsDTO inputDto = new MathFunctionsDTO();
//        MathFunctionsDTO outputDto = new MathFunctionsDTO();
//
//        when(mathFunctionsService.create(any())).thenReturn(outputDto);
//
//        mockMvc.perform(post("/functions")
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(inputDto)))
//                .andExpect(status().isOk())
//                .andExpect(content().json(new ObjectMapper().writeValueAsString(outputDto)));
//    }
//
//    @Test
//    public void testReadFunction() throws Exception {
//        long id = 1L;
//        MathFunctionsDTO outputDto = new MathFunctionsDTO();
//
//        when(mathFunctionsService.read(id)).thenReturn(outputDto);
//
//        mockMvc.perform(get("/functions/" + id)
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
//                .andExpect(status().isOk())
//                .andExpect(content().json(new ObjectMapper().writeValueAsString(outputDto)));
//    }
//
//    @Test
//    public void testUpdateFunction() throws Exception {
//        long id = 1L;
//        MathFunctionsDTO inputDto = new MathFunctionsDTO();
//        MathFunctionsDTO outputDto = new MathFunctionsDTO();
//
//        when(mathFunctionsService.update(any())).thenReturn(outputDto);
//
//        mockMvc.perform(put("/functions/" + id)
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(inputDto)))
//                .andExpect(status().isOk())
//                .andExpect(content().json(new ObjectMapper().writeValueAsString(outputDto)));
//    }
//
//    @Test
//    public void testDeleteFunction() throws Exception {
//        long id = 1L;
//
//        when(mathFunctionsService.getById(id)).thenReturn(new MathFunctionsDTO());
//
//        mockMvc.perform(delete("/functions/" + id)
//                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testDeleteFunction_NotFound() throws Exception {
//        long id = 1L;
//
//        when(mathFunctionsService.getById(id)).thenReturn(null);
//
//        mockMvc.perform(delete("/functions/" + id)
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void testFindByName() throws Exception {
//        String name = "add";
//        MathFunctionsDTO dto1 = new MathFunctionsDTO();
//        dto1.setName("add");
//        MathFunctionsDTO dto2 = new MathFunctionsDTO();
//        dto2.setName("add");
//
//        List<MathFunctionsDTO> responseList = Arrays.asList(dto1, dto2);
//
//        when(mathFunctionsService.findsByName(name)).thenReturn(responseList);
//
//        mockMvc.perform(get("/functions/search")
//                        .param("name", name)
//                        .contentType(MediaType.APPLICATION_JSON)
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
//                .andExpect(status().isOk()) // Ожидаем статус 200 OK
//                .andExpect(jsonPath("$", hasSize(2))) // Проверка на размер ответа
//                .andExpect(jsonPath("$[0].name", is("add"))) // Проверка на правильность имени первого элемента
//                .andExpect(jsonPath("$[1].name", is("add"))); // Проверка на правильность имени второго элемента
//    }
//
//    @Test
//    public void testFindByNameNoResults() throws Exception {
//        String name = "subtract";
//
//        // Мокаем поведение сервиса
//        when(mathFunctionsService.findsByName(name)).thenReturn(Collections.emptyList());
//
//        // Выполняем запрос
//        mockMvc.perform(get("/functions/search")
//                        .param("name", name)
//                        .contentType(MediaType.APPLICATION_JSON)
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
//                .andExpect(status().isNoContent());
//    }
//}