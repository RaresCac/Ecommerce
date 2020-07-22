package rares.web.ecommece.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rares.web.ecommece.entities.Order;
import rares.web.ecommece.entities.OrderDetails;
import rares.web.ecommece.repository.OrderDetailsRepository;
import rares.web.ecommece.repository.OrderRepository;
import rares.web.ecommece.service.OrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    public Optional<Order> findOrder(Long id){
        return orderRepository.findById(id);
    }

    public List<Order> getOpenOrders() {
        //Returns ordered list by id
        //return orderRepository.findByCompleted(false, Sort.by(Sort.Direction.ASC, "id"));
        return orderRepository.findByCompletedOrderByIdAsc(false);
    }

    public List<OrderDetails> getOrderDetails(Order order){
        return orderDetailsRepository.findByOrder(order);
    }

    public Map<Order, List<OrderDetails>> getOrderMap(){
        Map<Order, List<OrderDetails>> map = new HashMap<>();
        for (Order order : getOpenOrders()){
            map.put(order, getOrderDetails(order));
        }
        return map;
    }

    public void completeOrder(Long orderId){
        Optional<Order> order = orderRepository.findById(orderId);
        order.ifPresent((Order o) -> {
            o.setCompleted(true);
            orderRepository.save(o);
        });
    }
}
