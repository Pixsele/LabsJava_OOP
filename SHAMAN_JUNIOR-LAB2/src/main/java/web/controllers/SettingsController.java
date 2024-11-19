package web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SettingsController {

    @PostMapping("/settings")
    public String updateSettings(@RequestParam("factoryType") String factoryType) {

        String type;

        if ("array".equals(factoryType)) {
            type = "array";
        } else if ("linkedlist".equals(factoryType)) {
            type = "linkedlist";
        }
        return "home";
    }
}
