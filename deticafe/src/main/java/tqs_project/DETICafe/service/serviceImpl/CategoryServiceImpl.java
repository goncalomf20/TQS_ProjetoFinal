package tqs_project.DETICafe.service.serviceImpl;

import java.util.List;
import org.springframework.stereotype.Service;
import tqs_project.DETICafe.service.CategoryService;
import tqs_project.DETICafe.model.Category;
import tqs_project.DETICafe.repository.CategoryRepo;


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

    
    
}
