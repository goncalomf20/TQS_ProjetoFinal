package tqs_project.deticafe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tqs_project.deticafe.model.Category;
import tqs_project.deticafe.service.CategoryService;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class CategoryController {

    private final CategoryService categoryService;


    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/getAllCategories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/addCategory")
    public ResponseEntity<Category> addCategory(@RequestParam String categoryName) {
        if(categoryName.isEmpty()){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); 
        }
        Category newCategory = categoryService.addCategory(categoryName);
        return ResponseEntity.ok(newCategory);
    }


}
   