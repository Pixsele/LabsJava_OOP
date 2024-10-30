package sql.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("userForm", new UserEntity());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Validated UserEntity userForm, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "registration";
        }

        if(!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError","Incorrect paswword");
            return "registration";
        }

        if(!userService.saveUser(userForm)){
            model.addAttribute("usernameError","Incorrect username");
            return "registration";
        }

        return "redirect:/";
    }


}
