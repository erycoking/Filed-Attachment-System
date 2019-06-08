
package com.egerton.bugs.service;


import com.egerton.bugs.Model.App;
import com.egerton.bugs.Model.Company;
import com.egerton.bugs.Model.Enumerated.Status;
import com.egerton.bugs.Model.Town;
import com.egerton.bugs.repositories.ApplicationRepository;
import com.egerton.bugs.repositories.CompanyRepository;
import com.egerton.bugs.repositories.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Set;


@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository appRepo;
    @Autowired
    private TownRepository townRepository;
    @Autowired
    private StudentService studentService;

    @Autowired
    private CompanyRepository companyRepository;


    public Page<App> getAllApplications(Pageable pageable) {
        return appRepo.findAll(pageable);
    }


    //Save student app
    public void SaveApplication(App app,Town town) {
        Company company = companyRepository.findByCompanyName(town.getCompany().getCompanyName());
        if(company != null){
            Town townExist = townRepository.findByEmail(town.getEmail());
            if(townExist != null ){
                app.setCompanyApplied(townExist);
            }else {

                town.setCompany(company);
                company.getTown().add(town);
                app.setCompanyApplied(town);               }
        }else{
            app.setCompanyApplied(town);
        }

        appRepo.save(app);
    }

    //update application
    public void update(Long id) {
        App app = appRepo.findByApplicationId(id);
        if (app != null) {
            appRepo.save(app);
        }
    }


    //update app
    public void updateApplication(App app,Town town) {
        Company companyApplied = companyRepository.findByCompanyName(town.getCompany().getCompanyName());
        if(companyApplied != null){
            Town townExist = townRepository.findTownByEmailAndLocationContaining(town.getEmail(),town.getLocation());
            if(townExist != null ){
                townExist.setAddress(town.getAddress());
                townExist.setRegion(town.getRegion());
                townExist.setLocation(town.getLocation());
                townExist.setEmail(town.getEmail());
                app.setCompanyApplied(townExist);
            }else {

                town.setCompany(companyApplied);
                companyApplied.getTown().add(town);
                app.setCompanyApplied(town);               }
        }else{
            app.setCompanyApplied(town);
        }

        //app.setDateApplied(app.getDateApplied());
        appRepo.save(app);

    }

    //Approve application
    public void approveApplication(App app){
        appRepo.save(app);
    }


    public App getAppByCompanyName(String studentNo, Company company){
        return appRepo.findByStudentApplication_StudentNumberAndCompanyApplied_Company(studentNo, company);
    }
    //delete app
    public void deleteApplication(App app) {
        appRepo.delete(app.getApplicationId());
    }

    //find all applications corresponding to given department
    public List<App> getByDepartment(String department) {
        return appRepo.findAllByStudentApplication_Department(department);

    }

    //find all applications corresponding to given department
    public Page<App> getByDepartmentId(int department, Pageable pageable) {
        return appRepo.findAllByStudentApplication_Department_Id(department, pageable);

    }

    //find all applications corresponding to given department
    public Page<App> getByFacultyId(int faculty, Pageable pageable) {
        return appRepo.findAllByStudentApplication_Faculty_Id(faculty, pageable);

    }

    //find all applications corresponding to given faculty and coordinator approval
    public Page<App> getByFacultyIdAndCoordinatorApproval(Status status, int faculty, Pageable pageable) {
        return appRepo.findAllByCoordinatorApprovalAndStudentApplication_Faculty_Id(status, faculty, pageable);

    }

    //find all applications corresponding to given department
    public Page<App> getByDepartmentIdAndCoordinatorApproval(Status status, int department, Pageable pageable) {
        return appRepo.findAllByCoordinatorApprovalAndStudentApplication_Department_Id(status, department, pageable);

    }


    //find student application using townid and student number
    public App findApplication(String studentNumber, Long townId) {
        return appRepo.findByStudentApplication_StudentNumberAndCompanyApplied_townId(studentNumber, townId);
    }

    //find application by applicationId
    public App findApplicationByApplicationId(Long id) {
        return appRepo.findByApplicationId(id);
    }


    //Find all applications given student number
    public Page<App> findApplicationByStudentNumber(String id, Pageable pageable) {
        return appRepo.findAllByStudentApplication_StudentNumber(id, pageable);
    }

    //Find all applications given student number
    public List<App> findByStudentApplication_StudentNumberAndCoordinatorApproval(String id, Status status) {
        return appRepo.findByStudentApplication_StudentNumberAndCoordinatorApproval(id, status);
    }

    //find application to corresponding location
    public Page<App> getAllByLocation(String location, Pageable pageable){
        return appRepo.findAllByCompanyApplied_Location(location, pageable);
    }

    //find applications of corresponding company
    public Page<App> getAllByCompanyApplied(Town town, Pageable pageable){
        return appRepo.findAllByCompanyApplied(town, pageable);
    }

    //find by company
    public Page<App> getAllByCompany(String name, Pageable pageable){
        return appRepo.findAllByCompanyApplied_Company_CompanyName(name, pageable);
    }

    //find application given town id
    public Page<App> getAllByCompanyApplied_townId(String name, Pageable pageable){
        return appRepo.findAllByCompanyApplied_Company_CompanyName(name, pageable);
    }

    //find application by coordinator approval and company applied
    public List<App> getAllByCoordinatorapprovalAndCompanyApplied_Email(Status status, String email){
        return appRepo.findAllByCoordinatorApprovalAndCompanyApplied_Email(status, email);
    }

    //find all by student application
    public Page<App> getAllByStudentapplication_StudentNumber(String regno, Pageable pageable){
        return appRepo.findAllByStudentApplication_StudentNumber(regno, pageable);
    }

    //find all by student application
    public List<App> getAllByStudentapplication_StudentNumber(String regno){
        return appRepo.findAllByStudentApplication_StudentNumber(regno);
    }

    //find application given department name
    public Page<App> getAllByStudentapplication_department(String department, Pageable pageable){
        return appRepo.findAllByStudentApplication_Department(department, pageable);
    }

    //find all by coordinator approval
    public Page<App> getAllByCoordinatorapproval(Status status, Pageable pageable){
        return appRepo.findAllByCoordinatorApproval(status, pageable);
    }

    //find all by coordinator approval
    public Page<App> getAllByCoordinatorapprovalAndRegno(Status status,String regno, Pageable pageable){
        return appRepo.findAllByCoordinatorApprovalAndStudentApplication_StudentNumber(status, regno, pageable);
    }

    //find all by coordinator approval
    public Page<App> getAllByCoordinatorapprovalNotLikeAndRegno(Status status, String regno, Pageable pageable){
        return appRepo.findAllByCoordinatorApprovalNotLikeAndStudentApplication_StudentNumber(status, regno, pageable);
    }

    //find all by coordinator approval and department
    public Page<App> getAllByCoordinatorapprovalAndStudentapplication_Department(Status status, String department, Pageable pageable){
        return appRepo.findAllByCoordinatorApprovalAndStudentApplication_Department(status, department, pageable);
    }

    //find all by coordinator approval and company's email
    public Page<App> getAllByCoordinatorapprovalAndCompanyApplied_Email(Status status, String email, Pageable pageable){
        return appRepo.findAllByCoordinatorApprovalAndCompanyApplied_Email(status, email, pageable);
    }

    //find all by company approval and department
    public Page<App> getAllByCompanyapproval(Status status, Pageable pageable){
        return appRepo.findAllByCompanyApproval(status, pageable);
    }

    //find all by date of application
    public Page<App> getAllByDateAppliedBetween(Date from, Date to, Pageable pageable){
        return appRepo.findAllByDateAppliedBetween(from, to, pageable);
    }

    //find all by date Coordinator approved
    public Page<App> getAllByDateCoApprovedBetween(Date from, Date to, Pageable pageable){
        return appRepo.findAllByDateCoApprovedBetween(from, to, pageable);
    }

    //find all by date Coordinator approved
    public Page<App> getAllByDateCompanyApprBetween(Date from, Date to, Pageable pageable){
        return appRepo.findAllByDateCompanyApprBetween(from, to, pageable);
    }

    public Page<App> getAllByFaculty(String faculty, Pageable pageable){
        return appRepo.findAllByStudentApplication_Faculty(faculty, pageable);
    }

    //find all apps by student contains
    public Page<App> getAllByStudentapplication_FullNameContains(String name, Pageable pageable){
        return appRepo.findAllByStudentApplication_FullNameContains(name, pageable);
    }

}
