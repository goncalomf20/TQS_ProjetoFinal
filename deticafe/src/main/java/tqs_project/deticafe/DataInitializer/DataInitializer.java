package tqs_project.deticafe.DataInitializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tqs_project.deticafe.model.Category;
import tqs_project.deticafe.model.Product;
import tqs_project.deticafe.repository.CategoryRepo;
import tqs_project.deticafe.repository.ProductRepo;

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
        List<String> categoryNames = Arrays.asList("Drinks", "Foods", "Snacks", "Desserts", "Pastry", "Coffee", "Promotions");
        List<Category> categories = new ArrayList<>();

        for (String name : categoryNames) {
            categories.add(new Category(name));
        }

        categoryRepository.saveAll(categories);
    }

    private void initializeProducts() {
        List<Product> products = new ArrayList<>();

        List<Category> categories = categoryRepository.findAll();

        Category pastry = getCategoryByName(categories, "Pastry");
        Category coffee = getCategoryByName(categories, "Coffee");
        Category foods = getCategoryByName(categories, "Foods");
        Category drinks = getCategoryByName(categories, "Drinks");
        Category desserts = getCategoryByName(categories, "Desserts");
        Category snacks = getCategoryByName(categories, "Snacks");

        products.add(new Product("Ham and Cheese Croissant", Arrays.asList("croissant", "ham", "cheese"), 3.99, pastry));
        products.add(new Product("Large Coffee", Arrays.asList("coffee", "water", "sugar"), 1.99, coffee));
        products.add(new Product("Pizza", Arrays.asList("cheese", "tomato sauce", "flour"), 5.99, foods));
        products.add(new Product("Salmon Sushi", Arrays.asList("salmon", "rice", "avocado"), 4.99, foods));
        products.add(new Product("Cheeseburger", Arrays.asList("cheese", "tomato", "lettuce"), 3.99, foods));
        products.add(new Product("Spaghetti and Meatballs", Arrays.asList("spaghetti", "tomato sauce", "meatballs"), 6.99, foods));
        products.add(new Product("Tuna Sandwich", Arrays.asList("lettuce", "mayo", "tomato"), 4.99, foods));
        products.add(new Product("Lemonade", Arrays.asList("water", "lemon", "sugar"), 2.50, drinks));
        products.add(new Product("Cappuccino", Arrays.asList("milk", "coffee", "foam"), 2.99, coffee));
        products.add(new Product("Fruit Smoothie", Arrays.asList("fruit mix", "yogurt", "honey"), 3.50, drinks));
        products.add(new Product("Chocolate Cake", Arrays.asList("chocolate", "cream", "sugar"), 4.50, desserts));
        products.add(new Product("Peanut Butter Bars", Arrays.asList("peanuts", "chocolate", "honey"), 2.99, snacks));
        products.add(new Product("Butter Croissant", Arrays.asList("croissant", "butter", "sugar"), 2.99, pastry));
        products.add(new Product("Shortbread Cookies", Arrays.asList("flour", "sugar", "butter"), 3.99, snacks));
        products.add(new Product("Ham and Cheese Baguette", Arrays.asList("cheese", "ham", "baguette"), 4.99, foods));
        products.add(new Product("Turkey Sandwich", Arrays.asList("turkey", "lettuce", "whole grain bread"), 4.50, foods));
        products.add(new Product("Latte", Arrays.asList("espresso", "milk", "foam"), 3.99, coffee));
        products.add(new Product("Americano", Arrays.asList("espresso", "water"), 2.50, coffee));
        products.add(new Product("Mint Lemonade", Arrays.asList("water", "mint", "lemon"), 3.00, drinks));
        products.add(new Product("Sparkling Lime Water", Arrays.asList("sparkling water", "lime", "mint"), 2.75, drinks));
        products.add(new Product("Mocha", Arrays.asList("espresso", "chocolate", "milk"), 3.50, coffee));
        products.add(new Product("Caramel Macchiato", Arrays.asList("espresso", "milk", "caramel"), 3.99, coffee));
        products.add(new Product("Chocolate Chip Cookies", Arrays.asList("flour", "chocolate chips", "butter"), 2.99, snacks));
        products.add(new Product("Cinnamon Rolls", Arrays.asList("flour", "sugar", "butter", "cinnamon"), 3.99, pastry));
        products.add(new Product("Chocolate Mousse", Arrays.asList("chocolate"), 1.50, desserts));

        productRepository.saveAll(products);
    }

    private Category getCategoryByName(List<Category> categories, String name) {
        return categories.stream()
                .filter(category -> category.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + name));
    }
}
