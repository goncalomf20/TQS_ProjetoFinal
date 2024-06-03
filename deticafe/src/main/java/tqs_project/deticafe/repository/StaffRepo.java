package tqs_project.deticafe.repository;

import org.springframework.stereotype.Repository;

import tqs_project.deticafe.model.Staff;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Long>{

    Staff findByName(String name);
    
}
