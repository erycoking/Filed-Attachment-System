package com.egerton.bugs.controllers;

import com.egerton.bugs.Model.Department;
import com.egerton.bugs.Model.Student;
import com.egerton.bugs.components.PdfContent;
import com.egerton.bugs.components.PdfGenerator;
import com.egerton.bugs.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.FileSystems;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DefaultController{

    @Autowired
    private StudentService studentService;

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private PdfContent pdfContent;



    /*Home page*/
    @GetMapping("/")
    public ModelAndView home(){
        ModelAndView mav = new ModelAndView();
       mav.setViewName("redirect:/login");
      // mav.setViewName("mails/application_letter");
        return mav;
    }

    /*Login */
    @GetMapping("/login")
    public ModelAndView login( ModelAndView mav ){
        mav.setViewName("login/login");

        return mav;
    }

    /*Access registration page*/
    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView){
        modelAndView.addObject("faculty", studentService.getFaculties());
    	modelAndView.setViewName("home");
    	return modelAndView;
    }

    @GetMapping("/getDepartments")
    public ResponseEntity<List<Department>> getDepartments(@RequestParam int id){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(studentService.findDepartmentsByFaculty(id), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/preview.pdf", method = RequestMethod.GET)
    protected String previewSection(Principal principal,
                                    final HttpServletRequest request,
                                    final HttpServletResponse response) {
        try {

            // get current user
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute("student");

            //Create map to hold variable required by thymeleaf ui
            Map<String,Object> thymeleafvariable = new HashMap<>();
            thymeleafvariable.put("director","Andrew Ngesa");
            thymeleafvariable.put("student", student);

            pdfContent.setVariables(thymeleafvariable);
            pdfContent.setTemplateName("mails/individual_application_letter");

            byte[] documentInBytes = pdfGenerator.createPdf(pdfContent, request, response);
            response.setHeader("Content-Disposition", "inline; filename=\"report.pdf\"");
            //response.setDateHeader("Expires", -1);
            response.setContentType("application/pdf");
            response.setContentLength(documentInBytes.length);

            response.getOutputStream().write(documentInBytes);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }
}
