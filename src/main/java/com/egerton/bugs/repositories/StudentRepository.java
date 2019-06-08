
package com.egerton.bugs.repositories;

import java.util.List;

import com.egerton.bugs.Model.Department;
import com.egerton.bugs.Model.Enumerated.Year;
import com.egerton.bugs.Model.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egerton.bugs.Model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Student findByEmail(String email);

    Student findByStudentNumber(String number);

    void deleteByStudentNumber(String id);

    //find all students based on department
    Page<Student> findAllByDepartmentId(int id, Pageable pageable);

    List<Student> findByDepartmentId(int id);

    //find all students based on department
    Page<Student> findAllByFaculty(Faculty faculty, Pageable pageable);

    //find all students based on department
    Page<Student> findAllByFacultyId(int facultyId, Pageable pageable);

    List<Student> findAllByFaculty(Faculty faculty);

    //find all students by name
    Page<Student> findAllByFullNameContains(String name, Pageable pageable);

    //find all students by year
    Page<Student> findAllByCurrentYear(Year year, Pageable pageable);

    //find all students by gender
    Page<Student> findAllByGender(String gender, Pageable pageable);

    //find all students by campus name
    Page<Student> findAllByCampusNameLike(String name, Pageable pageable);

    //find all students by Regno
    Page<Student> findAllByStudentNumber(String regno, Pageable pageable);


}

