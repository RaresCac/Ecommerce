package rares.web.ecommece.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import rares.web.ecommece.entities.Product;
import rares.web.ecommece.model.AddToCartDTO;
import rares.web.ecommece.model.ProductSortEnum;
import rares.web.ecommece.service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@SessionAttributes("sort")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String viewProducts(@RequestParam Optional<Integer> page,
                               @RequestParam Optional<Integer> size,
                               @RequestParam Optional<Integer> sort,
                               @RequestParam Optional<String> search, Model model){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(4);
        int sortOption = sort.orElse(0);
        String searchInput = search.orElse("");

        ProductSortEnum sortEnum = ProductSortEnum.values()[sortOption];

        //Pages start from 0 but should be displayed starting from 1 on the web
        Page<Product> productPage = productService.getPaginatedProducts(searchInput,
                PageRequest.of(currentPage - 1, pageSize,
                        Sort.by(Sort.Direction.fromString(sortEnum.getSortDirection()), sortEnum.getProductAttrName())));

        model.addAttribute("productPage", productPage);

        //Generate the number of pages available to display on web
        int totalPages = productPage.getTotalPages();
        if (totalPages > 0){
            List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }
        return "products";
    }

    @GetMapping("/products/details")
    public String viewProductDetails(@RequestParam Long id, Model model){
        Product product = productService.findProduct(id).orElse(null);
        model.addAttribute("product", product);
        model.addAttribute("addToCartDTO", new AddToCartDTO());
        return "product-detail";
    }
}
