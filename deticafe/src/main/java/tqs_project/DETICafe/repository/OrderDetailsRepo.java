package tqs_project.deticafe.repository;

import org.springframework.stereotype.Repository;

import tqs_project.deticafe.model.OrderDetails;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long>{
    
}
