

package com.egerton.bugs.Model;


import com.egerton.bugs.repositories.StaffRepository;
import com.egerton.bugs.repositories.StudentRepository;
import com.egerton.bugs.repositories.TownRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUser {
	private StudentRepository studentRepo;
    private StaffRepository staffRepo; 	
    private TownRepository townRepository;

	private Town town;
	private Student student;
	private Staff staff;

    @Autowired
    public CustomUser(StudentRepository studentRepo, StaffRepository staffRepo, TownRepository townRepository) {
        this.studentRepo = studentRepo;
        this.staffRepo = staffRepo;
        this.townRepository = townRepository;
    }

	public User getUser(String username){

		try {
            if ((town = townRepository.findByEmail(username)) != null) {
				return new User(town.getEmail(), town.getCompanyAccount().getPassword(),
						true, true, true,
						true, town.getCompanyAccount().getAuthorities());

            }else if((student = studentRepo.findByStudentNumber(username)) != null) {
				return new User(student.getStudentNumber(), student.getPassword(), true,
						true, true, true,
						student.getAuthorities());

			}else if ((staff = staffRepo.findByPayrollId(username)) != null){
				return new User(staff.getPayrollId(), staff.getPassword(), true,
						true, true, true,
						staff.getAuthorities());
            }else {
				throw new UsernameNotFoundException("user not found");
			}

        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
	}






}


