package com.example.jwt.model.repositoris;


import com.example.jwt.model.entitats.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoriEmployee extends JpaRepository<Employee, Long> {

}
