package rares.web.ecommece.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import rares.web.ecommece.model.UserLoginDTO;

@Controller
public class GeneralController {

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userLoginDTO", new UserLoginDTO());
        return "login";
    }
}
