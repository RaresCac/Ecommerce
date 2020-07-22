package rares.web.ecommece.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rares.web.ecommece.entities.Order;
import rares.web.ecommece.exception.ProductException;
import rares.web.ecommece.model.ProductDTO;
import rares.web.ecommece.service.OrderService;
import rares.web.ecommece.service.ProductService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/admin/products")
    public String adminProducts(@ModelAttribute ProductDTO productDTO, Model model){
        model.addAttribute("productDTO", productDTO);
        return "admin-products";
    }

    @PostMapping("/admin/products")
    public String adminAddProduct(@Valid @ModelAttribute ProductDTO productDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "admin-products";
        }
        try {
            productService.addNewProduct(productDTO);
        } catch (ProductException e){
            model.addAttribute("productDTO", productDTO);
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", e.getMessage());
        }
        model.addAttribute("success", true);
        model.addAttribute("productDTO", new ProductDTO());
        return "admin-products";
    }

    @GetMapping("/admin/orders")
    public String adminOrders(@RequestParam Optional<Long> complete, Model model){
        complete.ifPresent(oid -> orderService.completeOrder(oid));
        model.addAttribute("orderMap", orderService.getOrderMap());
        return "admin-orders";
    }

    @GetMapping("/admin/orders/details")
    public String adminOrderDetails(@RequestParam Optional<Long> order, Model model){
        Optional<Order> orderObj;
        if (order.isPresent()){
            orderObj = orderService.findOrder(order.get());
            orderObj.ifPresent(o -> {
                model.addAttribute("orderDetails", orderService.getOrderDetails(o));
                model.addAttribute("order", o);
            });
        }

        return "order-detail";
    }
}
