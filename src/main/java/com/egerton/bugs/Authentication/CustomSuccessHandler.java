 package com.egerton.bugs.Authentication;

import com.egerton.bugs.Model.Enumerated.Role;
import com.egerton.bugs.Model.Staff;
import com.egerton.bugs.Model.Student;
import com.egerton.bugs.Model.Town;
import com.egerton.bugs.repositories.StaffRepository;
import com.egerton.bugs.repositories.StudentRepository;
import com.egerton.bugs.repositories.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private StudentRepository studentRepo;
    private StaffRepository staffRepo;
    private TownRepository townRepository;

    private String targetUrl;

    @Autowired
    public CustomSuccessHandler(StudentRepository studentRepo,
                                StaffRepository staffRepo,
                                TownRepository townRepository) {

        this.studentRepo = studentRepo;
        this.staffRepo = staffRepo;
        this.townRepository = townRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        String id = authentication.getName();
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        HttpSession session = request.getSession();

        if (authorities.contains(Role.ROLE_COORDINATOR.toString())){
            Staff staff = staffRepo.findByPayrollId(id);
            session.setAttribute("staff", staff);
            targetUrl ="/staff/home";
        }else if (authorities.contains(Role.ROLE_STUDENT.toString())){
            Student student = studentRepo.findByStudentNumber(id);
            session.setAttribute("student", student);
            targetUrl = "/student/home";
        }else if (authorities.contains(Role.ROLE_COMPANY.toString())){
            Town town = townRepository.findByEmail(id);
            session.setAttribute("company", town);
            targetUrl = "/company/home";
        }else if (authorities.contains(Role.ROLE_ADMIN.toString())){
            Staff staff = staffRepo.findByPayrollId(id);
            session.setAttribute("staff", staff);
            targetUrl = "/admin/home";
        }else if (authorities.contains(Role.ROLE_BUGS.toString())){
            Staff staff = staffRepo.findByPayrollId(id);
            session.setAttribute("staff", staff);
            targetUrl = "/bugs/home";
        }else {
            targetUrl = "/";
        }

        response.sendRedirect(request.getContextPath() + targetUrl);

    }
    
}
