package com.egerton.bugs.repositories;

import com.egerton.bugs.Model.CompanyAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyAccountRepository extends JpaRepository<CompanyAccount,Long> {
}
