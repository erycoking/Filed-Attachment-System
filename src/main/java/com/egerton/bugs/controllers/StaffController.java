
package com.egerton.bugs.controllers;

import java.util.*;
import java.util.stream.Collectors;

import com.egerton.bugs.Model.*;
import com.egerton.bugs.Model.App;
import com.egerton.bugs.Model.Enumerated.Status;
import com.egerton.bugs.service.ApplicationService;
import com.egerton.bugs.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.egerton.bugs.service.CompanyService;
import com.egerton.bugs.service.StudentService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private ApplicationService applicationService;


    @GetMapping("/home")
    public ModelAndView home(ModelAndView mav,
                             HttpServletRequest req) {

        HttpSession session = req.getSession();
        Staff staff = (Staff) session.getAttribute("staff");

        mav.addObject("username", staff.getFullName());
        mav.setViewName("/staff/home");
        return mav;
    }


    //view all applications
    @RequestMapping(value = "/viewall", method = RequestMethod.GET)
    public ModelAndView allApplications(ModelAndView mav,
                                        HttpServletRequest req,
                                        @RequestParam(defaultValue = "0") int page) {

        HttpSession session = req.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        String levelOfCoordinator = staff.getCoordinatorLevel();

        //get all students based on staff department
        List<Student> allStudents = new ArrayList<>();
        List<App> allApps = new ArrayList<>();
        if (levelOfCoordinator.equals("Faculty")){
                allStudents = studentService.getStudentsByFacultyId(staff.getFaculty().getId(), new PageRequest(page, 15, new Sort(Sort.Direction.ASC, "studentNumber"))).getContent();
                allApps = applicationService.getByFacultyId(staff.getFaculty().getId(), new PageRequest(page, 50, new Sort(Sort.Direction.ASC, "studentApplication"))).getContent();

        }else {
            allStudents = studentService.getByDepartmentPageable(staff.getDepartment().getId(), new PageRequest(page, 15, new Sort(Sort.Direction.ASC, "studentNumber")));
            allApps = applicationService.getByDepartmentId(staff.getDepartment().getId(), new PageRequest(page, 50, new Sort(Sort.Direction.ASC, "studentApplication"))).getContent();

        }

        mav.addObject("username", staff.getFullName());
        mav.addObject("students", allStudents);
        mav.addObject("allApps", allApps);

        mav.setViewName("/staff/view");

        return mav;
    }


    //Access student aproval view
    @GetMapping("/pending")
    public ModelAndView pending(ModelAndView mav,
                                HttpServletRequest req,
                                @RequestParam(defaultValue = "0") int page) {

        //Find authenticated  staff  object
        HttpSession session = req.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        String levelOfCoordinator = staff.getCoordinatorLevel();

        //get all students based on staff department
        List<Student> allStudents = new ArrayList<>();
        List<Student> newStudentList = new ArrayList<>();
        HashSet<Student> studentHashSet = new HashSet<>();
        List<App> allApps = new ArrayList<>();
        if (levelOfCoordinator.equals("Faculty")){
            allStudents = studentService.getStudentsByFacultyId(staff.getFaculty().getId(), new PageRequest(page, 15, new Sort(Sort.Direction.ASC, "studentNumber"))).getContent();
        }else {
            allStudents = studentService.getByDepartmentPageable(staff.getDepartment().getId(), new PageRequest(page, 15, new Sort(Sort.Direction.ASC, "studentNumber")));
        }

        if (!allStudents.isEmpty()){
            for (Student student : allStudents) {
                //check if student have made application before adding to applications
                if (!student.getApp().isEmpty()) {
                    //to help us manage approval
                    List<App> apps = applicationService.findApplicationByStudentNumber(student.getStudentNumber(), new PageRequest(page, 50, new Sort(Sort.Direction.ASC, "studentApplication"))).getContent();
                    apps.forEach(e ->{
                        if(e.getCoordinatorApproval() == Status.PENDING){
                            studentHashSet.add(student);
                        }
                    });
                }
            }
        }

        newStudentList.addAll(studentHashSet);

        mav.addObject("username", staff.getFullName());
        mav.addObject("students", newStudentList);
        mav.addObject("allApps", allApps);
        mav.setViewName("staff/pending");
        return mav;
    }

    //Access view for approved applications
    @GetMapping("/approved")
    public ModelAndView approved(ModelAndView mav,
                                 HttpServletRequest req,
                                 @RequestParam(defaultValue = "0") int page) {

        //Find authenticated  staff  object
        HttpSession session = req.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        String levelOfCoordinator = staff.getCoordinatorLevel();

        //get all students based on staff department
        List<Student> allStudents = new ArrayList<>();
        List<App> allApps = new ArrayList<>();
        if (levelOfCoordinator.equals("Faculty")){
            allStudents = studentService.getStudentsByFacultyId(staff.getFaculty().getId(), new PageRequest(page, 15, new Sort(Sort.Direction.ASC, "studentNumber"))).getContent();
            allApps = applicationService.getByFacultyIdAndCoordinatorApproval(Status.ACCEPTED, staff.getFaculty().getId(), new PageRequest(page, 50, new Sort(Sort.Direction.ASC, "studentApplication"))).getContent();

        }else {
            allStudents = studentService.getByDepartmentPageable(staff.getDepartment().getId(), new PageRequest(page, 15, new Sort(Sort.Direction.ASC, "studentNumber")));
            allApps = applicationService.getByDepartmentIdAndCoordinatorApproval(Status.ACCEPTED, staff.getDepartment().getId(), new PageRequest(page, 50, new Sort(Sort.Direction.ASC, "studentApplication"))).getContent();

        }


        /*for (Student student : allStudents) {

            //check if student have made application before adding to applications
            if (!student.getApp().isEmpty()) {
                //to help us manage approval
                List<App> apps = applicationService.
                        findApplicationByStudentNumber(student.getStudentNumber(), new PageRequest(page, 50, new Sort(Sort.Direction.ASC, "studentApplication"))).getContent().
                        stream().filter(e -> e.getCoordinatorApproval() == Status.ACCEPTED).
                        collect(Collectors.toList());
                allApps.addAll(apps);
            }
        }*/
        mav.addObject("username", staff.getFullName());
        mav.addObject("students", allStudents);
        mav.addObject("allApps", allApps);
        mav.setViewName("staff/approved");
        return mav;
    }

    //Access view disapproved apps
    @GetMapping("/disapproved")
    public ModelAndView disapproved(ModelAndView mav,
                                 HttpServletRequest req,
                                    @RequestParam(defaultValue = "0") int page) {

        //Find authenticated  staff  object
        HttpSession session = req.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        String levelOfCoordinator = staff.getCoordinatorLevel();

        //get all students based on staff department
        List<Student> allStudents = new ArrayList<>();
        List<App> allApps = new ArrayList<>();
        if (levelOfCoordinator.equals("Faculty")){
            allStudents = studentService.getStudentsByFacultyId(staff.getFaculty().getId(), new PageRequest(page, 15, new Sort(Sort.Direction.ASC, "studentNumber"))).getContent();
            allApps = applicationService.getByFacultyIdAndCoordinatorApproval(Status.REJECTED, staff.getFaculty().getId(), new PageRequest(page, 50, new Sort(Sort.Direction.ASC, "studentApplication"))).getContent();

        }else {
            allStudents = studentService.getByDepartmentPageable(staff.getDepartment().getId(), new PageRequest(page, 15, new Sort(Sort.Direction.ASC, "studentNumber")));
            allApps = applicationService.getByDepartmentIdAndCoordinatorApproval(Status.REJECTED, staff.getDepartment().getId(), new PageRequest(page, 50, new Sort(Sort.Direction.ASC, "studentApplication"))).getContent();

        }


        /*for (Student student : allStudents) {

            //check if student have made application before adding to applications
            if (!student.getApp().isEmpty()) {
                //to help us manage approval
                List<App> apps = applicationService.
                        findApplicationByStudentNumber(student.getStudentNumber(), new PageRequest(page, 50, new Sort(Sort.Direction.ASC, "studentApplication"))).getContent().
                        stream().filter(e -> e.getCoordinatorApproval() == Status.REJECTED).
                        collect(Collectors.toList());
                allApps.addAll(apps);
            }
        }*/
        mav.addObject("username", staff.getFullName());
        mav.addObject("students", allStudents);
        mav.addObject("allApps", allApps);
        mav.setViewName("staff/rejected");
        return mav;
    }

    @GetMapping("/approve")
    public ModelAndView approve(ModelAndView mav,
                                @RequestParam long appId){

//        Long appId = Long.parseLong(id);
        System.out.println(appId);
        App app = applicationService.findApplicationByApplicationId(appId);
        System.out.println(app);

        app.setCoordinatorApproval(Status.ACCEPTED);
        app.setDateCoApproved(new Date());
        applicationService.approveApplication(app);

        mav.setViewName("redirect:/staff/approved");
        return mav;
    }

    @PostMapping("/disapprove")
    public ModelAndView disapprove(ModelAndView mav,
                                   @RequestParam Map<String, String> requestParam) {

//        Long appId = Long.parseLong(id);
        App app = applicationService.findApplicationByApplicationId(Long.valueOf(requestParam.get("appId")));

        app.setCoordinatorApproval(Status.REJECTED);
        app.setCoordinatorFeedback(requestParam.get("reason"));
        app.setDateCoApproved(new Date());
        applicationService.approveApplication(app);

        mav.setViewName("redirect:/staff/disapproved");
        return mav;
    }

    @GetMapping("/student/apps")
    public ResponseEntity<List<App>> getAppsForASingleStudent(@RequestParam(name = "studentNumber") String regno,
                                                              HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(applicationService.findApplicationByStudentNumber(regno,
                new PageRequest(0, 20, new Sort(Sort.Direction.ASC, "applicationId"))).getContent(),
                headers, HttpStatus.OK);
    }
}
