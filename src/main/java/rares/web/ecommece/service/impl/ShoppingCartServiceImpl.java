package rares.web.ecommece.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import rares.web.ecommece.entities.Order;
import rares.web.ecommece.entities.OrderDetails;
import rares.web.ecommece.entities.Product;
import rares.web.ecommece.exception.QuantityException;
import rares.web.ecommece.model.CheckoutDTO;
import rares.web.ecommece.repository.OrderDetailsRepository;
import rares.web.ecommece.repository.OrderRepository;
import rares.web.ecommece.repository.ProductRepository;
import rares.web.ecommece.service.ShoppingCartService;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    //Represents the product cart
    private final Map<Product, Integer> productCart = new HashMap<>();

    @Autowired
    public ShoppingCartServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public void addProduct(Product product, int quantity){
        if (productCart.containsKey(product)){
            productCart.replace(product, productCart.get(product) + quantity);
        } else {
            productCart.put(product, quantity);
        }
    }

    public void updateProductQuantity(Product product, int quantity) throws QuantityException {
        if (quantity <= 0 || quantity > 10)
            throw new QuantityException();
        if (productCart.containsKey(product)){
            productCart.replace(product, quantity);
        }
    }

    public void removeProduct(Product product) {
        productCart.remove(product);
    }

    public void checkout(CheckoutDTO checkoutDTO) {
        Order order = new Order();
        order.setCompleted(false);
        order.setCustomerName(checkoutDTO.getName());
        order.setCustomerAddress(checkoutDTO.getFullAddress());
        order.setCustomerEmail(checkoutDTO.getEmail());
        order.setDate(new Date());
        order.setAmount(getTotalPrice());

        List<OrderDetails> orderDetailsList = new ArrayList<>();

        //Create an order detail object for each product in the order
        for (Map.Entry<Product, Integer> entry: productCart.entrySet()){
            OrderDetails od = new OrderDetails();
            od.setOrder(order);
            od.setProduct(entry.getKey());
            od.setQuantity(entry.getValue());
            orderDetailsList.add(od);
        }

        //Save orders and details in the respective tables in the DB
        orderRepository.save(order);
        orderDetailsRepository.saveAll(orderDetailsList);

        //Clear the basket
        productCart.clear();
    }

    public Map<Product, Integer> getProductsInCart() {
        return productCart;
    }

    public double getTotalPrice() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry: productCart.entrySet()){
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
}
