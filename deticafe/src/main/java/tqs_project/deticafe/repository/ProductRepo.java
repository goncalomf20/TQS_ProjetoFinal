package tqs_project.deticafe.repository;

import org.springframework.stereotype.Repository;

import tqs_project.deticafe.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Product findByName(String name);
    Product findByProductId(long productId);
    
}
