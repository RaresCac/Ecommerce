package rares.web.ecommece.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rares.web.ecommece.entities.Order;
import rares.web.ecommece.entities.OrderDetails;

import java.util.List;

public interface OrderDetailsRepository  extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findByOrder(Order order);
}
