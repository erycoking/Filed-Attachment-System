
package com.egerton.bugs.Model;


import com.egerton.bugs.Model.Enumerated.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "companyaccount")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CompanyAccount {
	private static final int EXPIRATION = 60 * 6;
	 
	@Id	
	@Column(name = "townid",nullable = false)
	private Long townId;

	@Column(name = "password",length = 64)
    private String password;



	@Column(name = "lastlogin")
    @Temporal(TemporalType.DATE)
	private Date lastLogin;

    @Column(name = "role",columnDefinition = "enum( 'ROLE_ADMIN','ROLE_STUDENT','ROLE_BUGS','ROLE_STAFF','ROLE_COORDINATOR', 'ROLE_COMPANY') ")
    @Enumerated(value = EnumType.STRING)
	private Role role;


   @MapsId
    @OneToOne(targetEntity = Town.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "town")
    private Town town;

    
    public CompanyAccount(){}

    public CompanyAccount(Long townId, String password, Date lastLogin, Role role, Town town) {
        this.townId = townId;
        this.password = password;
        this.lastLogin = lastLogin;
        this.role = role;
        this.town = town;
    }

    public CompanyAccount(String password, Role role,Town town) {
        this.password = password;

        this.role = role;
        this.town =town;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }


    public Long getTownId() {
        return townId;
    }

    public void setTownId(Long townId) {
        this.townId = townId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public Collection<? extends GrantedAuthority > getAuthorities() {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        Set<Role> roles = new HashSet<>();

        roles.add(getRole());
        roles.forEach(role -> {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.toString()));
        });

        return simpleGrantedAuthorities;
    }
}

