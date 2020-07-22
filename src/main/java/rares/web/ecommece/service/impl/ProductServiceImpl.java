package rares.web.ecommece.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rares.web.ecommece.entities.Product;
import rares.web.ecommece.exception.ProductException;
import rares.web.ecommece.model.ProductDTO;
import rares.web.ecommece.repository.ProductRepository;
import rares.web.ecommece.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public void addNewProduct(ProductDTO productDTO) throws ProductException {
        if (productExists(productDTO.getName()))
            throw new ProductException("Product exists with same name");

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        productRepository.save(product);
    }

    public Optional<Product> findProduct(Long id){
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Page<Product> getPaginatedProducts(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public Page<Product> getPaginatedProducts(String searchName, Pageable pageable){
        return productRepository.findAllByNameContains(searchName, pageable);
    }

    private boolean productExists(String name){
        return productRepository.findByName(name).isPresent();
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
}
