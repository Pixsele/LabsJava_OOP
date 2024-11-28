package web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import web.DTO.MathFunctionsDTO;
import web.service.MathFunctionsService;

import java.util.Arrays;
import java.util.List;


import static javafx.beans.binding.Bindings.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MathFunctionsContorellerTest {

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private SomeController someController;

    @BeforeEach
    public void setUp() {
        // Мокируем Authentication
        when(authentication.getName()).thenReturn("user");
        when(authentication.getAuthorities()).thenReturn(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

        // Мокируем SecurityContext
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Устанавливаем SecurityContext в SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testCreateFunction() throws Exception {
        MathFunctionsDTO dto = new MathFunctionsDTO();
        dto.setName("Test Function");
        when(mathFunctionsService.create(Mockito.any(MathFunctionsDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/functions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Function\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Function"));
    }

    @Test
    public void testGetFunction() throws Exception {
        MathFunctionsDTO dto = new MathFunctionsDTO();
        dto.setId(1L);
        dto.setName("Test Function");
        when(mathFunctionsService.read(1L)).thenReturn(dto);

        mockMvc.perform(get("/functions/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Function"));
    }

    @Test
    public void testUpdateFunction() throws Exception {
        MathFunctionsDTO dto = new MathFunctionsDTO();
        dto.setId(1L);
        dto.setName("Updated Function");
        when(mathFunctionsService.update(Mockito.any(MathFunctionsDTO.class))).thenReturn(dto);

        mockMvc.perform(put("/functions/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Function\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Function"));
    }

    @Test
    public void testDeleteFunction() throws Exception {
        MathFunctionsDTO dto = new MathFunctionsDTO();
        dto.setId(1L);
        when(mathFunctionsService.getById(1L)).thenReturn(dto);

        mockMvc.perform(delete("/functions/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByName() throws Exception {
        MathFunctionsDTO dto = new MathFunctionsDTO();
        dto.setId(1L);
        dto.setName("Test Function");
        List<MathFunctionsDTO> functions = Arrays.asList(dto);
        when(mathFunctionsService.findsByName("Test Function")).thenReturn(functions);

        mockMvc.perform(get("/functions/search")
                        .param("name", "Test Function"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Function"));
    }
}
