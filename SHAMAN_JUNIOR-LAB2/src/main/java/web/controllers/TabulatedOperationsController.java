package web.controllers;

import database.models.MathFunctionsEntity;
import database.models.PointEntity;
import database.repositories.MathFunctionsRepository;
import exceptions.ArrayIsNotSortedException;
import exceptions.LoadFunctionExecption;
import exceptions.RemoveIncorrectPoint;
import function.ArrayTabulatedFunction;
import function.api.TabulatedFunction;
import function.factory.TabulatedFunctionFactory;
import operations.TabulatedFunctionOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;
import web.DTO.MathFunctionsDTO;
import web.DTO.PointDTO;
import web.service.MathFunctionsService;
import web.service.PointService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/tabulated-operations")
public class TabulatedOperationsController {

    @Autowired
    private MathFunctionsRepository mathFunctionsRepository;


    @GetMapping
    public String showForm(Model model, HttpSession session,
                           @RequestParam(required = false) boolean showError,
                           @RequestParam(required = false) String errorMessage,
                           @RequestParam(required = false) String redirectTarget) {
        System.out.println(showError);
        if(showError) {
            model.addAttribute("showError", showError);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("redirectTarget", redirectTarget);
        }

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

        if(session.getAttribute("resultFunc") == null) {
            model.addAttribute("resultFunc",null);
        }
        else{
            TabulatedFunction result = (TabulatedFunction) session.getAttribute("resultFunc");
            model.addAttribute("resultFunc",session.getAttribute("resultFunc"));
            model.addAttribute("result",result);
            model.addAttribute("count3", result.getCount());
        }

        model.addAttribute("functions",mathFunctionsRepository.findAll());

        return "tabulated-operations";
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
        }

        session.setAttribute("resultFunc", result);
        return "redirect:/tabulated-operations";
    }
}
