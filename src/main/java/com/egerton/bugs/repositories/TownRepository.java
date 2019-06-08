
package com.egerton.bugs.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.egerton.bugs.Model.Town;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {

	List<Town> findAllByCompany_companyIdAndLocationContaining(Long companyId, String location);
	Town findTownByEmailAndLocationContaining(String email, String Location);
	Town findByEmail(String email);
	List<Town> findAllByCompany_companyName(String companyName);

	//find all by company name
	Page<Town> findAllByCompany_CompanyName(String name, Pageable pageable);

	//find all by location
	Page<Town> findAllByLocation(String location, Pageable pageable);

	//find all by email
	Page<Town> findAllByEmail(String email, Pageable pageable);


}

