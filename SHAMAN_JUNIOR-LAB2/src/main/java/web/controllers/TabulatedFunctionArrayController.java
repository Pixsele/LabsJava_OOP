package web.controllers;


import function.api.TabulatedFunction;
import function.factory.ArrayTabulatedFunctionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tabulated-function-array")
public class TabulatedFunctionArrayController {

    @GetMapping
    public String showForm(Model model) {
        return "tabulated-function-array";
    }

    @PostMapping("/generate")
    public String generateTable(@RequestParam("pointCount") int pointCount, Model model) {

        model.addAttribute("pointCount", pointCount);
        return "tabulated-function-array";
    }

    @PostMapping("/create")
    public String createFunction(@RequestParam("x") double[] x, @RequestParam("y") double [] y,Model model){

        ArrayTabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunction func = factory.create(x,y);


        model.addAttribute("success","Успех");
        model.addAttribute("showModal",true);
        return "tabulated-function-array";
    }

}
