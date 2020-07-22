package rares.web.ecommece.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rares.web.ecommece.exception.RegisterException;
import rares.web.ecommece.model.UserDTO;
import rares.web.ecommece.model.UserLoginDTO;
import rares.web.ecommece.service.UserService;

import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@Valid @ModelAttribute UserDTO userDTO,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            return "register";
        }

        try {
            userService.registerNewUser(userDTO);
        } catch (RegisterException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }

        redirectAttributes.addFlashAttribute("userLoginDTO", new UserLoginDTO());
        return "redirect:/login?success";
    }
}
