package tqs_project.DETICafe.DataInitializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tqs_project.DETICafe.model.Category;
import tqs_project.DETICafe.model.Product;
import tqs_project.DETICafe.repository.CategoryRepo;
import tqs_project.DETICafe.repository.ProductRepo;


import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepo categoryRepository;
    private final ProductRepo productRepository;

    public DataInitializer(CategoryRepo categoryRepository, ProductRepo productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            initializeCategories();
        }

        if (productRepository.count() == 0) {
            initializeProducts();
        }
    }

    private void initializeCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Drinks"));
        categories.add(new Category("Foods"));
        categories.add(new Category("Snacks"));
        categories.add(new Category("Desserts"));
        categories.add(new Category("Pastery"));
        categories.add(new Category("Coffee"));
        categories.add(new Category("Promotions"));


        categoryRepository.saveAll(categories);
    }

    private void initializeProducts(){
        List<Product> products = new ArrayList<>();
        List<String> ingredients1 = new ArrayList<>();
        ingredients1.add("croissant");
        ingredients1.add("ham");
        ingredients1.add("cheese");
        List<String> ingredients2 = new ArrayList<>();
        ingredients1.add("coffee");
        ingredients1.add("water");
        ingredients1.add("sugar");
        products.add(new Product("Ham and Cheese Croissant", ingredients1, 3.99, categoryRepository.findByName("Pastery")));
        products.add(new Product("Large Coffee", ingredients2, 1.99, categoryRepository.findByName("Coffee")));

        productRepository.saveAll(products);
    }
}
