package tqs_project.deticafe.repository;

import org.springframework.stereotype.Repository;

import tqs_project.deticafe.model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long>{

    Customer findByName(String name);
    
}
