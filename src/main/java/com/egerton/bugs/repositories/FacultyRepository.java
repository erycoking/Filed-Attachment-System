package com.egerton.bugs.repositories;

import com.egerton.bugs.Model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    Faculty findByFacultyName(String name);
}
