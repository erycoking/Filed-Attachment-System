
package com.egerton.bugs.Model;

import com.egerton.bugs.Model.Enumerated.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@Component
@Entity(name = "student")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student {
    @Id
    @Column(name = "studentNumber", length = 30, nullable = false, unique = true)
    @Size(min = 12, max = 13, message = "Invalid student registration number")
    @Pattern(regexp = "[A-Za-z]{1,2}\\d{2}[/]\\d{5}[/]\\d{2}", message = "Please provide a valid registration number")
    private String studentNumber;

    @Column(name = "studentName", length = 30, nullable = false)
    @Size(min = 3, message = "Name should at least be 3 characters")
    @NotEmpty(message = "Please provide your full name.")
    private String fullName;

    @Column(name = "gender", nullable = false, columnDefinition = "enum('MALE','FEMALE')")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "password", length = 64)
    private String password;

    @Email(message = "Invalid email")
    @Column(name = "email", length = 50, nullable = false, unique = true)
    @NotEmpty(message = "Please provide your email")
    private String email;

    @Pattern(regexp = "[0|[+254]]+[7]\\d{8}", message = "please provide a valid phone number.")
    @Column(name = "phoneNumber", length = 15, nullable = false)
    @NotEmpty(message = "Please provide your mobile number")
    private String cellPhoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "faculty", nullable = false)
    private Faculty faculty;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department", nullable = false)
    private Department department;


//    @Size(min = 3, message = "Course name should at least be 3 characters")
//    @Column(name = "programmedescription", length = 100, nullable = false)
//    @NotEmpty(message = "Please provide your course name")
//    private String programmeDescription;

    @Column(name = "campusName", nullable = false, columnDefinition = "enum('Njoro_Main_Campus', 'Nakuru_Town_Campus')")
    @Enumerated(value = EnumType.STRING)
    private Campus campusName;

    @Column(name = "currentYear", nullable = false, columnDefinition = "enum('ONE', 'TWO', 'THREE', 'FOUR', 'FIVE')")
    @Enumerated(value = EnumType.STRING)
    private Year currentYear;

    @JsonIgnore
    @Column(name = "role", columnDefinition = "enum( 'ROLE_ADMIN','ROLE_STUDENT','ROLE_BUGS','ROLE_STAFF', 'ROLE_COMPANY', 'ROLE_COORDINATOR')")
    @Enumerated(value = EnumType.STRING)
    private Role role;


    @JsonBackReference
    @JsonIgnore
    @OneToMany(mappedBy = "studentApplication", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<App> app;

    @Column(name = "applied", columnDefinition = "enum('NO', 'YES')")
    @Enumerated(value = EnumType.STRING)
    private YesNo applied = YesNo.NO;

    @Column(name = "canApply", columnDefinition = "enum('NO', 'YES')")
    @Enumerated(value = EnumType.STRING)
    private YesNo canApply = YesNo.NO;

    @Column(name = "studentFeedback")
    private String studentFeedback;

    public Student() {
    }

    public Student(String studentNumber, String fullName, Gender gender, String password, String email, String cellPhoneNumber, Department department, Faculty faculty, String programmeDescription, Campus campusName, Year currentYear, Role role, Set<App> app, YesNo applied) {
        this.studentNumber = studentNumber;
        this.fullName = fullName;
        this.gender = gender;
        this.password = password;
        this.email = email;
        this.cellPhoneNumber = cellPhoneNumber;
        this.department = department;
        this.faculty = faculty;
//        this.programmeDescription = programmeDescription;
        this.campusName = campusName;
        this.currentYear = currentYear;
        this.role = role;
        this.app = app;
        this.applied = applied;
    }

    public Student(String studentNumber, String fullName, Gender gender, String password, String email, String cellPhoneNumber, Faculty faculty, Department department, Campus campusName, Year currentYear, Role role, Set<App> app, YesNo applied, YesNo canApply, String studentFeedback) {
        this.studentNumber = studentNumber;
        this.fullName = fullName;
        this.gender = gender;
        this.password = password;
        this.email = email;
        this.cellPhoneNumber = cellPhoneNumber;
        this.faculty = faculty;
        this.department = department;
        this.campusName = campusName;
        this.currentYear = currentYear;
        this.role = role;
        this.app = app;
        this.applied = applied;
        this.canApply = canApply;
        this.studentFeedback = studentFeedback;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    /*public String getProgrammeDescription() {
        return programmeDescription;
    }

    public void setProgrammeDescription(String programmeDescription) {
        this.programmeDescription = programmeDescription;
    }*/

    public Campus getCampusName() {
        return campusName;
    }

    public void setCampusName(Campus campusName) {
        this.campusName = campusName;
    }

    public Year getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Year currentYear) {
        this.currentYear = currentYear;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<App> getApp() {
        return app;
    }

    public void setApp(Set<App> app) {
        this.app = app;
    }

    public YesNo getApplied() {
        return applied;
    }

    public void setApplied(YesNo applied) {
        this.applied = applied;
    }

    public YesNo getCanApply() {
        return canApply;
    }

    public void setCanApply(YesNo canApply) {
        this.canApply = canApply;
    }

    public String getStudentFeedback() {
        return studentFeedback;
    }

    public void setStudentFeedback(String studentFeedback) {
        this.studentFeedback = studentFeedback;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        Set<Role> roles = new HashSet<>();

        roles.add(getRole());
        roles.forEach(role -> {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.toString()));
        });

        return simpleGrantedAuthorities;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentNumber='" + studentNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender=" + gender +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", cellPhoneNumber='" + cellPhoneNumber + '\'' +
                ", faculty=" + faculty.getFacultyName() +
                ", department=" + department.getDepartmentName() +
                ", campusName=" + campusName +
                ", currentYear=" + currentYear +
                ", role=" + role +
                ", app=" + app +
                ", applied=" + applied +
                ", canApply=" + canApply +
                ", studentFeedback='" + studentFeedback + '\'' +
                '}';
    }
}

