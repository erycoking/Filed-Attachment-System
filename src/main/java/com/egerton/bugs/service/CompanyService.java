
package com.egerton.bugs.service;

import com.egerton.bugs.Model.Company;
import com.egerton.bugs.Model.Enumerated.Role;
import com.egerton.bugs.Model.Town;
import com.egerton.bugs.repositories.CompanyRepository;
import com.egerton.bugs.repositories.TownRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private TownRepository townRepository;


    //find company based on company email
    public Company getCompany(String email) {
        return companyRepository.findByTown_email(email);
    }


    //find company given keyword corresponding to company name
    public List<Company> contains(String companyName) {
        List<Company> company = new ArrayList<Company>();
        company = companyRepository.findAllByCompanyNameContaining(companyName);
        return company;
    }

    //find company given keyword corresponding to location name
    public List<Town> containsTown(Long companyId, String location) {
        List<Town> town = new ArrayList<Town>();
        town = townRepository.findAllByCompany_companyIdAndLocationContaining(companyId, location);
        return town;
    }

    //find company given keyword corresponding to location name
    public List<Town> containsTown(Long companyId, String location, Pageable pageable) {
        List<Town> town = new ArrayList<Town>();
        town = townRepository.findAllByCompany_companyIdAndLocationContaining(companyId, location);
        return town;
    }

    //find all companies
    public List<Company> getCompanies() {
        List<Company> companies = new ArrayList<>();
        companyRepository.findAll().forEach(companies::add);
        return companies;
    }


    //Find town containing email and town name provided
    public Town findTown(String email, String location) {
        return townRepository.findTownByEmailAndLocationContaining(email, location);
    }

    //Find locations given company name
    public List<Town> locations(String companyName) {
        List<Town> locations = new ArrayList<Town>();
        locations = townRepository.findAllByCompany_companyName(companyName);
        return locations;
    }

    //Find company location given company email
    public Town getTown(String email){
        return  townRepository.findByEmail(email);
    }

    //Find company given company name
    public Company findCompanyByName(String name) {
        return companyRepository.findByCompanyName(name);
    }

    public boolean save(Company company) {
        Company company1 = companyRepository.findByCompanyName(company.getCompanyName());
        Town town1;
        if (company1 != null){
            for (Town e : company.getTown()){
                if((town1 = townRepository.findByEmail(e.getEmail())) != null){
                    town1.setCompany(company1);
                    town1.setLocation(e.getLocation());
                    town1.setAddress(e.getAddress());
                    townRepository.save(town1);
                }else{
                    e.setCompany(company1);
                    townRepository.save(e);
                }
            }
            return true;
        }else{
            if (companyRepository.save(company) != null)
                return true;
            else
                return false;
        }
    }

    //save town given town object
    public void saveTown(Town town){
        townRepository.save(town);
    }


    public void saveCompany(Town town) {
        String s = town.getCompany().getCompanyName();
        Company company = companyRepository.findByCompanyName(s);
        if (company != null){
            town.setCompany(company);
            townRepository.save(town);
        }else{
            Company company1 = new Company();
            company1.setCompanyName(s);
            company1.getTown().add(town);
            town.setCompany(company1);
            companyRepository.save(company1);
        }
    }

    //find all towns
    public Page<Town> getAllTowns(Pageable pageable){
        return townRepository.findAll(pageable);
    }

    //find all by company name
    public Page<Town> getAllByCompany_CompanyName(String name, Pageable pageable){
        return townRepository.findAllByCompany_CompanyName(name, pageable);
    }

    //find all by location
    public Page<Town> getAllByLocation(String location, Pageable pageable){
        return townRepository.findAllByLocation(location, pageable);
    }

    //find all by email
    public Page<Town> getAllByEmail(String email, Pageable pageable){
        return townRepository.findAllByEmail(email, pageable);
    }
}

