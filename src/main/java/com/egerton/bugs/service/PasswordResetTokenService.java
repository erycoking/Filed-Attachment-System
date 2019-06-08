package com.egerton.bugs.service;

import com.egerton.bugs.Model.PasswordResetToken;
import com.egerton.bugs.Model.Student;
import com.egerton.bugs.Model.Staff;
import com.egerton.bugs.Model.Town;
import com.egerton.bugs.repositories.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class PasswordResetTokenService {
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private CompanyService companyService;

    /*Create password  reset verification token*/
    public void createVerificationToken(final String userId, final String resetToken) {
        if(passwordResetTokenRepository.findByUser(userId) != null){
            PasswordResetToken eToken = passwordResetTokenRepository.findByUser(userId);
            eToken.updateToken(resetToken);
            passwordResetTokenRepository.save(eToken);

        }else {
            final PasswordResetToken token = new PasswordResetToken(resetToken, userId);
            passwordResetTokenRepository.save(token);
        }
    }

    //Find PasswordResetToke given  token
    public PasswordResetToken findByToken(String token){
        return passwordResetTokenRepository.findByToken(token);
    }

    //Validate password reset token
    public String validatePasswordResetToken( String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);
        return passToken.getUser();
    }

}
