package rares.web.ecommece.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rares.web.ecommece.entities.Product;


import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    Page<Product> findAllByName(String name, Pageable pageable);
    Page<Product> findAllByNameContains(String name, Pageable pageable);
}
