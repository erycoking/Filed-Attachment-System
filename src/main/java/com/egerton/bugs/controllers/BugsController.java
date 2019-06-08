package com.egerton.bugs.controllers;

import com.egerton.bugs.Model.Deadlines;
import com.egerton.bugs.Model.Enumerated.Gender;
import com.egerton.bugs.Model.Enumerated.Role;
import com.egerton.bugs.Model.Enumerated.Status;
import com.egerton.bugs.Model.Enumerated.Year;
import com.egerton.bugs.Model.Staff;
import com.egerton.bugs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bugs")
public class BugsController {

    private StudentService studentService;
    private StaffService staffService;
    private CompanyService companyService;
    private ApplicationService applicationService;
    private BCryptPasswordEncoder passwordEncoder;
    private DeadlinesService deadlinesService;

    @Autowired
    public BugsController(StudentService studentService, StaffService staffService, CompanyService companyService, ApplicationService applicationService, BCryptPasswordEncoder passwordEncoder, DeadlinesService deadlinesService) {
        this.studentService = studentService;
        this.staffService = staffService;
        this.companyService = companyService;
        this.applicationService = applicationService;
        this.passwordEncoder = passwordEncoder;
        this.deadlinesService = deadlinesService;

//        ConcurrentTaskScheduler.
    }

    @GetMapping({"/", "/home"})
    public String bugsHome(Model model,
                           Principal principal,
                           HttpServletRequest req){
        HttpSession session = req.getSession();
        Staff staff = (Staff) session.getAttribute("staff");

        model.addAttribute("username", staff.getFullName());
        return "/bugs/bugs_home";
    }

    @GetMapping("/students")
    public String getStudents(Model model,
                              HttpServletRequest req,
                              @RequestParam(defaultValue = "0") int currentPage){

        HttpSession session = req.getSession();
        Staff staff = (Staff)session.getAttribute("staff");

        model.addAttribute("username", staff.getFullName());

        model.addAttribute("students", studentService.getAllStudentsPageable(new PageRequest(currentPage, 15, new Sort("fullName"))));

        model.addAttribute("currentPage", currentPage);
        return "/bugs/bugs_student";
    }

    @GetMapping(value = "/students", params = {"searchTerm", "searchValue"})
    public String getStudent(Model model,
                             HttpServletRequest req,
                             @RequestParam(defaultValue = "0") int currentPage,
                             @RequestParam("searchTerm") String search,
                             @RequestParam("searchValue") String value){

        HttpSession session = req.getSession();
        Staff staff = (Staff)session.getAttribute("staff");

        model.addAttribute("username", staff.getFullName());

        System.out.println(search);

        if(search.equals("Faculty")){
            model.addAttribute("students", studentService.getStudentsByFaculty(studentService.getFaculty(Integer.valueOf(value)), new PageRequest(currentPage, 15, new Sort("fullName"))));
        }else if(search.equals("Department")){
            model.addAttribute("students", studentService.getByDepartmentPageable(studentService.getDepartment(Integer.valueOf(value)).getId(), new PageRequest(currentPage, 15, new Sort("fullName"))));
        }else if(search.equals("Name")){
            model.addAttribute("students", studentService.getAllByFullName(value, new PageRequest(currentPage, 15, new Sort("fullName"))));
        }else if(search.equals("Year")){
            model.addAttribute("students", studentService.getStudenByCurrentYear(Year.valueOf(value), new PageRequest(currentPage, 15, new Sort("fullName"))));
        }else if(search.equals("Gender")){
            model.addAttribute("students", studentService.getAllByGender(value, new PageRequest(currentPage, 15, new Sort("fullName"))));
        }else if(search.equals("Regno")){
            model.addAttribute("students", studentService.getAllByStudentNumber(value, new PageRequest(currentPage, 15, new Sort("fullName"))));
        }else{
            model.addAttribute("students", studentService.getAllStudentsPageable(new PageRequest(currentPage, 15, new Sort("fullName"))));
        }

        model.addAttribute("currentPage", currentPage);
        return "/bugs/bugs_student";
    }

    @GetMapping("/apps")
    public String getApps(Model model,
                           HttpServletRequest req,
                           @RequestParam(defaultValue = "0") int currentPage){

        HttpSession session = req.getSession();
        Staff staff = (Staff)session.getAttribute("staff");

        model.addAttribute("username", staff.getFullName());
        model.addAttribute("apps", applicationService.getAllApplications(new PageRequest(currentPage, 15, new Sort("dateApplied"))));

        model.addAttribute("currentPage", currentPage);
        return "/bugs/bugs_apps";
    }

    @GetMapping(value = "/apps", params = {"searchTerm", "searchValue"})
    public String getApps(Model model,
                          HttpServletRequest req,
                          @RequestParam(defaultValue = "0") int currentPage,
                          @RequestParam("searchTerm") String search,
                          @RequestParam("searchValue") String value){

        HttpSession session = req.getSession();
        Staff staff = (Staff)session.getAttribute("staff");

        model.addAttribute("username", staff.getFullName());

        if (search.equals("Company")){
            model.addAttribute("apps", applicationService.getAllByCompany(value, new PageRequest(currentPage, 15, new Sort("dateApplied"))));
        }else if(search.equals("Location")){
            model.addAttribute("apps", applicationService.getAllByLocation(value, new PageRequest(currentPage, 15, new Sort("dateApplied"))));
        }else if(search.equals("Department")){
            model.addAttribute("apps", applicationService.getAllByStudentapplication_department(value, new PageRequest(currentPage, 15, new Sort("dateApplied"))));
        }else if(search.equals("Faculty")){
            model.addAttribute("apps", applicationService.getAllByFaculty(value, new PageRequest(currentPage, 15, new Sort("dateApplied"))));
        }else if(search.equals("Coordinatorapproval")){
            model.addAttribute("apps", applicationService.getAllByCoordinatorapproval(Status.valueOf(value), new PageRequest(currentPage, 15, new Sort("dateApplied"))));
        }else if(search.equals("Companyapproval")){
            model.addAttribute("apps", applicationService.getAllByCompanyapproval(Status.valueOf(value), new PageRequest(currentPage, 15, new Sort("dateApplied"))));
        }else if (search.equals("Regno")){
            model.addAttribute("apps", applicationService.getAllByStudentapplication_StudentNumber(value, new PageRequest(currentPage, 15, new Sort("dateApplied"))));
        }else if (search.equals("Name")){
            model.addAttribute("apps", applicationService.getAllByStudentapplication_FullNameContains(value, new PageRequest(currentPage, 15, new Sort("dateApplied"))));
        }else{
            model.addAttribute("apps", applicationService.getAllApplications(new PageRequest(currentPage, 15, new Sort("dateApplied"))));
        }

        model.addAttribute("currentPage", currentPage);
        return "/bugs/bugs_apps";
    }

    @GetMapping(value = "/apps", params = {"from", "to"})
    public String getAppsByDate(Model model,
                          HttpServletRequest req,
                          @RequestParam(defaultValue = "0") int currentPage,
                          @RequestParam("searchTerm") String search,
                          @RequestParam("from") String from,
                          @RequestParam("to") String to){

        HttpSession session = req.getSession();
        Staff staff = (Staff)session.getAttribute("staff");

        model.addAttribute("username", staff.getFullName());

        java.util.Date startDate = new java.util.Date();
        java.util.Date endDate = new java.util.Date();

        try {

             startDate = new SimpleDateFormat("MM/dd/yyyy").parse(from);
             endDate = new SimpleDateFormat("MM/dd/yyyy").parse(to);



        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }

        if(search.equals("DateApplied")){
            model.addAttribute("apps", applicationService.getAllByDateAppliedBetween(new Date(startDate.getTime()), new Date(endDate.getTime()), new PageRequest(currentPage, 15, new Sort("dateApplied"))));
        }else if(search.equals("DateCoApproved")){
            model.addAttribute("apps", applicationService.getAllByDateCoApprovedBetween(new Date(startDate.getTime()), new Date(endDate.getTime()), new PageRequest(currentPage, 15, new Sort("dateApplied"))));
        }else if(search.equals("DateCompanyAppr")){
            model.addAttribute("apps", applicationService.getAllByDateCompanyApprBetween(new Date(startDate.getTime()), new Date(endDate.getTime()), new PageRequest(currentPage, 15, new Sort("dateApplied"))));
        }else{
            model.addAttribute("apps", applicationService.getAllApplications(new PageRequest(currentPage, 15, new Sort("dateApplied"))));
        }

        model.addAttribute("currentPage", currentPage);
        return "/bugs/bugs_apps";
    }

    @GetMapping("/staff")
    public String getStaff(Model model,
                           HttpServletRequest req,
                           @RequestParam(defaultValue = "0") int currentPage){

        HttpSession session = req.getSession();
        Staff staff = (Staff)session.getAttribute("staff");

        model.addAttribute("username", staff.getFullName());
        model.addAttribute("staff", staffService.getAllStaff(new PageRequest(currentPage, 15, new Sort("fullName"))));

        model.addAttribute("currentPage", currentPage);
        return "/bugs/bugs_staff";
    }

    @GetMapping(value = "/staff", params = {"searchTerm", "searchValue"})
    public String getStaff(Model model,
                          HttpServletRequest req,
                          @RequestParam(defaultValue = "0") int currentPage,
                          @RequestParam("searchTerm") String search,
                          @RequestParam("searchValue") String value){

        HttpSession session = req.getSession();
        Staff staff = (Staff)session.getAttribute("staff");

        model.addAttribute("username", staff.getFullName());

        if (search.equals("PayrollNo")){
            model.addAttribute("staff", staffService.getAllByPayrollId(value, new PageRequest(currentPage, 15, new Sort("fullName"))));
        }else if(search.equals("Name")){
            model.addAttribute("staff", staffService.getAllByFullNameContains(value, new PageRequest(currentPage, 15, new Sort("fullName"))));
        }else if(search.equals("Department")){
            model.addAttribute("staff", staffService.getAllByDepartment(value, new PageRequest(currentPage, 15, new Sort("fullName"))));
        }else if(search.equals("Faculty")){
            model.addAttribute("staff", staffService.getAllByFaculty(value, new PageRequest(currentPage, 15, new Sort("fullName"))));
        }else if(search.equals("Gender")){
            model.addAttribute("staff", staffService.getAllByGender(Gender.valueOf(value), new PageRequest(currentPage, 15, new Sort("fullName"))));
        }else{
            model.addAttribute("staff", staffService.getAllStaff(new PageRequest(currentPage, 15, new Sort("fullName"))));
        }

        model.addAttribute("currentPage", currentPage);
        return "/bugs/bugs_staff";
    }

    @GetMapping("/company")
    public String getCompany(Model model,
                           HttpServletRequest req,
                           @RequestParam(defaultValue = "0") int currentPage){

        HttpSession session = req.getSession();
        Staff staff = (Staff)session.getAttribute("staff");

        model.addAttribute("username", staff.getFullName());
        model.addAttribute("towns", companyService.getAllTowns(new PageRequest(currentPage, 15, new Sort("Company_companyName"))));

        model.addAttribute("currentPage", currentPage);
        return "/bugs/bugs_company";
    }

    @GetMapping(value = "/company", params = {"searchTerm", "searchValue"})
    public String getCompany(Model model,
                           HttpServletRequest req,
                           @RequestParam(defaultValue = "0") int currentPage,
                           @RequestParam("searchTerm") String search,
                           @RequestParam("searchValue") String value){

        HttpSession session = req.getSession();
        Staff staff = (Staff)session.getAttribute("staff");

        model.addAttribute("username", staff.getFullName());

        if (search.equals("Name")){
            model.addAttribute("towns", companyService.getAllByCompany_CompanyName(value, new PageRequest(currentPage, 15, new Sort("Company_companyName"))));
        }else if(search.equals("Location")){
            model.addAttribute("towns", companyService.getAllByLocation(value, new PageRequest(currentPage, 15, new Sort("Company_companyName"))));
        }else if(search.equals("email")){
            model.addAttribute("towns", companyService.getAllByEmail(value, new PageRequest(currentPage, 15, new Sort("Company_companyName"))));
        }else{
            model.addAttribute("towns", companyService.getAllTowns(new PageRequest(currentPage, 15, new Sort("Company_companyName"))));
        }

        model.addAttribute("currentPage", currentPage);
        return "/bugs/bugs_company";
    }

    /*add cordinator get*/
    @GetMapping("/addCoordinator")
    public ModelAndView addCordinator(ModelAndView modelAndView,HttpServletRequest req){
        HttpSession session = req.getSession();
        Staff staff = (Staff)session.getAttribute("staff");

        modelAndView.addObject("username", staff.getFullName());
        modelAndView.addObject("staff", new Staff());
        modelAndView.addObject("faculty", studentService.getFaculties());
        modelAndView.setViewName("bugs/add_coordinator");

        return modelAndView;

    }


    /*add cordinator get*/
    @PostMapping("/addCoordinator")
    public ModelAndView addCoordinator(ModelAndView mav, @Valid @ModelAttribute("staff") Staff staff,
                                       BindingResult result,
                                       @RequestParam Map<String, String> requestParams,
                                       RedirectAttributes attributes){

        Staff staff1 = staffService.getCoordinator(requestParams.get("payrollId"));
        Staff staff2 = staffService.getCoordinatorByEmail(requestParams.get("email"));
        if (staff1 != null) {
            attributes.addAttribute("faculty", studentService.getFaculties());
            result.rejectValue("payrollId", "error.payrollId",
                    "There is already a staff member registered with the payroll number provided.");
        }
        if (staff2 != null) {
            attributes.addAttribute("faculty", studentService.getFaculties());
            result.rejectValue("email", "error.email",
                    "There is already a staff member registered with the email provided.");
        }

        if (result.hasErrors()) {
            mav.addObject("faculty", studentService.getFaculties());
            mav.setViewName("/bugs/add_coordinator");
        } else {
            staff.setRole(Role.ROLE_COORDINATOR);
            staff.setPassword(passwordEncoder.encode(staff.getPayrollId()+staff.getEmail().substring(0, 3)));
            staffService.addCoordinator(staff);

            mav.setViewName("redirect:/bugs/staff");
        }
        return mav;

    }

    @GetMapping("/timelines")
    public ModelAndView getTimelinesPage(ModelAndView mav, HttpServletRequest req){

        HttpSession session = req.getSession();
        Staff staff = (Staff) session.getAttribute("staff");

        mav.addObject("staff", staff);
        mav.addObject("username", staff.getFullName());

        List<Deadlines> deadlinesList = deadlinesService.getAllTimeliness();

        if(!deadlinesList.isEmpty()){
            mav.addObject("timeline", deadlinesList.get(0));
        }else{
            mav.addObject("timeline", null);
        }


        mav.setViewName("bugs/timelines");

        return mav;
    }

    @PostMapping("/timeline")
    public ModelAndView setTimelines(ModelAndView mav,
                                     HttpServletRequest req,
                                     @RequestParam Map<String, String> requestParams){

        try {

            java.util.Date opening = new SimpleDateFormat("MM/dd/yyyy").parse(requestParams.get("opening"));
            java.util.Date closing = new SimpleDateFormat("MM/dd/yyyy").parse(requestParams.get("closing"));

            Deadlines deadlines = new Deadlines(new Date(opening.getTime()), new Date(closing.getTime()));
            deadlinesService.save(deadlines);

        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }

        mav.setViewName("redirect:/bugs/timelines");
        return mav;
    }

    @GetMapping("/registerStaff")
    public ModelAndView getRegisterCoordinator(ModelAndView mav, HttpServletRequest req){
        HttpSession session = req.getSession();
        Staff staff = (Staff)session.getAttribute("staff");

        mav.addObject("username", staff.getFullName());
        mav.addObject("staff", new Staff());
        mav.addObject("faculty", studentService.getFaculties());
        mav.setViewName("/bugs/register_bugs");

        return mav;
    }

    @PostMapping("/addStaff")
    public ModelAndView registerCoordinator(ModelAndView mav, @Valid @ModelAttribute("staff") Staff staff,
                                            BindingResult result,
                                            @RequestParam Map<String, String> requestParams,
                                            RedirectAttributes attributes){

        Staff staff1 = staffService.getCoordinator(requestParams.get("payrollId"));
        Staff staff2 = staffService.getCoordinatorByEmail(requestParams.get("email"));
        if (staff1 != null) {
            attributes.addAttribute("faculty", studentService.getFaculties());
            result.rejectValue("payrollId", "error.payrollId",
                    "There is already a staff member registered with the payroll number provided.");
        }
        if (staff2 != null) {
            attributes.addAttribute("faculty", studentService.getFaculties());
            result.rejectValue("email", "error.email",
                    "There is already a staff member registered with the email provided.");
        }

        if (result.hasErrors()) {
            mav.addObject("faculty", studentService.getFaculties());
            mav.setViewName("/bugs/add_coordinator");
        } else {
            staff.setRole(Role.ROLE_BUGS);
            staff.setPassword(passwordEncoder.encode(staff.getPayrollId()+staff.getEmail().substring(0, 3)));
            staff.setCoordinatorLevel("None");
            staffService.addCoordinator(staff);

            mav.setViewName("redirect:/bugs/staff");
        }
        return mav;
    }


}
