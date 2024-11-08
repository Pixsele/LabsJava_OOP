package db.security;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        testUserDTO = new UserDTO();
        testUserDTO.setUsername("testUser");
        testUserDTO.setPassword("password");
        testUserDTO.setRole(Role.USER);
    }

    @Test
    void testRegisterUser_Success() throws Exception {
        Mockito.when(userRepository.findByUsername(testUserDTO.getUsername())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(testUserDTO.getPassword())).thenReturn("encodedPassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Пользователь успешно зарегистрирован"));
    }

    @Test
    void testRegisterUser_UserAlreadyExists() throws Exception {
        Mockito.when(userRepository.findByUsername(testUserDTO.getUsername())).thenReturn(Optional.of(new UserEntity()));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Пользователь с таким именем уже существует"));
    }

    @Test
    void testLoginUser_Success() throws Exception {
        Authentication auth = Mockito.mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        Mockito.when(jwtUtil.generateToken(eq(testUserDTO.getUsername()), eq(testUserDTO.getRole()))).thenReturn("jwt-token");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"));
    }

    @Test
    void testLoginUser_FailedAuthentication() throws Exception {
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserDTO)))
                .andExpect(status().isNotFound());
    }
}
