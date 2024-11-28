package web.controllers;


import concurrent.IntegralTaskExecutor;
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
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/integral")
public class IntegralOperatorController {

    @Autowired
    public MathFunctionsRepository mathFunctionsRepository;

    @GetMapping
    public String integral(Model model, HttpSession session, @RequestParam(required = false) String showError, @RequestParam(required = false) String errorMessage) {


        if(showError != null) {
            model.addAttribute("showError", showError);
            model.addAttribute("errorMessage", errorMessage);
        }

        if(session.getAttribute("integralFunc") == null) {
            model.addAttribute("integralFunc",null);
        }
        else{
            TabulatedFunction func = (TabulatedFunction) session.getAttribute("integralFunc");

            model.addAttribute("integralFunc",session.getAttribute("integralFunc"));
            model.addAttribute("integralFunc",func);
            model.addAttribute("countOfIntegral", func.getCount());
        }
        if(session.getAttribute("integralResult") == null) {
            model.addAttribute("integralResult",null);
        }
        else {
            model.addAttribute("integralResult",session.getAttribute("integralResult"));
        }

        model.addAttribute("functions",mathFunctionsRepository.findAll());

        return "integral-operation";
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam("threadCount") int threadCount,Model model, HttpSession session) throws ExecutionException, InterruptedException {
        TabulatedFunction func = (TabulatedFunction) session.getAttribute("integralFunc");

        IntegralTaskExecutor integralTaskExecutor = new IntegralTaskExecutor(threadCount);

        double result = integralTaskExecutor.itegrate(func);

        session.setAttribute("integralResult",result);
        return "redirect:/integral";
    }

}
