package web.controllers;

import exceptions.RemoveIncorrectPoint;
import function.api.TabulatedFunction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/editing")
public class EditAndRemoveController {

    @PostMapping("/edit")
    public String edit(@RequestParam("target") String saveTarget, @RequestParam("x") double x, @RequestParam("y") double y, Model model, HttpSession session) {

        System.out.println("Edit " + saveTarget);
        System.out.println("x: " + x);
        System.out.println("y: " + y);

        TabulatedFunction func = (TabulatedFunction) session.getAttribute(saveTarget+"Func");

        func.insert(x,y);


        session.setAttribute(saveTarget+"Func", func);
        return "redirect:/tabulated-operations";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("target") String saveTarget,
                         @RequestParam("x") double x,
                         @RequestParam("redirectTarget") String redirectTarget, Model model, HttpSession session) {

        TabulatedFunction func = (TabulatedFunction) session.getAttribute(saveTarget+"Func");

        int index = func.indexOfX(x);
        if(index == -1) {
            throw new RemoveIncorrectPoint("Incorrect point");
        }

        model.addAttribute("redirectTarget", redirectTarget);
        func.remove(index);
        session.setAttribute(saveTarget+"Func", func);

        return "redirect:/tabulated-operations";
    }
}
