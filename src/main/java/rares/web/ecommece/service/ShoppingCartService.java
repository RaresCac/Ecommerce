package rares.web.ecommece.service;

import rares.web.ecommece.entities.Product;
import rares.web.ecommece.model.CheckoutDTO;

import java.util.Map;

public interface ShoppingCartService {
    void addProduct(Product product, int quantity);
    void updateProductQuantity(Product product, int quantity);
    void removeProduct(Product product);
    void checkout(CheckoutDTO checkoutDTO);

    Map<Product, Integer> getProductsInCart();
    double getTotalPrice();
}
