//package web.controllers;
//
//import database.models.UserEntity;
//import database.repositories.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import web.DTO.UserDTO;
//import web.core.FunctionRepository;
//import web.security.Role;
//import web.service.MathFunctionsService;
//
//import javax.naming.AuthenticationException;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//
//@SpringBootTest
//@ActiveProfiles("test")
//@AutoConfigureMockMvc
//class UserControllerTest {
//
//    @MockBean
//    private PasswordEncoder passwordEncoder;
//
//    @MockBean
//    private FunctionRepository functionRepository;
//
//    @MockBean
//    private MathFunctionsService mathFunctionsService;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @MockBean
//    private UserEntity userEntity;
//
//    @MockBean
//    private UserDTO userDto;
//
//    @MockBean
//    private AuthenticationManager authenticationManager;
//
//    @BeforeEach
//    public void setUp() {
//        // Подготовка мока для аутентификации
//        Authentication authentication = mock(UsernamePasswordAuthenticationToken.class);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//
//    @Test
//    public void testLoginPage() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
//                .andExpect(MockMvcResultMatchers.view().name("login"));
//    }
//
//    @Test
//    public void testHomePage() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/"))
//                .andExpect(MockMvcResultMatchers.view().name("home"));
//    }
//    @Test
//    public void testLoginSubmitSuccess() throws Exception {
//        String username = "testuser";
//        String password = "password";
//
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                .thenReturn(mock(Authentication.class));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/login")
//                        .param("username", username)
//                        .param("password", password))
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/home"));
//    }
//
//
//    @Test
//    public void testRegisterPage() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
//                .andExpect(MockMvcResultMatchers.view().name("registration"));
//    }
//
//    @Test
//    public void testRegisterUserSuccess() throws Exception {
//        String username = "newuser";
//        String password = "password123";
//
//        when(userRepository.findByUsername(username)).thenReturn(Optional.empty()); // Новый пользователь
//        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/registere")
//                        .param("username", username)
//                        .param("password", password))
//                .andExpect(MockMvcResultMatchers.view().name("registration"))
//                .andExpect(MockMvcResultMatchers.model().attribute("message", "Пользователь успешно зарегистрирован"));
//    }
//
//    @Test
//    public void testRegisterUserFailure() throws Exception {
//        String username = "existinguser";
//        String password = "password123";
//
//        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new UserEntity()));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/registere")
//                        .param("username", username)
//                        .param("password", password))
//                .andExpect(MockMvcResultMatchers.view().name("registration"))
//                .andExpect(MockMvcResultMatchers.model().attribute("message", "Пользователь с таким именем уже существует"));
//    }
//
//    @Test
//    public void testRegisterUserApiSuccess() throws Exception {
//        String username = "newuser";
//        String password = "password123";
//        String role = "USER";
//
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername(username);
//        userDTO.setPassword(password);
//        userDTO.setRole(Role.USER);
//
//        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
//        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/reg")
//                        .contentType("application/json")
//                        .content("{\"username\":\"" + username + "\", \"password\":\"" + password + "\", \"role\":\"" + role + "\"}"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Пользователь успешно зарегистрирован"));
//    }
//
//    @Test
//    public void testRegisterUserApiFailure() throws Exception {
//        String username = "existinguser";
//        String password = "password123";
//        String role = "USER";
//
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername(username);
//        userDTO.setPassword(password);
//        userDTO.setRole(Role.USER);
//
//        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new UserEntity()));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/reg")
//                        .contentType("application/json")
//                        .content("{\"username\":\"" + username + "\", \"password\":\"" + password + "\", \"role\":\"" + role + "\"}"))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.content().string("Пользователь с таким именем уже существует"));
//    }
//
//}