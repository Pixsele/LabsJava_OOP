package web.controllers;


import exceptions.ArrayIsNotSortedException;
import function.api.MathFunction;
import function.api.TabulatedFunction;
import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.TabulatedFunctionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/tabulated-function-mathfunc")
public class TabulatedFunctionMathController {

    private TabulatedFunctionFactory tableFunctionFactory;

    @GetMapping
    public String showForm(Model model, HttpSession session) {
        if(session.getAttribute("FACTORY_KEY") != null) {
            tableFunctionFactory = (TabulatedFunctionFactory) session.getAttribute("FACTORY_KEY");
        }
        else{
            tableFunctionFactory = new ArrayTabulatedFunctionFactory();
        }

        model.addAttribute("factory", tableFunctionFactory.getClass().getSimpleName());


        model.addAttribute("functionMap",MathFunctionProvider.mathFunctions());
        return "tabulated-function-mathfunc";
    }

    @GetMapping("/modal")
    public String showModalForm(@RequestParam("redirectTarget") String redirectTarget,
                                @RequestParam("target") String target, Model model, HttpSession session) {
        if(session.getAttribute("FACTORY_KEY") != null) {
            tableFunctionFactory = (TabulatedFunctionFactory) session.getAttribute("FACTORY_KEY");
        }
        else{
            tableFunctionFactory = new ArrayTabulatedFunctionFactory();
        }

        model.addAttribute("factory", tableFunctionFactory.getClass().getSimpleName());


        model.addAttribute("functionMap",MathFunctionProvider.mathFunctions());
        model.addAttribute("redirectTarget", redirectTarget);
        model.addAttribute("target", target);
        return "fragments/modalFormMathFunc";
    }

    @PostMapping("/create")
    public String createFunction(@RequestParam("function") String functionName,
                                 @RequestParam("xFrom") double xFrom ,
                                 @RequestParam("xTo") double xTo,
                                 @RequestParam("count") int count,
                                 Model model){

        Map<String, MathFunction> functions = MathFunctionProvider.mathFunctions();

        TabulatedFunction func = tableFunctionFactory.create(functions.get(functionName),xFrom,xTo,count);


        model.addAttribute("success","Успех");
        model.addAttribute("showModal",true);
        model.addAttribute("factory", tableFunctionFactory.getClass().getSimpleName());
        model.addAttribute("functionMap",MathFunctionProvider.mathFunctions());
        System.out.println("food");

        if(true){
            throw new IllegalArgumentException("popka");
        }

        return "tabulated-function-mathfunc";
    }

    @PostMapping("/createModal")
    public String createModalFunction(@RequestParam("function") String functionName,
                                 @RequestParam("xFrom") double xFrom ,
                                 @RequestParam("xTo") double xTo,
                                 @RequestParam("count") int count,
                                 @RequestParam("redirectTarget") String redirectTarget,
                                 @RequestParam("target") String target,
                                 Model model, HttpSession session){

        Map<String, MathFunction> functions = MathFunctionProvider.mathFunctions();

        TabulatedFunction func = tableFunctionFactory.create(functions.get(functionName),xFrom,xTo,count);
        session.setAttribute(target+"Func",func);
        System.out.println(redirectTarget);

        return "redirect:/"+redirectTarget;
    }
}
