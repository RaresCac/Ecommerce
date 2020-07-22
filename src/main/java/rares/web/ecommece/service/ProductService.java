package rares.web.ecommece.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rares.web.ecommece.entities.Product;
import rares.web.ecommece.model.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    void addNewProduct(ProductDTO productDTO);
    Optional<Product> findProduct(Long id);
    List<Product> getAllProducts();
    Page<Product> getPaginatedProducts(Pageable pageable);
    Page<Product> getPaginatedProducts(String searchName, Pageable pageable);
}
