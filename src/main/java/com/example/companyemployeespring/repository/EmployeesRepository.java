package com.example.companyemployeespring.repository;

import com.example.companyemployeespring.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeesRepository extends JpaRepository<Employees, Integer> {
}
