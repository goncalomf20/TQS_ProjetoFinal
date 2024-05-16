package tqs_project.DETICafe.repository;

import org.springframework.stereotype.Repository;

import tqs_project.DETICafe.model.OrderDetails;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long>{
    
}
