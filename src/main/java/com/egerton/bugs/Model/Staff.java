package com.egerton.bugs.Model;

import com.egerton.bugs.Model.Enumerated.Gender;
import com.egerton.bugs.Model.Enumerated.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;


@Component
@Entity(name = "staff")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Staff  {
	
	@Id
    @Column(name = "payrollid", length = 10, nullable = false,unique = true)
    @NotEmpty(message = "Please provide your payroll id.")
    @Pattern(regexp = "\\d{5}", message = "Pay roll number must be at least 5 digits.")
    private String payrollId;

	@Column(name = "staffname", length = 30, nullable = false)
	@Size(min = 3, message = "Name should at least be 3 characters")
	@NotEmpty(message = "Please provide your full name.")
	private String fullName;

	@Column(name = "password",length = 64)
	private String password;

	@Email(message = "Invalid email")
	@Column(name = "email", length = 30, nullable = false,unique = true)
	@NotEmpty(message = "Please provide your email")
	private String email;


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "faculty", nullable = false)
	private Faculty faculty;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department", nullable = false)
	private Department department;

	@Column(name = "gender", length = 10, columnDefinition = "enum('MALE','FEMALE') ")
	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Column(name = "coordinatorLevel", length = 30, nullable = false)
	@Size(min = 3, message = "Name should at least be 3 characters")
	@NotEmpty(message = "Please provide your coordinator level.")
	private String coordinatorLevel = "Department";


	@JsonIgnore
	@Column(name = "role", length = 20, columnDefinition = "enum( 'ROLE_ADMIN','ROLE_STUDENT','ROLE_BUGS','ROLE_STAFF', 'ROLE_COMPANY', 'ROLE_COORDINATOR')")
	@Enumerated(value = EnumType.STRING)
	private Role role;


    public Staff(){}

	public Staff(String payrollId, String fullName, String password, String email, Faculty faculty, Department department, Gender gender, Role role) {
		this.payrollId = payrollId;
		this.fullName = fullName;
		this.password = password;
		this.email = email;
		this.faculty = faculty;
		this.department = department;
		this.gender = gender;
		this.role = role;
	}

	public Staff(String payrollId, String fullName, String password, String email, Faculty faculty, Department department, Gender gender, String coordinatorLevel, Role role) {
		this.payrollId = payrollId;
		this.fullName = fullName;
		this.password = password;
		this.email = email;
		this.faculty = faculty;
		this.department = department;
		this.gender = gender;
		this.coordinatorLevel = coordinatorLevel;
		this.role = role;
	}

	public String getPayrollId() {
		return payrollId;
	}

	public void setPayrollId(String payrollId) {
		this.payrollId = payrollId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getCoordinatorLevel() {
		return coordinatorLevel;
	}

	public void setCoordinatorLevel(String coordinatorLevel) {
		this.coordinatorLevel = coordinatorLevel;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Staff{" +
				"payrollId='" + payrollId + '\'' +
				", fullName='" + fullName + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", faculty=" + faculty +
				", department=" + department +
				", gender=" + gender +
				", coordinatorLevel='" + coordinatorLevel + '\'' +
				", role=" + role +
				'}';
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
		Set<Role> roles = new HashSet<>();

		roles.add(getRole());
		roles.forEach(role -> {
			simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.toString()));
		});

		return simpleGrantedAuthorities;
	}



    
}
