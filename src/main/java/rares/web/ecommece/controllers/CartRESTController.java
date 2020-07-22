package rares.web.ecommece.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rares.web.ecommece.entities.Product;
import rares.web.ecommece.exception.QuantityException;
import rares.web.ecommece.model.AddToCartDTO;
import rares.web.ecommece.service.ProductService;
import rares.web.ecommece.service.ShoppingCartService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class CartRESTController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductService productService;

    @PostMapping("/cart/add")
    public ResponseEntity<String> addProductToCart(@Valid @ModelAttribute AddToCartDTO addToCartDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            //Set message for both NumberFormatExceptions and out of bounds errors
            return new ResponseEntity<>("Please enter a valid number between 0 and 10", HttpStatus.NOT_ACCEPTABLE);
        }

        Optional<Product> product = productService.findProduct(addToCartDTO.getId());
        if (product.isPresent()){
            shoppingCartService.addProduct(product.get(), addToCartDTO.getQuantity());
        } else {
            return new ResponseEntity<>("Fail", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Added!", HttpStatus.OK);
    }

    @PostMapping("/cart/remove")
    public ResponseEntity<String> removeProductFromCart(@ModelAttribute("productId") String productId){
        Optional<Product> product = productService.findProduct(Long.valueOf(productId));
        if (product.isPresent()){
            shoppingCartService.removeProduct(product.get());
        } else {
            return new ResponseEntity<>("Fail", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Product removed", HttpStatus.OK);
    }

    @PostMapping("/cart/update")
    public ResponseEntity<String> updateProductInCart(@ModelAttribute("productId") String productId,
                                                      @ModelAttribute("quantity") String quantity){

        try {
            Optional<Product> product = productService.findProduct(Long.valueOf(productId));
            if (product.isPresent()){
                shoppingCartService.updateProductQuantity(product.get(), Integer.parseInt(quantity));
            } else {
                return new ResponseEntity<>("Fail", HttpStatus.NOT_FOUND);
            }
        } catch (NumberFormatException | QuantityException e){
            return new ResponseEntity<>("Please enter a valid number between 0 and 10", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>("Product updated", HttpStatus.OK);
    }

}
