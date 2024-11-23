package web.controllers;

import function.Point;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/tabulated-operations")
public class TabulatedOperationsController {

    @GetMapping
    public String showForm(Model model, HttpSession session) {

        if(session.getAttribute("operand1Func") == null) {
            model.addAttribute("operand1Func",null);
        }
        else{
            TabulatedFunction func = (TabulatedFunction) session.getAttribute("operand1Func");

            model.addAttribute("operand1Func",session.getAttribute("operand1Func"));
            model.addAttribute("function1",func);
            model.addAttribute("count1", func.getCount());
        }

        if(session.getAttribute("operand2Func") == null) {
            model.addAttribute("operand2Func",null);
        }
        else{
            TabulatedFunction func = (TabulatedFunction) session.getAttribute("operand2Func");
            model.addAttribute("operand2Func",session.getAttribute("operand2Func"));
            model.addAttribute("function2",func);
            model.addAttribute("count2", func.getCount());
        }

        return "tabulated-operations";
    }

    @GetMapping("/createForm")
    public String createForm(@RequestParam String target, Model model, HttpSession session) {
        model.addAttribute("target", target);
        model.addAttribute("factory",session.getAttribute("FACTORY_KEY").getClass().getSimpleName());
        return "fragments/createForm"; // Форма для создания функции
    }

    @PostMapping("/generateTable")
    public String generateTable(@RequestParam int points, @RequestParam String target, Model model) {
        model.addAttribute("points", points);
        model.addAttribute("target", target);
        //TODO
        System.out.println("Generating table");
        return "fragments/tableForm"; // Форма с таблицей
    }

    @PostMapping("/createFunction")
    public String createFunction(@RequestParam String target, @RequestParam double[] x, @RequestParam double[] y, Model model, HttpSession session) {

        TabulatedFunctionFactory factory = (TabulatedFunctionFactory) session.getAttribute("FACTORY_KEY");

        factory= new ArrayTabulatedFunctionFactory();
        TabulatedFunction function = factory.create(x,y);

        model.addAttribute("function", function);

        if(Objects.equals(target, "operand1,operand1")) {
            session.setAttribute("operand1Func", function);
        }
        else if(Objects.equals(target, "operand2,operand2")) {
            session.setAttribute("operand2Func", function);
        }
        return "redirect:/tabulated-operations";
    }
}
