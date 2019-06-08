
package com.egerton.bugs.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.egerton.bugs.Model.CustomUser;
import com.egerton.bugs.Model.Staff;
import com.egerton.bugs.Model.Student;

import javax.servlet.http.HttpServlet;


@Service

public class CustomUserDetailsService extends HttpServlet implements UserDetailsService {

    private CustomUser customUser;
    private StaffService staffService;
    private StudentService studentService;
    private CompanyService companyService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public CustomUserDetailsService(CustomUser customUser, StaffService staffService, StudentService studentService, CompanyService companyService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customUser = customUser;
        this.staffService = staffService;
        this.studentService = studentService;
        this.companyService = companyService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private Student student;
    private Staff staff;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = customUser.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        return user;

    }

    //find user given user id
    public void save(String userId, String password) {

        if ((staff = staffService.getCoordinator(userId)) != null) {
            staff.setPassword(bCryptPasswordEncoder.encode(password));
            staffService.addCoordinator(staff);
        } else if ((student = studentService.findStudentByStudentNumber(userId)) != null) {
            student.setPassword(bCryptPasswordEncoder.encode(password));
            studentService.addStudent(student);
        }
    }


}

