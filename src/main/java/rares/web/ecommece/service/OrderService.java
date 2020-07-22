package rares.web.ecommece.service;

import rares.web.ecommece.entities.Order;
import rares.web.ecommece.entities.OrderDetails;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {
    Optional<Order> findOrder(Long id);
    void completeOrder(Long orderId);

    List<Order> getOpenOrders();
    List<OrderDetails> getOrderDetails(Order order);
    Map<Order, List<OrderDetails>> getOrderMap();
}
