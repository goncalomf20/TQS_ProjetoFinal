package tqs_project.deticafe.service.serviceImpl;

import java.util.List;
import org.springframework.stereotype.Service;

import tqs_project.deticafe.model.Product;
import tqs_project.deticafe.repository.ProductRepo;
import tqs_project.deticafe.service.ProductService;

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

    @Override
    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

}  
