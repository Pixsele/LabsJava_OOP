package web.controllers;

import web.DTO.UserDTO;
import database.models.UserEntity;
import database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import web.security.Role;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
public class UserController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              Model model,
                              HttpServletRequest request) {
        try {
            // Создаем объект аутентификации
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // Устанавливаем аутентификацию в SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/home";
        } catch (AuthenticationException e) {
            // Если аутентификация не удалась, показываем ошибку
            model.addAttribute("error", "Неверное имя пользователя или пароль");
            return "login"; // Отображаем страницу логина с ошибкой
        }
    }



    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Пользователь с таким именем уже существует");
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole(userDto.getRole());
        newUser.setCreationTime(LocalDateTime.now());

        userRepository.save(newUser);
        return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    }

    @GetMapping("/reg")
    public String register(Model model) {
        return "registration";
    }

    @PostMapping("/registere")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String role,
                               Model model) {
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("message", "Пользователь с таким именем уже существует");
            return "registration"; // возвращаем страницу регистрации с ошибкой
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(Role.valueOf(role));
        newUser.setCreationTime(LocalDateTime.now());

        userRepository.save(newUser);
        model.addAttribute("message", "Пользователь успешно зарегистрирован");
        return "registration"; // возвращаем страницу регистрации с успешным сообщением
    }
}