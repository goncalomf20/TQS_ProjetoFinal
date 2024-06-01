package tqs_project.deticafe.service.serviceImpl;

import java.util.List;
import org.springframework.stereotype.Service;
import tqs_project.deticafe.service.CategoryService;
import tqs_project.deticafe.model.Category;
import tqs_project.deticafe.repository.CategoryRepo;


@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryRepo categoryRepo;

    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category addCategory(String categoryName) {
        Category category = new Category(categoryName);
        categoryRepo.save(category);
        return category;
    }

}


