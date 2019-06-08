
package com.egerton.bugs.service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.egerton.bugs.Model.Department;
import com.egerton.bugs.Model.Enumerated.Role;
import com.egerton.bugs.Model.Enumerated.Year;
import com.egerton.bugs.Model.Faculty;
import com.egerton.bugs.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.egerton.bugs.Model.Student;

@Service
public class StudentService {

    private StudentRepository studentRepo;
    private FacultyRepository facultyRepository;
    private DepartmentRepository departmentRepository;
    private CompanyRepository companyRepository;
    private TownRepository townRepository;

    public StudentService(StudentRepository studentRepo, FacultyRepository facultyRepository, DepartmentRepository departmentRepository, CompanyRepository companyRepository, TownRepository townRepository) {
        this.studentRepo = studentRepo;
        this.facultyRepository = facultyRepository;
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
        this.townRepository = townRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public Page<Student> getAllStudentsPageable(Pageable pageable) {
        return studentRepo.findAll(pageable);
    }

    //find student from database using student number
    public Student findStudentByStudentNumber(String id) {
        return studentRepo.findByStudentNumber(id);
    }


    //Register student
    public void addStudent(Student student) {
        student.setRole(Role.ROLE_STUDENT);
        //student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));

        studentRepo.save(student);
    }

    public List<Faculty> getFaculties(){
        return facultyRepository.findAll();
    }

    public List<Department> getDepartments(){
        return departmentRepository.findAll();
    }

    public Faculty findByFacultyName(String name){
        return facultyRepository.findByFacultyName(name);
    }

    public  List<Department> findDepartmentsByFaculty(int id){
        return departmentRepository.findByFaculty(facultyRepository.findOne(id));
    }

    public Faculty getFaculty(int id){
        return facultyRepository.findOne(id);
    }

    public Department getDepartment(int id){
        return  departmentRepository.findOne(id);
    }


    public void updateStudent(Student student) {
        studentRepo.save(student);
    }

    public void deleteStudent(String id) {
        studentRepo.deleteByStudentNumber(id);
    }

    //find all students given department
    public List<Student> getByDepartment(int departmentId) {
        return studentRepo.findByDepartmentId(departmentId);
    }

    //find all students given department
    public Page<Student> getStudentsByFaculty(Faculty faculty, Pageable pageable) {
        return studentRepo.findAllByFaculty(faculty, pageable);
    }
    //find all students given department
    public Page<Student> getStudentsByFacultyId(int facultyId, Pageable pageable) {
        return studentRepo.findAllByFacultyId(facultyId, pageable);
    }

    //find all students given department
    public List<Student> getByDepartmentPageable(int department, Pageable pageable) {
        return studentRepo.findAllByDepartmentId(department, pageable).getContent();
    }

    //find all students given year
    public Page<Student> getStudenByCurrentYear(Year year, Pageable pageable) {
        return studentRepo.findAllByCurrentYear(year, pageable);
    }

    //find all students given gender
    public Page<Student> getAllByGender(String gender, Pageable pageable) {
        return studentRepo.findAllByGender(gender, pageable);
    }

    //find all students given name
    public Page<Student> getAllByFullName(String name, Pageable pageable) {
        return studentRepo.findAllByFullNameContains(name, pageable);
    }

    //find all students given year
    public Page<Student> getAllByCampusName(String name, Pageable pageable) {
        return studentRepo.findAllByCampusNameLike(name, pageable);
    }

    //find all students given year
    public Page<Student> getAllByStudentNumber(String regno, Pageable pageable) {
        return studentRepo.findAllByStudentNumber(regno, pageable);
    }
}

