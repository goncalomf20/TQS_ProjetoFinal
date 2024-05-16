package tqs_project.DETICafe.repository;

import org.springframework.stereotype.Repository;

import tqs_project.DETICafe.model.Staff;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Long>{
    
}
