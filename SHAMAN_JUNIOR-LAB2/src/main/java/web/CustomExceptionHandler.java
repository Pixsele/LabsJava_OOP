package web;



import exceptions.ArrayIsNotSortedException;
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
        //TODO
        if(Objects.equals(request.getRequestURI(), "/tabulated-function/create")){
            page = "tabulated-function-form";
        }

        model.addAttribute("errorTitle","Ошибка");
        model.addAttribute("errorMessage",exception.getMessage());

        return page;
    }
}
