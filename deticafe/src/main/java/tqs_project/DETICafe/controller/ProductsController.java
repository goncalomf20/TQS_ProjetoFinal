package tqs_project.deticafe.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tqs_project.deticafe.model.Product;
import tqs_project.deticafe.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductsController {

    private final ProductService productService;


    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestParam String productName, @RequestParam List<String> productDescription, @RequestParam double productPrice, @RequestParam String productCategory) {
        try {
            Product newProduct = productService.addProduct(productName, productDescription, productPrice, productCategory);
            return ResponseEntity.ok(newProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}

