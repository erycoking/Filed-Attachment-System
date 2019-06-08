
package com.egerton.bugs.service;



import com.egerton.bugs.Model.Enumerated.Gender;
import com.egerton.bugs.Model.Enumerated.Role;
import com.egerton.bugs.Model.Staff;
import com.egerton.bugs.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

;



@Service
public class StaffService {
	@Autowired
    private StaffRepository staffRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //get all staffs
	public Page<Staff> getAllStaff(Pageable pageable){
        return staffRepo.findAll(pageable);
    }
	
	
	//find staff given payroll number;
    public Staff getCoordinator(String payrollId){
        return staffRepo.findByPayrollId(payrollId);
    }

    //find staff given payroll number;
    public Staff getCoordinatorByEmail(String email){
        return staffRepo.findByEmail(email);
    }

    

 //add new staff
    public void addCoordinator(Staff staff){
        System.out.println(Role.ROLE_ADMIN.toString());
    	//save staff
        staffRepo.save(staff);
    }

    public void  updateCoordinator(Staff staff){
        staffRepo.save(staff);
    }

    public void deleteCoordinator(String id){
        staffRepo.deleteByPayrollId(id);
    }

    //find by name
    public Page<Staff> getAllByFullNameContains(String name, Pageable pageable){
        return staffRepo.findAllByFullNameContains(name, pageable);
    }

    //find by payrollno
    public Page<Staff> getAllByPayrollId(String id, Pageable pageable){
        return staffRepo.findAllByPayrollId(id, pageable);
    }

    //find all by faculty
    public Page<Staff> getAllByFaculty(String faculty, Pageable pageable){
        return staffRepo.findAllByFaculty(faculty, pageable);
    }

    //find all by department
    public Page<Staff> getAllByDepartment(String department, Pageable pageable){
        return staffRepo.findAllByDepartment(department, pageable);
    }

    //find all by gender
    public Page<Staff> getAllByGender(Gender gender, Pageable pageable){
        return staffRepo.findAllByGender(gender, pageable);
    }
}

