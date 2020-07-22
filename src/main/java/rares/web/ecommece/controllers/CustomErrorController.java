package rares.web.ecommece.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "access-denied";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handle(){
        return "access-denied";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}