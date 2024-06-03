package tqs_project.deticafe.repository;

import org.springframework.stereotype.Repository;

import tqs_project.deticafe.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long>{

    Category findByName(String name);
     
}
