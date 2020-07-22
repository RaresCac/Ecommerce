package rares.web.ecommece.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import rares.web.ecommece.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);
    List<Order> findByCompleted(Boolean completed, Sort sort);
    List<Order> findByCompletedOrderByIdAsc(Boolean completed);
}
