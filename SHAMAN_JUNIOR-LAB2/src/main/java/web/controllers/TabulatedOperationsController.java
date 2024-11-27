package web.controllers;

import database.models.MathFunctionsEntity;
import database.models.PointEntity;
import database.repositories.MathFunctionsRepository;
import exceptions.ArrayIsNotSortedException;
import exceptions.LoadFunctionExecption;
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

    private final MathFunctionsService mathFunctionsService;
    private final PointService pointService;

    @Autowired
    public TabulatedOperationsController(MathFunctionsService mathFunctionsService, PointService pointService) {
        this.mathFunctionsService = mathFunctionsService;
        this.pointService = pointService;
    }

    @GetMapping
    public String showForm(Model model, HttpSession session, @RequestParam(required = false) boolean showError,@RequestParam(required = false) String errorMessage,
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

    @GetMapping("/createForm")
    public String createForm(@RequestParam String target, @RequestParam("redirectTarget") String redirectTarget, Model model, HttpSession session) {
        model.addAttribute("target", target);
        model.addAttribute("redirectTarget", redirectTarget);
        model.addAttribute("factory", session.getAttribute("FACTORY_KEY").getClass().getSimpleName());
        return "fragments/createForm"; // Форма для создания функции
    }

    @PostMapping("/generateTable")
    public String generateTable(@RequestParam int points, @RequestParam String target, @RequestParam("redirectTarget") String redirectTarget, Model model) {
        model.addAttribute("points", points);
        model.addAttribute("target", target);
        model.addAttribute("redirectTarget", redirectTarget);
        //TODO
        System.out.println("Generating table");
        System.out.println(redirectTarget);

        return "fragments/tableForm"; // Форма с таблицей
    }

    @PostMapping("/createFunction")
    public String createFunction(@RequestParam String target, @RequestParam("redirectTarget") String redirectTarget,@RequestParam double[] x,@RequestParam double[] y, Model model, HttpSession session) {

        TabulatedFunctionFactory factory = (TabulatedFunctionFactory) session.getAttribute("FACTORY_KEY");

        TabulatedFunction function = factory.create(x, y);
        model.addAttribute("function", function);

        if (Objects.equals(target, "operand1")) {
            session.setAttribute("operand1Func", function);
        } else if (Objects.equals(target, "operand2")) {
            session.setAttribute("operand2Func", function);
        } else if (Objects.equals(target, "diff")) {
            session.setAttribute("diffFunc", function);
        }

        return "redirect:/"+redirectTarget;

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

    @PostMapping("/load")
    public String load(@RequestParam("target") String target, @RequestParam("id") Long id, Model model, HttpSession session) {
        System.out.println("Loading " + target);

        System.out.println(target);

        MathFunctionsEntity loadFunc = mathFunctionsRepository.findById(id).orElse(null);

        List<PointEntity> list = loadFunc.getPoints();
        if(list.size() >= 2){
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
        }else{
            throw new LoadFunctionExecption("Function have < 2 points");
        }
    }


    @PostMapping("/save")
    public String save(@RequestParam("target") String saveTarget, @RequestParam("funcName") String funcName, Model model, HttpSession session) {

        TabulatedFunction func = (TabulatedFunction) session.getAttribute(saveTarget+"Func");
        if(func == null) {
            throw new IllegalArgumentException("Function is empty");
        }

        MathFunctionsDTO dto = new MathFunctionsDTO();
        dto.setName(funcName);
        dto.setxTo(func.rightBound());
        dto.setxFrom(func.leftBound());
        dto.setCount(func.getCount());

        Long idResult = mathFunctionsService.create(dto).getId();

        for(int i = 0; i < func.getCount(); i++) {
            PointDTO point = new PointDTO();
            point.setFunction(idResult);
            point.setX(func.getX(i));
            point.setY(func.getY(i));

            pointService.create(point);
        }

        return "redirect:/tabulated-operations";
    }
}
