package com.egerton.bugs.repositories;

import com.egerton.bugs.Model.Department;
import com.egerton.bugs.Model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    List<Department> findByFaculty(Faculty faculty);

}
