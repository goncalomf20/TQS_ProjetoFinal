package tqs_project.DETICafe.service;

import org.springframework.stereotype.Service;

import tqs_project.DETICafe.model.Product;

@Service
public interface ProductService {

    public Product findProductById(Long id);
    
}
