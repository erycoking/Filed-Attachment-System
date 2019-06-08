package com.egerton.bugs;



import com.egerton.bugs.Model.Department;
import com.egerton.bugs.Model.Enumerated.Gender;
import com.egerton.bugs.Model.Enumerated.Role;
import com.egerton.bugs.Model.Faculty;
import com.egerton.bugs.Model.Staff;
import com.egerton.bugs.repositories.FacultyRepository;
import com.egerton.bugs.repositories.StaffRepository;
import com.egerton.bugs.service.InitializeCompaniesAndTown;
import com.egerton.bugs.service.InitializeFacultyAndDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;


@SpringBootApplication
public class BugsApplication {

	@Autowired
	private FacultyRepository facultyRepo;

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private InitializeFacultyAndDepartment initializeFacultyAndDepartment;

	@Autowired
	private InitializeCompaniesAndTown initializeCompaniesAndTown;

	public static void main(String[] args) {
		SpringApplication.run(BugsApplication.class, args);
	}	
		
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

	/*@Bean
	CommandLineRunner runner(){
		return (String...args) -> {
			initializeFacultyAndDepartment.initFacultyAndDepartment();
			Faculty faculty = new Faculty();
			faculty.setFacultyName("BUGS");
			faculty.setDepartment(Arrays.asList(
					new Department("BUGS", faculty)
			));
			facultyRepo.save(faculty);
			initializeCompaniesAndTown.initCompaniesAndTown();
			staffRepository.save(new Staff("54321", "Admin", new BCryptPasswordEncoder().encode("admin@1234"), "admin@admin.com", faculty, faculty.getDepartment().get(0), Gender.MALE, Role.ROLE_BUGS));
		};
	}*/
}