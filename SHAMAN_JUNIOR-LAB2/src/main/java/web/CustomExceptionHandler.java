package web;



import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ArrayIsNotSortedException.class)
    public String handleArrayIsNotSortedException(ArrayIsNotSortedException exception, Model model, HttpServletRequest request){

        String page = "/";

        if(Objects.equals(request.getRequestURI(), "/tabulated-operations/createFunction")){
            System.out.println("zalupa");
            page = "tabulated-operations";
        }

        model.addAttribute("errorMessage", exception.getMessage());
        model.addAttribute("showError", true);
        System.out.println("poppppp");
        return "tabulated-function-mathfunc";
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
}
