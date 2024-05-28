package tqs_project.DETICafe.DataInitializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tqs_project.DETICafe.model.Category;
import tqs_project.DETICafe.model.Product;
import tqs_project.DETICafe.repository.CategoryRepo;
import tqs_project.DETICafe.repository.ProductRepo;


import java.util.ArrayList;
import java.util.Arrays;
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
        categories.add(new Category("Pastry"));
        categories.add(new Category("Coffee"));
        categories.add(new Category("Promotions"));


        categoryRepository.saveAll(categories);
    }

    private void initializeProducts() {
<<<<<<< TF-40-Takeaway-Interface
=======
        // productRepository.deleteAll(); // para limpar a base de dados
>>>>>>> development

        List<Product> products = new ArrayList<>();

        List<String> ingredients1 = Arrays.asList("croissant", "ham", "cheese");
        List<String> ingredients2 = Arrays.asList("coffee", "water", "sugar");
        List<String> ingredients3 = Arrays.asList("cheese", "tomato sauce", "flour");
        List<String> ingredients4 = Arrays.asList("salmon", "rice", "avocado");
        List<String> ingredients5 = Arrays.asList("cheese", "tomato", "lettuce");
        List<String> ingredients6 = Arrays.asList("spaghetti", "tomato sauce", "meatballs");
        List<String> ingredients7 = Arrays.asList("lettuce", "mayo", "tomato");
        List<String> ingredients8 = Arrays.asList("water", "lemon", "sugar");
        List<String> ingredients9 = Arrays.asList("milk", "coffee", "foam");
        List<String> ingredients10 = Arrays.asList("fruit mix", "yogurt", "honey");
        
<<<<<<< TF-40-Takeaway-Interface
        products.add(new Product("Ham and Cheese Croissant", ingredients1, 3.99, categoryRepository.findByName("Pastry")));
=======
        products.add(new Product("Ham and Cheese Croissant", ingredients1, 3.99, categoryRepository.findByName("Pastery")));
>>>>>>> development
        products.add(new Product("Large Coffee", ingredients2, 1.99, categoryRepository.findByName("Coffee")));
        products.add(new Product("Pizza", ingredients3, 5.99, categoryRepository.findByName("Foods")));
        products.add(new Product("Salmon Sushi", ingredients4, 4.99, categoryRepository.findByName("Foods")));
        products.add(new Product("Cheeseburger", ingredients5, 3.99, categoryRepository.findByName("Foods")));
        products.add(new Product("Spaghetti and Meatballs", ingredients6, 6.99, categoryRepository.findByName("Foods")));
        products.add(new Product("Tuna Sandwich", ingredients7, 4.99, categoryRepository.findByName("Foods")));

        products.add(new Product("Lemonade", ingredients8, 2.50, categoryRepository.findByName("Drinks")));
        products.add(new Product("Cappuccino", ingredients9, 2.99, categoryRepository.findByName("Coffee")));
        products.add(new Product("Fruit Smoothie", ingredients10, 3.50, categoryRepository.findByName("Drinks")));

        List<String> ingredients11 = Arrays.asList("chocolate", "cream", "sugar");
        List<String> ingredients12 = Arrays.asList("peanuts", "chocolate", "honey");
        products.add(new Product("Chocolate Cake", ingredients11, 4.50, categoryRepository.findByName("Desserts")));
        products.add(new Product("Peanut Butter Bars", ingredients12, 2.99, categoryRepository.findByName("Snacks")));

        List<String> ingredients13 = Arrays.asList("croissant", "butter", "sugar");
        List<String> ingredients14 = Arrays.asList("flour", "sugar", "butter");
<<<<<<< TF-40-Takeaway-Interface
        products.add(new Product("Butter Croissant", ingredients13, 2.99, categoryRepository.findByName("Pastry")));
=======
        products.add(new Product("Butter Croissant", ingredients13, 2.99, categoryRepository.findByName("Pastery")));
>>>>>>> development
        products.add(new Product("Shortbread Cookies", ingredients14, 3.99, categoryRepository.findByName("Snacks")));

        List<String> ingredients15 = Arrays.asList("cheese", "ham", "baguette");
        List<String> ingredients16 = Arrays.asList("turkey", "lettuce", "whole grain bread");
        products.add(new Product("Ham and Cheese Baguette", ingredients15, 4.99, categoryRepository.findByName("Foods")));
        products.add(new Product("Turkey Sandwich", ingredients16, 4.50, categoryRepository.findByName("Foods")));

        List<String> ingredients17 = Arrays.asList("espresso", "milk", "foam");
        List<String> ingredients18 = Arrays.asList("espresso", "water");
        products.add(new Product("Latte", ingredients17, 3.99, categoryRepository.findByName("Coffee")));
        products.add(new Product("Americano", ingredients18, 2.50, categoryRepository.findByName("Coffee")));

        List<String> ingredients19 = Arrays.asList("water", "mint", "lemon");
        List<String> ingredients20 = Arrays.asList("sparkling water", "lime", "mint");
        products.add(new Product("Mint Lemonade", ingredients19, 3.00, categoryRepository.findByName("Drinks")));
        products.add(new Product("Sparkling Lime Water", ingredients20, 2.75, categoryRepository.findByName("Drinks")));

        List<String> ingredients21 = Arrays.asList("espresso", "chocolate", "milk");
        List<String> ingredients22 = Arrays.asList("espresso", "milk", "caramel");
        products.add(new Product("Mocha", ingredients21, 3.50, categoryRepository.findByName("Coffee")));
        products.add(new Product("Caramel Macchiato", ingredients22, 3.99, categoryRepository.findByName("Coffee")));

        List<String> ingredients23 = Arrays.asList("flour", "chocolate chips", "butter");
        List<String> ingredients24 = Arrays.asList("flour", "sugar", "butter", "cinnamon");
        products.add(new Product("Chocolate Chip Cookies", ingredients23, 2.99, categoryRepository.findByName("Snacks")));
<<<<<<< TF-40-Takeaway-Interface
        products.add(new Product("Cinnamon Rolls", ingredients24, 3.99, categoryRepository.findByName("Pastry")));
=======
        products.add(new Product("Cinnamon Rolls", ingredients24, 3.99, categoryRepository.findByName("Pastery")));
>>>>>>> development

        List<String> ingredients25 = Arrays.asList("chocolate");
        products.add(new Product("Chocolate mousse", ingredients25, 1.50, categoryRepository.findByName("Desserts")));

        productRepository.saveAll(products);
    }
}