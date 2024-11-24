package web.controllers;

import database.models.MathFunctionsEntity;
import database.models.PointEntity;
import database.repositories.MathFunctionsRepository;
import function.ArrayTabulatedFunction;
import function.Point;
import function.api.TabulatedFunction;
import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.TabulatedFunctionFactory;
import operations.TabulatedFunctionOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/tabulated-operations")
public class TabulatedOperationsController {

    @Autowired
    private MathFunctionsRepository mathFunctionsRepository;

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

        if(session.getAttribute("result") == null) {
            model.addAttribute("result",null);
        }
        else{
            TabulatedFunction resultFunc = (TabulatedFunction) session.getAttribute("result");
            model.addAttribute("result",session.getAttribute("result"));
            model.addAttribute("resultFunc",resultFunc);
            model.addAttribute("count3", resultFunc.getCount());
        }

        model.addAttribute("functions",mathFunctionsRepository.findAll());

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

    @PostMapping("/doOperation")
    public String doOperation(@RequestParam String operation, Model model, HttpSession session) {

        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService((TabulatedFunctionFactory) session.getAttribute("FACTORY_KEY"));
        TabulatedFunction result = null;
        if(session.getAttribute("operand1Func") != null && session.getAttribute("operand2Func") != null) {
            TabulatedFunction func1 = (TabulatedFunction) session.getAttribute("operand1Func");
            TabulatedFunction func2 = (TabulatedFunction) session.getAttribute("operand2Func");

            switch (operation) {
                case "add":
                    result = service.add(func1, func2);
                    break;
                case "subtract":
                    result = service.subtract(func1, func2);
                    break;
                case "multiply":
                    result = service.multiply(func1, func2);
                    break;
                case "divide":
                    result = service.devide(func1, func2);
                    break;
            }
        }else{
            throw new IllegalArgumentException("Invalid operand");
        }

        session.setAttribute("result", result);
        return "redirect:/tabulated-operations";
    }

    @PostMapping("/load")
    public String load(@RequestParam("target") String target, @RequestParam("id") Long id, Model model, HttpSession session) {
        System.out.println("Loading " + target);

        System.out.println(target);

        MathFunctionsEntity loadFunc = mathFunctionsRepository.findById(id).orElse(null);

        List<PointEntity> list = loadFunc.getPoints();

        double[] x = new double[list.size()];
        double[] y = new double[list.size()];

        int i = 0;
        for(PointEntity point : list) {
            System.out.println(point.getId());
            x[i] = point.getX();
            System.out.println(point.getX());
            y[i] = point.getY();
            System.out.println(point.getY());
            i++;
        }

        TabulatedFunction result = new ArrayTabulatedFunction(x,y);
        session.setAttribute(target+"Func", result);
        return "redirect:/tabulated-operations";
    }
}
