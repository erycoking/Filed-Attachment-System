
package com.egerton.bugs.controllers;


import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.egerton.bugs.Authentication.CustomSuccessHandler;
import com.egerton.bugs.components.MailContent;
import com.egerton.bugs.Model.PasswordResetToken;
import com.egerton.bugs.components.PdfContent;
import com.egerton.bugs.components.PdfGenerator;
import com.egerton.bugs.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import com.egerton.bugs.Model.Student;
import com.egerton.bugs.Model.Town;
import com.egerton.bugs.Model.Staff;

import javax.activation.DataSource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/password")
public class PasswordController {


    private CompanyService companyService;
    private StaffService staffService;
    private StudentService studentService;
    private CustomUserDetailsService customUserDetailsService;
    private AuthenticationManager authenticationManager;
    private CustomSuccessHandler successHandler;

    //@Autowired
    //private EmailService emailService;
    @Autowired
    private MailContent mailContent;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private PdfContent pdfContent;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public PasswordController(CompanyService companyService, StaffService staffService,
                              StudentService studentService, CustomUserDetailsService customUserDetailsService,
                              AuthenticationManager authenticationManager, CustomSuccessHandler successHandler) {
        this.companyService = companyService;
        this.staffService = staffService;
        this.studentService = studentService;
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.successHandler = successHandler;
    }

    //Get set password view
    @GetMapping("/passwordReset")
    public ModelAndView getPassword(ModelAndView mav,
                                    HttpServletRequest request) {

        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        mav.addObject("user", staff);
        mav.addObject("userId", staff.getPayrollId());

        mav.setViewName("password/password");
        return mav;

    }


    // set password
    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public ModelAndView setPassword( @RequestParam Map<String, String> requestParams,
                             HttpServletRequest request,
                             ModelAndView mav){

        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");

        // Set password for the new user
        String username = requestParams.get("userId");
        String password = requestParams.get("password");
        System.out.println("\n"+username +" "+password+"\n");

        staff.setPassword(passwordEncoder.encode(password));
        staffService.addCoordinator(staff);

        mav.setViewName("redirect:/staff/home");

        /*customUserDetailsService.save(username, password);


        *//*Configuration for auto login after registration*//*
        Authentication authentication = authWithAuthManager(request,username,password);
        *//*Redirect after auto login*//*
        successHandler.onAuthenticationSuccess(request,response,authentication);*/


        return mav;
    }



    // set password
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ModelAndView passwordReset(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams,HttpServletRequest request
                                      ) {
        String userEmail=null;
        final String username = requestParams.get("username");
        System.out.println(username);
        if(username != null){
            Student student = studentService.findStudentByStudentNumber(username);
            if(student != null){
                userEmail = student.getEmail();
                System.out.println(userEmail);
            }else {
                Town town = companyService.getTown(username);
                if(town != null ){
                    userEmail = town.getEmail();
                }else {

                    Staff staff = staffService.getCoordinator(username);
                    if (staff != null){
                        userEmail = staff.getEmail();
                    }else {

                    }
                  }
            }

            //Prepare and send password reset email to the user
            if(userEmail != null) {
                //Generate password reset token
                String token = UUID.randomUUID().toString();

                passwordResetTokenService.createVerificationToken(username,token);

                //Obtain string for password reset link
                String resetLink = request.getRequestURL() +"/" + token;

                //Set mail content parameters
                mailContent.setFrom("fraponyo94@gmail.com");
                mailContent.setTo(userEmail);
                mailContent.setSubject("Password reset request");

                Map<String, Object> variable = new HashMap<>();
                variable.put("appUrl", resetLink);

                mailContent.setModel(variable);

                //For trial purposes
                //Create map to hold variable required by thymeleaf ui
                Map<String,Object> thymeleafvariable = new HashMap<>();
                thymeleafvariable.put("name","Papa alex Orie");


                //Set templateName and variables required
                pdfContent.setTemplateName("mails/hello");
                pdfContent.setVariables(thymeleafvariable);

                DataSource dataSource = pdfGenerator.generatePdf(pdfContent);



                //Send mail
                emailService.sendEmail(mailContent,"mails/example",dataSource);

                System.out.println("email send successfully");

                modelAndView.setViewName("redirect:/login");
            }

        }else{
            modelAndView.addObject("noUser","No such user exists");
            modelAndView.setViewName("password/forgotPassword");
        }

        return modelAndView;
    }



    //Reset password view
    @RequestMapping(value = "/reset/{token}", method = RequestMethod.GET)
    public ModelAndView resetPassword(ModelAndView modelAndView,@PathVariable("token") String token){
        final PasswordResetToken passToken = passwordResetTokenService.findByToken(token);

        if ((passToken == null)) {
           modelAndView.addObject("invalidToken","Invalid token plz try again");
           modelAndView.setViewName("password/forgotPassword");

        }else {

            final Calendar cal = Calendar.getInstance();
            if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
                modelAndView.addObject("expired", "Expired token .Please request again");
                modelAndView.setViewName("/password/forgotPassword");
            } else{

                String userId = passwordResetTokenService.validatePasswordResetToken(token);
                modelAndView.addObject("userId", userId);

                modelAndView.setViewName("password/password");
            }
        }
        return modelAndView;
    }

    //Rsent verification token


    // Display forgotPassword page for company
    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public ModelAndView displayForgotPasswordPage(ModelAndView modelAndView) {

        modelAndView.setViewName("password/forgotPassword");

        return modelAndView;
    }



    //Auto login configuration
    public Authentication authWithAuthManager(HttpServletRequest request, String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        return authentication;
    }


}

