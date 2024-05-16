package tqs_project.DETICafe.DataInitializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tqs_project.DETICafe.model.Category;
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
}
