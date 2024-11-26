package web.controllers;


import database.repositories.MathFunctionsRepository;
import function.api.TabulatedFunction;
import function.factory.TabulatedFunctionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping("/differential-operation")
public class DifferentialOperatorController {

    @Autowired
    private MathFunctionsRepository mathFunctionsRepository;

    @GetMapping
    public String differentialOperator(Model model, HttpSession session,@RequestParam(required = false) String showError,@RequestParam(required = false) String errorMessage) {

        if(showError != null) {
            model.addAttribute("showError", showError);
            model.addAttribute("errorMessage", errorMessage);
        }

        if(session.getAttribute("diffFunc") == null) {
            model.addAttribute("diffFunc",null);
        }
        else{
            TabulatedFunction func = (TabulatedFunction) session.getAttribute("diffFunc");

            model.addAttribute("diffFunc",session.getAttribute("diffFunc"));
            model.addAttribute("diffFunction",func);
            model.addAttribute("countOfDiff", func.getCount());
        }

        model.addAttribute("functions",mathFunctionsRepository.findAll());

        return "differential-operation";
    }
}
