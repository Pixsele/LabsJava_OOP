package web;



import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;
import exceptions.InconsistentFunctionsException;
import exceptions.LoadFunctionExecption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ArrayIsNotSortedException.class)
    public String handleArrayIsNotSortedException(ArrayIsNotSortedException exception, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        String page = "/";

        if(Objects.equals(request.getRequestURI(), "/tabulated-operations/createFunction")){
            page = "tabulated-operations";
        }

        redirectAttributes.addAttribute("errorMessage", exception.getMessage());
        redirectAttributes.addAttribute("showError", true);
        System.out.println("poppppp");
        return "redirect:/"+page;
    }

    @ExceptionHandler(DifferentLengthOfArraysException.class)
    public String handleDifferentLengthOfArraysException(DifferentLengthOfArraysException exception, Model model, HttpServletRequest request){

        String page = "/";
        //TODO
        if(Objects.equals(request.getRequestURI(), "/tabulated-function-array/create")){
            page = "tabulated-function-array";
        }

        model.addAttribute("errorTitle","Ошибка");
        model.addAttribute("errorMessage",exception.getMessage());

        return request.getHeader("Referer");
    }

    @ExceptionHandler(LoadFunctionExecption.class)
    public ResponseEntity<Map<String, String>> handleLoadFunctionExecption(LoadFunctionExecption exception, Model model, HttpServletRequest request){
        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());
        response.put("showError", "true");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InconsistentFunctionsException.class)
    public String handleInconsistentFunctionsException(InconsistentFunctionsException exception, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){

        redirectAttributes.addAttribute("errorMessage", exception.getMessage());
        redirectAttributes.addAttribute("showError", true);

        return "redirect:/tabulated-operations";
    }

}
