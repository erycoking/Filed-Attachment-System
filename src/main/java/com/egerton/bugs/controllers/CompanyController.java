package com.egerton.bugs.controllers;

import com.egerton.bugs.Model.App;
import com.egerton.bugs.Model.Company;
import com.egerton.bugs.Model.CompanyAccount;
import com.egerton.bugs.Model.Enumerated.Role;
import com.egerton.bugs.Model.Enumerated.Status;
import com.egerton.bugs.Model.Town;
import com.egerton.bugs.repositories.ApplicationRepository;
import com.egerton.bugs.service.CompanyService;
import com.egerton.bugs.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.util.*;


@Controller
@RequestMapping("/company")
public class CompanyController {

    private CompanyService companyService;

    private ApplicationRepository appRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Autowired
    public CompanyController(CompanyService companyService,ApplicationRepository appRepo) {
        super();

        this.companyService = companyService;

        this.appRepo = appRepo;

    }



    //Return company registration form view template
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Town town = (Town) session.getAttribute("town");
        model.addAttribute("username", principal.getName());
        return "company/home";
    }

    @GetMapping("/app/viewApp")
    public String viewApps(Model model,
                           Principal principal,
                           HttpServletRequest req,
                           @RequestParam(defaultValue = "0") int page) {

        HttpSession session = req.getSession();
        Town town = (Town) session.getAttribute("town");
        model.addAttribute("username", principal.getName());
//        model.addAttribute("apps", appRepo.findAllByCompanyApprovalAndCoordinatorApproval(
//                Status.PENDING, Status.PENDING, new PageRequest(page, 10, new Sort(Sort.Direction.ASC, "dateApplied"))));
        model.addAttribute("apps", appRepo.findAllByCoordinatorApprovalAndCompanyApprovalAndCompanyApplied_Email(
                Status.ACCEPTED, Status.PENDING,  principal.getName(), new PageRequest(page, 10, new Sort("dateApplied"))));
        return "company/viewApps";
    }


    @GetMapping("/app/accept")
    public String approve(Model model,
                          Principal principal,
                          HttpServletRequest req,
                          @RequestParam Map<String, String> requestParam) {

        Long appId = Long.valueOf(requestParam.get("appId"));
        App app = appRepo.findByApplicationId(appId);

        app.setCompanyApproval(Status.ACCEPTED);
//        app.getStudentApplication().setCanApply
        app.setDateCompanyAppr(new Date());

        appRepo.save(app);

        return "redirect:/company/viewAccepted";
    }

    @GetMapping("/app/reject")
    public String reject(Model model,
                         Principal principal,
                         HttpServletRequest req,
                         @RequestParam Map<String, String> requestParam) {

        Long appId = Long.valueOf(requestParam.get("appId"));
        App app = appRepo.findByApplicationId(appId);
        app.setCompanyApproval(Status.REJECTED);
        app.setDateCompanyAppr(new Date());

        appRepo.save(app);

        return "redirect:/company/viewRejected";
    }

    @GetMapping("/app/viewAccepted")
    public String getApprovedApps(Model model,
                                  Principal principal,
                                  HttpServletRequest req,
                                  @RequestParam(defaultValue = "0") int page) {

        HttpSession session = req.getSession();
        Town town = (Town) session.getAttribute("town");
        model.addAttribute("username", principal.getName());
        model.addAttribute("apps", appRepo.findAllByCoordinatorApprovalAndCompanyApprovalAndCompanyApplied_Email(
                Status.ACCEPTED, Status.ACCEPTED,  principal.getName(), new PageRequest(page, 10, new Sort("dateApplied"))));
        return "company/approved_apps";
    }

    @GetMapping("/app/viewRejected")
    public String getDisapprovedApps(Model model,
                                     Principal principal,
                                     HttpServletRequest req,
                                     @RequestParam(defaultValue = "0") int page) {

        HttpSession session = req.getSession();
        Town town = (Town) session.getAttribute("town");
        model.addAttribute("username", principal.getName());
        model.addAttribute("apps", appRepo.findAllByCoordinatorApprovalAndCompanyApprovalAndCompanyApplied_Email(
                Status.ACCEPTED, Status.REJECTED,  principal.getName(), new PageRequest(page, 10, new Sort("dateApplied"))));
        return "company/disapproved";
    }

    // Return company registration form view template
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationPage(Model model) {
        Company company = new Company();
        company.getTown().add(new Town());

        ArrayList<String> regions = new ArrayList<>(Arrays.asList("Central Kenya", "Coastal Kenya", "East Kenya", "Nairobi", "Nyanza", "Rift Valley", "West Kenya"));
        model.addAttribute("regions", regions);


        int size = company.getTown().size();
        if (size >= 2) {
            model.addAttribute("size", size);
        }
        model.addAttribute("company", company);
        return "company/register";
    }


    // Process registration form input data
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String companyRegistrationForm(final Model model,
                                          @Valid @ModelAttribute Company company,
                                          BindingResult bindingResult, RedirectAttributes attributes) {
        //to hold emails
        List<String> emails = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            return "company/register";
        } else {
            company.getTown().forEach(e -> {
                e.setCompany(company);

                emails.add(e.getEmail());

            });
            if (companyService.save(company)) {
                model.addAttribute("successMsg", "company successfully added.");

                int size = company.getTown().size();
                if (size >= 2) {
                    model.addAttribute("size", size);
                }

                //add flashattributes for use during setting of email
                attributes.addFlashAttribute("email", emails);

            } else {
                model.addAttribute("successMsg", "Error adding companies");
            }
        }

        return "redirect:/company/setPassword";
    }

    @PostMapping(value = "/register", params = "addBranch")
    public String addBranch(final Model model,
                            @Valid @ModelAttribute Company company) {

        company.getTown().add(new Town());

        model.addAttribute("company", company);

        ArrayList<String> regions = new ArrayList<>(Arrays.asList("Central Kenya", "Coastal Kenya", "East Kenya", "Nairobi", "Nyanza", "Rift Valley", "West Kenya"));
        model.addAttribute("regions", regions);

        int size = company.getTown().size();
        if (size >= 2) {
            model.addAttribute("size", size);
        }

        return "company/register";
    }


    @PostMapping(value = "/register", params = "removeBranch")
    public String removeBranch(final Model model,
                               HttpServletRequest req,
                               @Valid @ModelAttribute Company company) {

        final Integer id = Integer.valueOf(req.getParameter("removeBranch"));

        company.getTown().remove(id.intValue());
        model.addAttribute("company", company);

        ArrayList<String> regions = new ArrayList<>(Arrays.asList("Central Kenya", "Coastal Kenya", "East Kenya", "Nairobi", "Nyanza", "Rift Valley", "West Kenya"));
        model.addAttribute("regions", regions);

        int size = company.getTown().size();
        if (size >= 2) {
            model.addAttribute("size", size);
        }
        return "company/register";
    }


    //set password for the head office of the company
    @RequestMapping(value = "/setPassword", method = RequestMethod.GET)
    public ModelAndView password(ModelAndView modelAndView,
                                 @ModelAttribute("email") Object email) {

        //set model attributes
        modelAndView.addObject("email", email);

        modelAndView.setViewName("company/password");

        return modelAndView;
    }


    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public ModelAndView password(ModelAndView modelAndView,
                                 @RequestParam Map<String, String> requestParam,
                                 RedirectAttributes attributes) {

        Town town = companyService.getTown(requestParam.get("selectedEmail"));


        if (town != null) {
            String password = bCryptPasswordEncoder.encode(requestParam.get("password"));
            CompanyAccount companyAccount = new CompanyAccount(password, Role.ROLE_COMPANY, town);

            town.setCompanyAccount(companyAccount);


            companyService.saveTown(town);

            attributes.addFlashAttribute("successMessage",
                    "Your password has been set successfully.You can now login!");

            modelAndView.setViewName("redirect:/");
        }


        return modelAndView;
    }

}

