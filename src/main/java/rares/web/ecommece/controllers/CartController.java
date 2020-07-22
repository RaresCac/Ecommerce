package rares.web.ecommece.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rares.web.ecommece.model.CheckoutDTO;
import rares.web.ecommece.service.ShoppingCartService;

import javax.validation.Valid;

@Controller
public class CartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/cart")
    public String cart(Model model){
        model.addAttribute("cart", shoppingCartService.getProductsInCart());
        return "cart";
    }

    @GetMapping("/cart/checkout")
    public String cartCheckout(Model model){
        model.addAttribute("checkoutDTO", new CheckoutDTO());
        return "checkout";
    }

    @PostMapping("/cart/checkout")
    public String checkoutProcess(@Valid @ModelAttribute CheckoutDTO checkoutDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            return "checkout";
        }

        shoppingCartService.checkout(checkoutDTO);
        //Previous call wipes shoppingCart for the user in session
        redirectAttributes.addFlashAttribute("cart", shoppingCartService.getProductsInCart());
        return "redirect:/cart?success";
    }
}
