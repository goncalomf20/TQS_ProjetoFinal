package tqs_project.deticafe.data.initializer;

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

    // Constants for categories
    private static final String DRINKS = "Drinks";
    private static final String FOODS = "Foods";
    private static final String SNACKS = "Snacks";
    private static final String DESSERTS = "Desserts";
    private static final String PASTRY = "Pastry";
    private static final String COFFEE = "Coffee";
    private static final String PROMOTIONS = "Promotions";

    // Constants for ingredients
    private static final String CHEESE = "cheese";
    private static final String WATER = "water";
    private static final String SUGAR = "sugar";
    private static final String FLOUR = "flour";
    private static final String LETTUCE = "lettuce";
    private static final String CHOCOLATE = "chocolate";
    private static final String BUTTER = "butter";
    private static final String ESPRESSO = "espresso";

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
        List<String> categoryNames = Arrays.asList(DRINKS, FOODS, SNACKS, DESSERTS, PASTRY, COFFEE, PROMOTIONS);
        List<Category> categories = new ArrayList<>();

        for (String name : categoryNames) {
            categories.add(new Category(name));
        }

        categoryRepository.saveAll(categories);
    }

    private void initializeProducts() {
        List<ProductData> productDataList = Arrays.asList(
            new ProductData("Ham and Cheese Croissant", Arrays.asList("croissant", "ham", CHEESE), 3.99, PASTRY),
            new ProductData("Large Coffee", Arrays.asList("coffee", WATER, SUGAR), 1.99, COFFEE),
            new ProductData("Pizza", Arrays.asList(CHEESE, "tomato sauce", FLOUR), 5.99, FOODS),
            new ProductData("Salmon Sushi", Arrays.asList("salmon", "rice", "avocado"), 4.99, FOODS),
            new ProductData("Cheeseburger", Arrays.asList(CHEESE, "tomato", LETTUCE), 3.99, FOODS),
            new ProductData("Spaghetti and Meatballs", Arrays.asList("spaghetti", "tomato sauce", "meatballs"), 6.99, FOODS),
            new ProductData("Tuna Sandwich", Arrays.asList(LETTUCE, "mayo", "tomato"), 4.99, FOODS),
            new ProductData("Lemonade", Arrays.asList(WATER, "lemon", SUGAR), 2.50, DRINKS),
            new ProductData("Cappuccino", Arrays.asList("milk", "coffee", "foam"), 2.99, COFFEE),
            new ProductData("Fruit Smoothie", Arrays.asList("fruit mix", "yogurt", "honey"), 3.50, DRINKS),
            new ProductData("Chocolate Cake", Arrays.asList(CHOCOLATE, "cream", SUGAR), 4.50, DESSERTS),
            new ProductData("Peanut Butter Bars", Arrays.asList("peanuts", CHOCOLATE, "honey"), 2.99, SNACKS),
            new ProductData("Butter Croissant", Arrays.asList("croissant", BUTTER, SUGAR), 2.99, PASTRY),
            new ProductData("Shortbread Cookies", Arrays.asList(FLOUR, SUGAR, BUTTER), 3.99, SNACKS),
            new ProductData("Ham and Cheese Baguette", Arrays.asList(CHEESE, "ham", "baguette"), 4.99, FOODS),
            new ProductData("Turkey Sandwich", Arrays.asList("turkey", LETTUCE, "whole grain bread"), 4.50, FOODS),
            new ProductData("Latte", Arrays.asList(ESPRESSO, "milk", "foam"), 3.99, COFFEE),
            new ProductData("Americano", Arrays.asList(ESPRESSO, WATER), 2.50, COFFEE),
            new ProductData("Mint Lemonade", Arrays.asList(WATER, "mint", "lemon"), 3.00, DRINKS),
            new ProductData("Sparkling Lime Water", Arrays.asList("sparkling water", "lime", "mint"), 2.75, DRINKS),
            new ProductData("Mocha", Arrays.asList(ESPRESSO, CHOCOLATE, "milk"), 3.50, COFFEE),
            new ProductData("Caramel Macchiato", Arrays.asList(ESPRESSO, "milk", "caramel"), 3.99, COFFEE),
            new ProductData("Chocolate Chip Cookies", Arrays.asList(FLOUR, "chocolate chips", BUTTER), 2.99, SNACKS),
            new ProductData("Cinnamon Rolls", Arrays.asList(FLOUR, SUGAR, BUTTER, "cinnamon"), 3.99, PASTRY),
            new ProductData("Chocolate Mousse", Arrays.asList(CHOCOLATE), 1.50, DESSERTS)
        );

        List<Product> products = new ArrayList<>();
        for (ProductData data : productDataList) {
            Category category = categoryRepository.findByName(data.categoryName);
            products.add(new Product(data.name, data.ingredients, data.price, category));
        }

        productRepository.saveAll(products);
    }

    private static class ProductData {
        String name;
        List<String> ingredients;
        double price;
        String categoryName;

        ProductData(String name, List<String> ingredients, double price, String categoryName) {
            this.name = name;
            this.ingredients = ingredients;
            this.price = price;
            this.categoryName = categoryName;
        }
    }
}
