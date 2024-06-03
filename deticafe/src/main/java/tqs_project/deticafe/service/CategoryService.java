package tqs_project.deticafe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tqs_project.deticafe.model.Category;

@Service
public interface CategoryService {

    public List<Category> getAllCategories();

    public Category addCategory(String categoryName);

    public Category save(String category);
}

