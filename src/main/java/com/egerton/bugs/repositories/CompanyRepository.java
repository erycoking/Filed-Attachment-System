
package com.egerton.bugs.repositories;

import com.egerton.bugs.Model.Company;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Page<Company> findAllByCompanyNameContaining(String companyName, Pageable pageable);

    List<Company> findAllByCompanyNameContaining(String companyName);

    Company findByCompanyName(String companyName);

    Company findByTown_email(String email);


}

