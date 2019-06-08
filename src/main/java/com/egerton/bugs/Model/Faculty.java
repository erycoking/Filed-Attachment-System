package com.egerton.bugs.Model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "faculties")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Size(min = 3, message = "course should at least be 3 characters")
    @NotEmpty(message = "Please provide your course code")
    @Column(name = "facultyName", nullable = false, length = 100)
    private String facultyName;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Department> department;

    @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY)
    private List<Student> student;

    public Faculty() {
    }

    public Faculty(String facultyName, List<Department> department) {
        this.facultyName = facultyName;
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public List<Department> getDepartment() {
        return department;
    }

    public void setDepartment(List<Department> department) {
        this.department = department;
    }

    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", facultyName='" + facultyName + '\'' +
                ", department=" + department +
                ", student=" + student +
                '}';
    }
}
