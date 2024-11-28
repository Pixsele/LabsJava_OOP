package web.controllers;


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
import web.core.FunctionRepository;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Controller
@RequestMapping("/tabulated-function-mathfunc")
public class TabulatedFunctionMathController {

    public FunctionRepository functionRepository;

    public TabulatedFunctionMathController(FunctionRepository functionRepository) {
        this.functionRepository = functionRepository;
    }

    public TabulatedFunctionFactory tableFunctionFactory;

    @GetMapping("/modal")
    public String showModalForm(@RequestParam("redirectTarget") String redirectTarget,
                                @RequestParam("target") String target, Model model, HttpSession session) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(session.getAttribute("FACTORY_KEY") != null) {
            tableFunctionFactory = (TabulatedFunctionFactory) session.getAttribute("FACTORY_KEY");
        }
        else{
            tableFunctionFactory = new ArrayTabulatedFunctionFactory();
        }

        model.addAttribute("factory", tableFunctionFactory.getClass().getSimpleName());

        Map<String ,MathFunction> mapfunc = functionRepository.getFunctionMap();

        model.addAttribute("functionMap",mapfunc);
        model.addAttribute("redirectTarget", redirectTarget);
        model.addAttribute("target", target);
        return "fragments/modalFormMathFunc";
    }

    @PostMapping("/createModal")
    public String createModalFunction(@RequestParam("function") String functionName,
                                 @RequestParam("xFrom") double xFrom ,
                                 @RequestParam("xTo") double xTo,
                                 @RequestParam("count") int count,
                                 @RequestParam("redirectTarget") String redirectTarget,
                                 @RequestParam("target") String target,
                                 Model model, HttpSession session){

        MathFunction function = functionRepository.getFunction(functionName);

        TabulatedFunction func = tableFunctionFactory.create(function,xFrom,xTo,count);
        session.setAttribute(target+"Func",func);

        return "redirect:/"+redirectTarget;
    }
}
