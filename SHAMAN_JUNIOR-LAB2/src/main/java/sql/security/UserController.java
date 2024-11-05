package sql.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/home")
    public String userHome() {
        return "Добро пожаловать, пользователь!";
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserDTO userDto) {
        // Проверьте, нет ли пользователя с таким же именем
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            return "Пользователь с таким именем уже существует";
        }

        UserEntity entity = new UserEntity();
        entity.setUsername(userDto.getUsername());
        entity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        entity.setCreationTime(LocalDateTime.now());
        entity.setRoles(userDto.getRoles());  // Установите роль, переданную в запросе

        userRepository.save(entity);
        return "Пользователь успешно зарегистрирован";
    }

}
