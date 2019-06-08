package com.egerton.bugs.controllers;


import com.egerton.bugs.Authentication.CustomSuccessHandler;
import com.egerton.bugs.Model.App;
import com.egerton.bugs.Model.Enumerated.Status;
import com.egerton.bugs.Model.Student;
import com.egerton.bugs.Model.Town;
import com.egerton.bugs.repositories.ApplicationRepository;
import com.egerton.bugs.service.ApplicationService;
import com.egerton.bugs.service.CustomUserDetailsService;
import com.egerton.bugs.service.StudentService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/student")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StudentController {

    private StudentService studentService;
    private ApplicationService applicationService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public StudentController(StudentService studentService,
                             ApplicationService applicationService,
                             BCryptPasswordEncoder passwordEncoder) {
        super();
        this.studentService = studentService;
        this.applicationService = applicationService;
        this.passwordEncoder = passwordEncoder;
    }

    //  /student/re
    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("student", new Student());
        mav.addObject("faculty", studentService.getFaculties());
        mav.setViewName("/student/register");
        return mav;

    }

    @PostMapping("/register")
    public ModelAndView createNewUser(@Valid @ModelAttribute Student student,
                                      BindingResult result,
                                      ModelAndView mav,
                                      RedirectAttributes attributes,
                                      @RequestParam Map<String, String> requestParams,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws ServletException, IOException {

        Student student1 = studentService.
                findStudentByStudentNumber(student.getStudentNumber());
        if (student1 != null) {
            attributes.addAttribute("faculty", studentService.getFaculties());
            result.rejectValue("studentNumber", "error.studentNumber",
                    "There is already a Student registered with the registration number provided.");
//            result.addError(new ObjectError("studentNumber",
//                    "There is already a Student registered with the registration number provided."));
        }
        if (result.hasErrors()) {
//            mav.addObject("student", new Student());
            mav.addObject("faculty", studentService.getFaculties());
            mav.setViewName("/student/register");
        } else {

            // Set password for the new user
            String password = requestParams.get("password");

            student.setPassword(passwordEncoder.encode(password));
            System.out.println("\n\n"+student+"\n\n");
            studentService.addStudent(student);
            mav.addObject("successMessage",
                    "Student has been registered successfully");

            mav.setViewName("redirect:/login");
        }
        return mav;
    }


    @GetMapping("/home")
    public ModelAndView home(ModelAndView mav,
                             Town town,
                             HttpServletRequest req) {

        HttpSession session = req.getSession();
        Student student = (Student)session.getAttribute("student");

        mav.addObject("user", student);
        mav.addObject("student", student.getFullName());
        mav.addObject("town", town);

        mav.setViewName("/student/home");

        return mav;
    }


    /*Pending applications*/
    @GetMapping("/pendingApplications")
    public ModelAndView pendingApplications(ModelAndView mav,
                                            HttpServletRequest req,
                                            @RequestParam(defaultValue = "0") int page) {

        HttpSession session = req.getSession();
        Student student = (Student)session.getAttribute("student");

        mav.addObject("apps", applicationService.getAllByCoordinatorapprovalAndRegno(Status.PENDING, student.getStudentNumber(), new PageRequest(page, 100)).getContent());
        mav.addObject("user", student);
        mav.addObject("student", student.getFullName());
        mav.setViewName("/student/application/pendingApplications");

        return mav;
    }


    /*Approved applications*/
    @GetMapping("/approvedApplications")
    public ModelAndView approvedApplications(ModelAndView mav,
                                            HttpServletRequest req,
                                             @RequestParam(defaultValue = "0") int page) {

        HttpSession session = req.getSession();
        Student student = (Student)session.getAttribute("student");

        mav.addObject("apps", applicationService.getAllByCoordinatorapprovalAndRegno(Status.ACCEPTED, student.getStudentNumber(), new PageRequest(page, 100)).getContent());
        mav.addObject("user", student);
        mav.addObject("student", student.getFullName());
        mav.setViewName("/student/application/approvedApplications");

        return mav;
    }

    /*Approved applications*/
    @GetMapping("/disapprovedApplications")
    public ModelAndView dispprovedApplications(ModelAndView mav,
                                            HttpServletRequest req,
                                             @RequestParam(defaultValue = "0") int page) {

        HttpSession session = req.getSession();
        Student student = (Student)session.getAttribute("student");

        mav.addObject("apps", applicationService.getAllByCoordinatorapprovalAndRegno(Status.REJECTED, student.getStudentNumber(), new PageRequest(page, 100)).getContent());
        mav.addObject("user", student);
        mav.addObject("student", student.getFullName());
        mav.setViewName("/student/application/disapprovedApplications");

        return mav;
    }

}
