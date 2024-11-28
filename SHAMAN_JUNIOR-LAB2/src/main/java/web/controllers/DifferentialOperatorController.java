package web.controllers;


import database.repositories.MathFunctionsRepository;
import function.api.TabulatedFunction;
import function.factory.TabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;
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
    public String differentialOperator(Model model, HttpSession session,@RequestParam(required = false) boolean showError,@RequestParam(required = false) String errorMessage) {

        if(showError) {
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

        if(session.getAttribute("resultDiffFunc") == null) {
            model.addAttribute("resultDiffFunc",null);
        }
        else{
            TabulatedFunction func = (TabulatedFunction) session.getAttribute("resultDiffFunc");

            model.addAttribute("resultDiffFunc",session.getAttribute("resultDiffFunc"));
            model.addAttribute("resultDiffFunc",func);
            model.addAttribute("countOfResultDiff", func.getCount());
        }

        model.addAttribute("functions",mathFunctionsRepository.findAll());

        return "differential-operation";
    }

    @PostMapping("/doDiff")
    public String doDiffOperator(Model model,HttpSession session){

        TabulatedFunction func = (TabulatedFunction) session.getAttribute("diffFunc");

        TabulatedDifferentialOperator service = new TabulatedDifferentialOperator((TabulatedFunctionFactory) session.getAttribute("FACTORY_KEY"));

        if(func != null){
            TabulatedFunction result = service.derive(func);
            session.setAttribute("resultDiffFunc",result);
        }

        return "redirect:/differential-operation";
    }
}
