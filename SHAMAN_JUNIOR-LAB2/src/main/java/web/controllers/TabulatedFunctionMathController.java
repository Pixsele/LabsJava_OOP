package web.controllers;


import function.api.MathFunction;
import function.api.TabulatedFunction;
import function.factory.ArrayTabulatedFunctionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/tabulated-function-mathfunc")
public class TabulatedFunctionMathController {

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("functionMap",MathFunctionProvider.mathFunctions());
        return "tabulated-function-mathfunc";
    }

    @PostMapping("/create")
    public String createFunction(@RequestParam("function") String functionName,
                                 @RequestParam("xFrom") double xFrom ,
                                 @RequestParam("xTo") double xTo,
                                 @RequestParam("count") int count,
                                 Model model){

        Map<String, MathFunction> functions = MathFunctionProvider.mathFunctions();

        ArrayTabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunction func = factory.create(functions.get(functionName),xFrom,xTo,count);


        model.addAttribute("success","Успех");
        model.addAttribute("showModal",true);
        return "tabulated-function-mathfunc";
    }

}
