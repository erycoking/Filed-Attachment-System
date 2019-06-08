
package com.egerton.bugs.repositories;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.egerton.bugs.Model.App;
import com.egerton.bugs.Model.Company;
import com.egerton.bugs.Model.Enumerated.Status;
import com.egerton.bugs.Model.Town;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ApplicationRepository extends JpaRepository<App, Long> {
    //find application given application id
    App findByApplicationId(Long applicationId);

    //find Applications base on department
    Page<App> findAllByStudentApplication_Department_Id(int id, Pageable pageable);

    //find applications based on faculty
    Page<App> findAllByStudentApplication_Faculty_Id(int id, Pageable pageable);

    //find applications base on faculty and coordinator approval
    Page<App> findAllByCoordinatorApprovalAndStudentApplication_Faculty_Id(Status status, int id, Pageable pageable);

    //find applications base on department and coordinator approval
    Page<App> findAllByCoordinatorApprovalAndStudentApplication_Department_Id(Status status, int id, Pageable pageable);


    //find all by student application
    Page<App> findAllByStudentApplication_StudentNumber(String regno, Pageable pageable);
    List<App> findAllByStudentApplication_StudentNumber(String regno);

    //find all applications by student number
    List<App> findByStudentApplication_StudentNumberAndCoordinatorApproval(String studentNumber, Status status);

    //find application corresponding
    App findByStudentApplication_StudentNumberAndCompanyApplied_townId(String StudentNumber, Long townId);
    App findByStudentApplication_StudentNumberAndCompanyApplied_Company(String studentNumber, Company company);

    //find applications of corresponding company
    Page<App> findAllByCompanyApplied(Town town, Pageable pageable);
    List<App> findAllByCompanyApplied(Town town);

    //find applications by corresponding location
    Page<App> findAllByCompanyApplied_Location(String location, Pageable pageable);

    //find application given town id
    Page<App> findAllByCompanyApplied_Company_CompanyName(String name, Pageable pageable);

    //find by coordinator approval and company applied
    List<App> findAllByCoordinatorApprovalAndCompanyApplied_Email(Status status, String email);

    //find application given department name
    Page<App> findAllByStudentApplication_Department(String department, Pageable pageable);
    List<App> findAllByStudentApplication_Department(String department);

    //find application given faculty name
    Page<App> findAllByStudentApplication_Faculty(String faculty, Pageable pageable);
    List<App> findAllByStudentApplication_Faculty(String faculty);

    //find all by coordinator approval
    Page<App> findAllByCoordinatorApproval(Status status, Pageable pageable);
    Page<App> findAllByCoordinatorApprovalAndStudentApplication_StudentNumber(Status status, String regno, Pageable pageable);
    Page<App> findAllByCoordinatorApprovalNotLikeAndStudentApplication_StudentNumber(Status status, String regno, Pageable pageable);

    //find all by coordinator approval and company's email
    Page<App> findAllByCoordinatorApprovalAndCompanyApplied_Email(Status status, String email, Pageable pageable);

    //find all by coordinator approval and department
    Page<App> findAllByCoordinatorApprovalAndStudentApplication_Department(Status status, String department, Pageable pageable);

    //find all by company approval and department
    Page<App> findAllByCompanyApprovalAndCoordinatorApproval(Status status, Status status1, Pageable pageable);
    Page<App> findAllByCompanyApproval(Status status, Pageable pageable);
    List<App> findAllByCompanyApproval(Status status);

    //find all by date of application
    Page<App> findAllByDateAppliedBetween(Date from, Date to, Pageable pageable);

    //find all by date Coordinator approved
    Page<App> findAllByDateCoApprovedBetween(Date from, Date to, Pageable pageable);

    //find all by date Coordinator approved
    Page<App> findAllByDateCompanyApprBetween(Date from, Date to, Pageable pageable);

    //find application given coordinator , company approval and company applied
    Page<App> findAllByCoordinatorApprovalAndCompanyApprovalAndCompanyApplied(Status status, Status status2, Town town, Pageable pageable);
    Page<App> findAllByCoordinatorApprovalAndCompanyApprovalAndCompanyApplied_Email(Status accepted, Status accepted1, String email, Pageable pageable);
    List<App> findAllByCoordinatorApprovalAndCompanyApprovalAndCompanyApplied_Email(Status accepted, Status accepted1, String email);

    //find all apps by student contains
    Page<App> findAllByStudentApplication_FullNameContains(String name, Pageable pageable);

}


