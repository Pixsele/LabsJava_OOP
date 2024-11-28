package web.controllers;

import database.repositories.MathFunctionsRepository;
import function.api.TabulatedFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/graph")
public class GraphController {

    @Autowired
    MathFunctionsRepository mathFunctionsRepository;

    @GetMapping
    public String graph(Model model, HttpSession session, @RequestParam(required = false) boolean showError, @RequestParam(required = false) String errorMessage) {

        if(showError) {
            model.addAttribute("showError", showError);
            model.addAttribute("errorMessage", errorMessage);
        }

        if(session.getAttribute("graphFunc") == null) {
            model.addAttribute("graphFunc",null);
        }
        else{
            TabulatedFunction result = (TabulatedFunction) session.getAttribute("graphFunc");
            model.addAttribute("graphFunc",session.getAttribute("graphFunc"));
            model.addAttribute("graphFunc",result);
            model.addAttribute("countGraph", result.getCount());
        }
        if(session.getAttribute("graphResult") == null) {
            model.addAttribute("graphResult",null);
        }
        else {
            model.addAttribute("graphResult",session.getAttribute("graphResult"));
        }

        model.addAttribute("functions",mathFunctionsRepository.findAll());

        return "graph";
    }

    @PostMapping("/apply")
    public String apply(@RequestParam("xToApply") double xToApply, Model model, HttpSession session) {

        TabulatedFunction func = (TabulatedFunction) session.getAttribute("graphFunc");
        double result = func.apply(xToApply);

        session.setAttribute("graphResult",result);
        return "redirect:/graph";
    }
}
