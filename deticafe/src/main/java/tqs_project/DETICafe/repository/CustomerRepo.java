package tqs_project.DETICafe.repository;

import org.springframework.stereotype.Repository;

import tqs_project.DETICafe.model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long>{
    
}
