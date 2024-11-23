package web.controllers;

import function.api.TabulatedFunction;
import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.LinkedListTabulatedFunctionFactory;
import function.factory.TabulatedFunctionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/tabulated-function-array")
public class TabulatedFunctionArrayController {

    TabulatedFunctionFactory tabulatedFunctionFactory;

    @GetMapping
    public String showForm(Model model,HttpSession session ,@RequestParam(required = false) Boolean showModal) {

        if(session.getAttribute("FACTORY_KEY") != null){
            tabulatedFunctionFactory = (TabulatedFunctionFactory) session.getAttribute("FACTORY_KEY");
        }
        else{
            tabulatedFunctionFactory = new LinkedListTabulatedFunctionFactory();
        }

        model.addAttribute("showModal", showModal != null && showModal);
        model.addAttribute("factory", tabulatedFunctionFactory.getClass().getSimpleName());
        return "tabulated-function-array";
    }

    @PostMapping("/generate")
    public String generateTable(@RequestParam("pointCount") int pointCount, Model model) {

        model.addAttribute("pointCount", pointCount);
        model.addAttribute("factory", tabulatedFunctionFactory.getClass().getSimpleName());
        return "tabulated-function-array";
    }


    @PostMapping("/create")
    public String createFunction(@RequestParam("x") double[] x, @RequestParam("y") double [] y,Model model) {

        TabulatedFunction func = tabulatedFunctionFactory.create(x,y);

        model.addAttribute("factory", tabulatedFunctionFactory.getClass().getSimpleName());
        model.addAttribute("success","Успех");
        model.addAttribute("showModal",true);
        return "tabulated-function-array";
    }

    @GetMapping("/table")
    public String getTable(@RequestParam("pointCount") int pointCount, Model model) {
        model.addAttribute("pointCount", pointCount);
        return "tabulated-function-array :: tableFragment";
    }

}