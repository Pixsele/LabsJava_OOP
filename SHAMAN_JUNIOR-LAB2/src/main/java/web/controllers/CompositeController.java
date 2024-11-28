package web.controllers;

import function.CompositeFunction;
import function.api.MathFunction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.core.AnnotatedFunctions;
import web.core.FunctionRepository;

@Controller
@RequestMapping("/composite")
public class CompositeController {

    private final FunctionRepository functionRepository;

    public CompositeController(FunctionRepository functionRepository) {
        this.functionRepository = functionRepository;
    }

    @GetMapping
    public String CompositeController(Model model) {
        model.addAttribute("functions",functionRepository.getFunctionMap());
        return "composite";
    }

    @PostMapping("/create")
    public String CompositeControllerPost(@RequestParam String firstFunc,
                                          @RequestParam String secondFunc,
                                          @RequestParam String compositeName) {

        MathFunction first = functionRepository.getFunction(firstFunc);
        MathFunction second = functionRepository.getFunction(secondFunc);

        AnnotatedFunctions function = new AnnotatedFunctions(new CompositeFunction(first,second),0,compositeName);

        functionRepository.addUserFunction(function);

        return "redirect:/composite";
    }

}
