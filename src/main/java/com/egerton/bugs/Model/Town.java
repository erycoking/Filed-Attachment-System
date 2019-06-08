package com.egerton.bugs.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;


@Entity
@Table(name = "town")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Town {
    @Id
   /* @GenericGenerator(name = "sequence_town_id", strategy = "com.egerton.bugs.IdGenerator.TownIdGenerator")
    @GeneratedValue(generator = "sequence_town_id")*/
    @Column(name = "Townid", nullable = false, unique = true, length = 6)
    @JsonView(View.Summary.class)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long townId;

    @Column(name = "region", nullable = false, length = 100)
    @NotEmpty(message = "Please provide location")
    @JsonView(View.Summary.class)
    private String region;

    @Column(name = "location", nullable = false, length = 100)
    @NotEmpty(message = "Please provide location")
    @JsonView(View.Summary.class)
    private String location;

    @Column(name = "address", length = 255)
    @NotEmpty(message = "Please provide company address")
    @JsonView(View.Summary.class)
    private String address;


    @Column(name = "Companyemail", nullable = false, unique = true, length = 100)
    @Email(message = "Please provide a valid e-mail")
    @NotEmpty(message = "Please provide an e-mail")
    @JsonView(View.Summary.class)
    private String email;

    @Column(name = "contactPerson", columnDefinition = "varchar(100) default 'HR'")
    @JsonView(View.Summary.class)
    private String contactPerson = "HR";


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private CompanyAccount companyAccount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "Company")
    private Company company;

    public Town() {
    }

    public Town(Company company, String region, String location, String address, String email) {
        this.region = region;
        this.location = location;
        this.address = address;
        this.email = email;
        this.company = company;
    }

    public Town(String region, String location, String address, String email, CompanyAccount companyAccount, Company company) {
        this.region = region;
        this.location = location;
        this.address = address;
        this.email = email;
        this.companyAccount = companyAccount;
        this.company = company;
    }

    public CompanyAccount getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(CompanyAccount companyAccount) {
        this.companyAccount = companyAccount;
    }

    public Long getTownId() {
        return townId;
    }

    public void setTownId(Long townId) {
        this.townId = townId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Town{" +
                "townId=" + townId +
                ", region='" + region + '\'' +
                ", location='" + location + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", company=" + company +
                '}';
    }
}
