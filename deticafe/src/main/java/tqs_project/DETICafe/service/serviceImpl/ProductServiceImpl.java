package tqs_project.DETICafe.service.serviceImpl;

import java.util.List;
import org.springframework.stereotype.Service;

import tqs_project.DETICafe.model.Product;
import tqs_project.DETICafe.repository.ProductRepo;
import tqs_project.DETICafe.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
    
    ProductRepo productRepo;


    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }


    @Override
    public Product getProductById(int id) {
        Long idLong = Long.valueOf(id);
        return productRepo.findByProductId(idLong);
    }

    @Override
    public Product getProductByName(String name) {
        return productRepo.findByName(name);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

}  
