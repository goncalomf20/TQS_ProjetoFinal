package tqs_project.deticafe.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import tqs_project.deticafe.model.Category;
import tqs_project.deticafe.repository.CategoryRepo;
import tqs_project.deticafe.service.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category addCategory(String categoryName) {
        if (categoryName == null || categoryName.isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        Category category = new Category(categoryName);
        categoryRepo.save(category);
        return category;
    }

    @Override
    public Category save(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        Category category = new Category(categoryName);
        return categoryRepo.save(category);
    }
}


