package tqs_project.deticafe.service;

import java.util.List;
import org.springframework.stereotype.Service;

import tqs_project.deticafe.model.Product;

@Service
public interface ProductService {

    public Product getProductById(int id);
    public Product getProductByName(String name);
    public List<Product> getAllProducts();
    public Product addProduct(String productName, List<String> productDescription, double productPrice, String productCategory);
    
}
