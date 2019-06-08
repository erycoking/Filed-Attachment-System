package com.egerton.bugs.service;

import com.egerton.bugs.Model.Company;
import com.egerton.bugs.Model.Town;
import com.egerton.bugs.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class InitializeCompaniesAndTown {

    @Autowired
    private CompanyRepository companyRepo;

    public void initCompaniesAndTown(){

        Company company = new Company("42 Geomatic Services Ltd");
        company.setTown(Arrays.asList(
                new Town(company, "Nairobi", "Nairobi", "Professional Centre, Ground Flr. Room No. 2 Ngong Road",  "daniel@42geomatics.com")
        ));
        companyRepo.save(company);

        Company company1 = new Company("Abu Engineering Ltd");
        company1.setTown(Arrays.asList(
                new Town(company1, "Nairobi", "Nairobi", "Nairobi",  "info@abuengineering.org")
        ));
        companyRepo.save(company1);

        Company company2 = new Company("Acme Container Ltd");
        company2.setTown(Arrays.asList(
                new Town(company2, "Nairobi", "Nairobi", "Red Hill Road",  "info@acmecontainers.com")
        ));
        companyRepo.save(company2);

        Company company3 = new Company("Adhesive Solutions Africa Ltd");
        company3.setTown(Arrays.asList(
                new Town(company3, "Nairobi", "Nairobi", "Vinodeep Twrs, 2nd Fl, Baricho Rd, Ind. Area",  "info@adhesives.co.ke")
        ));
        companyRepo.save(company3);


        Company company4 = new Company("Africa Oil Kenya B.V");
        company4.setTown(Arrays.asList(
                new Town(company4, "Nairobi", "Nairobi", "Nairobi",  "africaoilcorp@namdo.com")
        ));
        companyRepo.save(company4);


        Company company5 = new Company("African Cotton Industries Ltd");
        company5.setTown(Arrays.asList(
                new Town(company5, "Nairobi", "Nairobi", "Mombasa road",  "info@kenyacompanies.com")
        ));
        companyRepo.save(company5);


        Company company6 = new Company("Agni Enterprises Ltd");
        company6.setTown(Arrays.asList(
                new Town(company6, "Mombasa", "Mombasa", "Haile Selassie Ave",  "agni@alfakenya.com")
        ));
        companyRepo.save(company6);


        Company company7 = new Company("Ali Glaziers Ltd");
        company7.setTown(Arrays.asList(
                new Town(company7, "Nairobi", "Nairobi", "Funzi Rd, Industrial Area",  "info@aliglaziers.com")
        ));
        companyRepo.save(company7);


        Company company8 = new Company("Alpha Dairy Products Ltd");
        company8.setTown(Arrays.asList(
                new Town(company8, "Nairobi", "Nairobi", "Industrial Area,Off Enterprise Road,Road ‘A’",  "affl@alphafinefoods.com")
        ));
        companyRepo.save(company8);


        Company company9 = new Company("Apex Steel Ltd");
        company9.setTown(Arrays.asList(
                new Town(company9, "Nairobi", "Nairobi", "Funzi Road, Off Enterprise road Industrial Area",  "info@apex-steel.com")
        ));
        companyRepo.save(company9);


        Company company10 = new Company("Bamburi Special Products Ltd");
        company10.setTown(Arrays.asList(
                new Town(company10, "Nairobi", "Nairobi", "Kitui Road, Off Kampala Road, Industrial Area, Nairobi, Kenya",  "info@bamburi.lafarge.com")
        ));
        companyRepo.save(company10);


        Company company11 = new Company("BIDCO Oil Refineries Limited");
        company11.setTown(Arrays.asList(
                new Town(company11, "Nairobi", "Thika", "Thika Town, Kenya",  "thika@bidco-oil.com")
        ));
        companyRepo.save(company11);


        Company company12 = new Company("Bilco Engineering");
        company12.setTown(Arrays.asList(
                new Town(company12, "Nairobi", "Nairobi", "Baba Dogo Road, Ruaraka",  "salesinfo@bilcoengineering.com")
        ));
        companyRepo.save(company12);


        Company company14 = new Company("Chandaria Industries Limited");
        company14.setTown(Arrays.asList(
                new Town(company14, "Nairobi", "Nairobi", "Baba Dogo Rd, Ruaraka, Nairobi",  "info@chandaria.com")
        ));
        companyRepo.save(company14);


    }
}
