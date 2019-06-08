
package com.egerton.bugs.Model;

import java.util.*;
import javax.persistence.*;

import com.egerton.bugs.Model.Enumerated.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "application")
public class App {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "applicationId",precision = 5)
	private Long applicationId;

	@Column(name ="CoordinatorApproval",columnDefinition = "enum('PENDING','REJECTED','ACCEPTED') default 'PENDING'")
	@Enumerated(EnumType.STRING)
	private Status coordinatorApproval;


	@Column(name ="CompanyApproval",columnDefinition = "enum('PENDING','REJECTED','ACCEPTED') default 'PENDING'")
	@Enumerated(EnumType.STRING)
	private Status companyApproval;

	@Column(name = "dateApplied")
	@Temporal(TemporalType.DATE)
	private Date dateApplied;

	@Column(name="dateCoordinatorApproved")
	@Temporal(TemporalType.DATE)
	private Date dateCoApproved;

	@Column(name="dateCompanyApproved")
	@Temporal(TemporalType.DATE)
	private Date dateCompanyAppr;


	@Column(name = "CoordinatorFeedback", columnDefinition = "TEXT DEFAULT NULL")
	private String coordinatorFeedback;


	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "companyApplied")
	private Town companyApplied;

//	@ManyToOne
//	@JoinColumn(name = "company", nullable = false)
//	private Company company;

	@ManyToOne
	@JoinColumn(name = "studentApplication")
	@JsonIgnore
	private Student studentApplication;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "companyFeedBack")
	private CompanyFeedback companyFeedback;

	public App(){}

	public App(Status coordinatorApproval, Status companyApproval, Date dateApplied, Date dateCoApproved, Date dateCompanyAppr, String coordinatorFeedback, Town companyApplied, Student studentApplication) {
		this.coordinatorApproval = coordinatorApproval;
		this.companyApproval = companyApproval;
		this.dateApplied = dateApplied;
		this.dateCoApproved = dateCoApproved;
		this.dateCompanyAppr = dateCompanyAppr;
		this.coordinatorFeedback = coordinatorFeedback;
		this.companyApplied = companyApplied;
//		this.company = company;
		this.studentApplication = studentApplication;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Status getCoordinatorApproval() {
		return coordinatorApproval;
	}

	public void setCoordinatorApproval(Status coordinatorapproval) {
		this.coordinatorApproval = coordinatorapproval;
	}

	public Status getCompanyApproval() {
		return companyApproval;
	}

	public void setCompanyApproval(Status companyApproval) {
		this.companyApproval = companyApproval;
	}

	public Date getDateApplied() {
		return dateApplied;
	}

	public void setDateApplied(Date dateApplied) {
		this.dateApplied = dateApplied;
	}

	public Date getDateCoApproved() {
		return dateCoApproved;
	}

	public void setDateCoApproved(Date dateCoApproved) {
		this.dateCoApproved = dateCoApproved;
	}

	public Date getDateCompanyAppr() {
		return dateCompanyAppr;
	}

	public void setDateCompanyAppr(Date dateCompanyAppr) {
		this.dateCompanyAppr = dateCompanyAppr;
	}

	public String getCoordinatorFeedback() {
		return coordinatorFeedback;
	}

	public void setCoordinatorFeedback(String coordinatorFeedback) {
		this.coordinatorFeedback = coordinatorFeedback;
	}


	public Town getCompanyApplied() {
		return companyApplied;
	}

	public void setCompanyApplied(Town companyApplied) {
		this.companyApplied = companyApplied;
	}

	/*public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
*/
	public Student getStudentApplication() {
		return studentApplication;
	}

	public void setStudentApplication(Student studentApplication) {
		this.studentApplication = studentApplication;
	}


	public CompanyFeedback getCompanyFeedback() {
		return companyFeedback;
	}

	public void setCompanyFeedback(CompanyFeedback companyFeedback) {
		this.companyFeedback = companyFeedback;
	}

	@Override
	public String toString() {
		return "App{" +
				"applicationId=" + applicationId +
				", coordinatorApproval=" + coordinatorApproval +
				", companyApproval=" + companyApproval +
				", dateApplied=" + dateApplied +
				", dateCoApproved=" + dateCoApproved +
				", dateCompanyAppr=" + dateCompanyAppr +
				", coordinatorFeedback='" + coordinatorFeedback + '\'' +
				", companyApplied=" + companyApplied +
				", studentApplication=" + studentApplication.getFullName() +
				'}';
	}
}

