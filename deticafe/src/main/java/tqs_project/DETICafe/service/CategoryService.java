package tqs_project.DETICafe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tqs_project.DETICafe.model.Category;

@Service
public interface CategoryService {

    public List<Category> getAllCategories();
}