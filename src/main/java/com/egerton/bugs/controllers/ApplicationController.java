package com.egerton.bugs.controllers;

import com.egerton.bugs.Model.*;
import com.egerton.bugs.Model.Enumerated.Status;
import com.egerton.bugs.Model.Enumerated.YesNo;
import com.egerton.bugs.repositories.CompanyRepository;
import com.egerton.bugs.repositories.TownRepository;
import com.egerton.bugs.service.ApplicationService;
import com.egerton.bugs.service.CompanyService;
import com.egerton.bugs.service.DeadlinesService;
import com.egerton.bugs.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/application")
public class ApplicationController {

    private StudentService studentService;
    private TownRepository companyRepo;
    private ApplicationService applicationService;
    private CompanyRepository comRepo;
    private DeadlinesService deadlinesService;

    @Autowired
    public ApplicationController(StudentService studentService,
                                 TownRepository companyRepo,
                                 ApplicationService applicationService,
                                 CompanyRepository companyRepository,
                                 DeadlinesService deadlinesService) {
        super();
        this.studentService = studentService;
        this.companyRepo = companyRepo;
        this.applicationService = applicationService;
        this.comRepo =companyRepository;
        this.deadlinesService = deadlinesService;
    }

    @ModelAttribute("regions")
    public ArrayList<String> getRegions(){
        return new ArrayList<>(Arrays.asList("Central Kenya", "Coastal Kenya", "East Kenya", "Nairobi", "Nyanza", "Rift Valley", "West Kenya"));
    }

    @ModelAttribute("time")
    public Model getTimelines(Model model){
        List<Deadlines> deadlinesList = deadlinesService.getAllTimeliness();
        if(!deadlinesList.isEmpty()){
            Deadlines deadlines = deadlinesList.get(0);
            java.util.Date from =  deadlines.getFrom();
            java.util.Date to =  deadlines.getTo();

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(from);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(to);

            Calendar cal = Calendar.getInstance();
            DateFormat format = new SimpleDateFormat("dd MMM yyyy");

            String openingDate = format.format(from);
            String closingDate = format.format(to);

            if(cal.before(cal1)){
                model.addAttribute("timelineMsg", "Applications are not open until: "+ openingDate + " to: "+ closingDate);
                model.addAttribute("canApply", false);
            }else if(cal.after(cal2)){
                model.addAttribute("timelineMsg", "Applications were closed on: "+ closingDate);
                model.addAttribute("canApply", false);
            }else {
                model.addAttribute("timelineMsg", "Applications are not yet Open");
                model.addAttribute("canApply", true);
            }
            model.addAttribute("timeline", deadlinesList.get(0));
        }else{
            model.addAttribute("timelineMsg", "Applications are not yet Open");
            model.addAttribute("timeline", null);
            model.addAttribute("canApply", false);

        }
        return model;
    }

    //Access first application n form
    @GetMapping("/apply")
    public String gotoApplication(Model model, HttpServletRequest req) throws ParseException {

        HttpSession session = req.getSession();
        Student student = (Student)session.getAttribute("student");

        model.addAttribute("student", student.getFullName());

        Town town = new Town();
        town.setCompany(new Company());
        Town town1 = new Town();
        town1.setCompany(new Company());
        TownWrapper townWrapper = new TownWrapper();
        townWrapper.getTownWrapper().addAll(Arrays.asList(town, town1));

        model.addAttribute("wrapper", townWrapper);
        model.addAttribute("apps", applicationService.getAllByStudentapplication_StudentNumber(student.getStudentNumber()));

        return "student/application";
    }


    @PostMapping("/student/home")
    public ModelAndView home(final ModelAndView mav,
                             @Valid @ModelAttribute("wrapper") TownWrapper townWrapper,
                             BindingResult bindingResult,
                             HttpServletRequest req,
                             RedirectAttributes attributes) {

        HttpSession session = req.getSession();
        Student student = (Student)session.getAttribute("student");

        Company companyExists;
        App ExistingApp;

        if (bindingResult.hasErrors()) {
            mav.addObject("apps", applicationService.getAllByStudentapplication_StudentNumber(student.getStudentNumber()));
            mav.setViewName("/student/application");
        } else {
            for (Town e: townWrapper.getTownWrapper()){
                companyExists = comRepo.findByCompanyName(e.getCompany().getCompanyName());
                if ((ExistingApp = applicationService.getAppByCompanyName(student.getStudentNumber(),
                        companyExists)) != null ){

                    System.out.println(ExistingApp);
                    mav.addObject("apps", applicationService.getAllByStudentapplication_StudentNumber(student.getStudentNumber()));
                    mav.addObject( "alreadySelected",
                            "You already selected (" +
                                    e.getCompany().getCompanyName() +
                                    ")company as one of your choices."
                                    + "Please provide a different company");
                    mav.setViewName("/student/application");
                    break;
                }else {
                    App app = new App(Status.PENDING, Status.PENDING, new Date(),
                            null, null, null, e,  student);
                    applicationService.SaveApplication(app,e);
                    app.getStudentApplication().setApplied(YesNo.YES);
                    mav.setViewName("redirect:/student/pendingApplications");
                }
            }
        }


        return mav;
    }

    @PostMapping(value = "/student/home", params = "addCompany")
    public String addCompany(final Model model,
                             @Valid @ModelAttribute TownWrapper townWrapper) {

        Town town = new Town();
        town.setCompany(new Company());
        townWrapper.getTownWrapper().add(town);

        int townWrapperSize = townWrapper.getTownWrapper().size();


        model.addAttribute("townWrapperSize",townWrapperSize);
        model.addAttribute("wrapper", townWrapper);

        return "/student/application";
    }

    @PostMapping(value = "/student/home", params = "removeCompany")
    public String removeCompany(final Model model,
                                @Valid @ModelAttribute("wrapper") TownWrapper townWrapper,
                                HttpServletRequest req) {
        final Integer id = Integer.valueOf(req.getParameter("removeCompany"));
        townWrapper.getTownWrapper().remove(id.intValue());
        model.addAttribute("wrapper", townWrapper);
        return "student/application";
    }

    // return view for user to edit the application
    @GetMapping("/edit")
    public ResponseEntity<App> edit(@RequestParam("appId") Long appId, ModelAndView modelAndView) {

        /*Town town =   applicationService.findApplicationByApplicationId(appId).getCompanyApplied();
        modelAndView.addObject("town",town);
        modelAndView.addObject("applicationId",appId);
        modelAndView.setViewName("student/application/edit");*/

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        return new ResponseEntity<>(applicationService.findApplicationByApplicationId(appId), headers, HttpStatus.OK);
    }


    //update application
    @PostMapping("/update")
    public ModelAndView update(ModelAndView mav,@ModelAttribute("town") Town town,
                               @RequestParam Map<String, String> requestParams) {

        //find the app by app id
        App app = applicationService.findApplicationByApplicationId(Long.valueOf(requestParams.
                get("appId")));
        Company company = new Company();
        company.setCompanyName(requestParams.get("companyName"));
        town.setCompany(company);

        if(app != null){
            applicationService.updateApplication(app,town);
        }
        //redirect the view
        mav.setViewName("redirect:/student/pendingApplications");

        return mav;
    }



    @GetMapping("/delete")
    public ModelAndView ediDelete(ModelAndView mav,
                                  @RequestParam("appId") Long appId) {

        App app = applicationService.findApplicationByApplicationId(appId);

        app.setCompanyApplied(null);
        app.setStudentApplication(null);

        applicationService.deleteApplication(app);

        mav.setViewName("redirect:/student/pendingApplications");
        return mav;
    }

    @PostMapping("/approve/{appId}")
    public ModelAndView approve(ModelAndView mav,
                                @PathVariable("appId") Long appId) {

        App app = applicationService.findApplicationByApplicationId(appId);

        //set approval message, date etc
        app.setCoordinatorApproval(Status.ACCEPTED);
        app.setDateCoApproved(new Date());

        //save the changes
        applicationService.update(appId);

        //set return view
        mav.setViewName("redirect:/staff/approve/{studentNumber}");

        return mav;
    }

    //Get view for rejection message
    @GetMapping("/reject/{appId}")
    public ModelAndView getReject(ModelAndView mav,
                                  @PathVariable("appId") Long appId) {

        mav.addObject("id", appId);

        //set return view
        mav.setViewName("rejectMessage");
        return mav;
    }

    /*Update company details after*/
    @PostMapping("/update/company")
    public ModelAndView updateCompany(final ModelAndView mav,
                                      @Valid @ModelAttribute("Town") Town town,
                                      BindingResult bindingResult,
                                      HttpServletRequest req) {

        HttpSession session = req.getSession();
        Student student = (Student)session.getAttribute("student");


        if (bindingResult.hasErrors()) {
            mav.setViewName("/student/application");
        } else {

        }
        return mav;
    }

}