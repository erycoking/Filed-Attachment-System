package com.egerton.bugs.repositories;

import com.egerton.bugs.Model.CompanyFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyFeedbackRepository extends JpaRepository<CompanyFeedback, Integer> {
}
