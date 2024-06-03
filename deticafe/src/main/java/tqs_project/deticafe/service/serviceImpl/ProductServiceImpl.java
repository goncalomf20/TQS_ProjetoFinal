package tqs_project.deticafe.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import tqs_project.deticafe.model.Category;
import tqs_project.deticafe.model.Product;
import tqs_project.deticafe.repository.CategoryRepo;
import tqs_project.deticafe.repository.ProductRepo;
import tqs_project.deticafe.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
    
    ProductRepo productRepo;
    CategoryRepo categoryRepo;


    public ProductServiceImpl(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
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
    public Product addProduct(String name, List<String> description, double price, String categoryName) {
        Category category = categoryRepo.findByName(categoryName);
        if (category == null) {
            throw new IllegalArgumentException("Category not found");
        }
        Product product = new Product(name, description, price, category);
        productRepo.save(product);
        return product;
        
    }

}  
