package tqs_project.DETICafe.service;

import java.util.List;
import org.springframework.stereotype.Service;

import tqs_project.DETICafe.model.Product;

@Service
public interface ProductService {

    public Product getProductById(int id);
    public Product getProductByName(String name);
    public List<Product> getAllProducts();
    
}
