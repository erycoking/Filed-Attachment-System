package com.egerton.bugs.Model;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;




@Entity
@Table(name="company")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Company {
	
    @Id
    /*@GenericGenerator(name = "sequence_company_id", strategy = "com.egerton.bugs.IdGenerator.CompanyIdGenerator")
    @GeneratedValue(generator = "sequence_company_id")*/
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(View.Summary.class)
	@Column(name = "Companyid",unique = true,nullable = false,length = 6)
	private Long companyId;
	
	@Column(name = "Companyname", nullable = false,unique = true,length = 100)
	@NotEmpty(message="Please provide company name")
	@JsonView(View.Summary.class)
	private String companyName;

	@Column(name = "industry", length = 100, nullable = true)
	@Pattern(regexp = "([a-zA-Z]+\\s)*")
	@JsonView(View.Summary.class)
	private String industry;

	@Column(name = "sector", length = 100, nullable = true)
	@Pattern(regexp = "([a-zA-Z]+\\s)*")
	@JsonView(View.Summary.class)
	private String sector;
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "company")
	@JsonView(View.Summary.class)
	@JsonBackReference
	private List<Town> town = new ArrayList<>();
	
	public Company() {}

	public Company(String companyName) {
		this.companyName = companyName;
	}

	public Company(String companyName, ArrayList<Town> town) {
		this.companyName = companyName;
		this.town = town;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public void setTown(List<Town> town) {
		this.town = town;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public List<Town> getTown() {
		return town;
	}

	public void setTown(ArrayList<Town> town) {
		this.town = town;
	}

	@Override
	public String toString() {
		return "Company{" +
				"companyId=" + companyId +
				", companyName='" + companyName + '\'' +
				", industry='" + industry + '\'' +
				", sector='" + sector + '\'' +
				'}';
	}
}
