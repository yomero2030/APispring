/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.user;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.UniqueConstraint;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.owner.Owner;

/**
 *
 * @author AugustoRuCle
 */
@Entity
@Table(name = "users")

public class User extends Person {

    @Column(name = "email", unique = true)    
    @NotEmpty
    @Email    
    private String email;

    @Column(name = "password")
    @NotEmpty
    private String password;

    @Column(name = "telephone")
    @Size(min = 0, max = 10)
    private String telephone;

    @Column(name = "active")
    @NotEmpty
    @Max(1)
    private String active;

    @Column(name = "zipcode")
    @NotEmpty
    @Size(min = 5, max = 10)
    private String zipcode;

    @Column(name = "city")
    @NotEmpty
    private String city;
    
    /////////////////kevin
    @OneToOne
    @PrimaryKeyJoinColumn
    private Owner owner;
    ///////////////

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getActive() {
        return this.active;
    }
    
    public boolean isEnabled() {
        if(this.active.compareTo("1") == 0){
            return true;
        }
        return false;        
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

//    @Override
//    public String toString() {
//        return new ToStringCreator(this)
//                .append("id", this.getId()).append("new", this.isNew())
//                .append("lastName", this.getLastName())
//                .append("firstName", this.getFirstName())
//                .append("email", this.getEmail())
//                .append("active", this.getActive())
//                .append("password", this.getPassword())
//                .append("zipcode", this.getZipcode())
//                .append("telephone", this.getTelephone())
//                .append("city", this.getCity())
//                .toString();
//    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Collection<Role> roles) {
        this.roles = roles;
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("User [id=").append(this.getId()).append(", firstName=").append(this.getFirstName()).append(", lastName=").append(this.getLastName()).
                append(", email=").append(email).append(", password=").append(password).append(", enabled=").append("true").append(", secret=").
                append(", roles=").append(roles).
                append(", city=").append(city).
                append(", zipcode=").append(zipcode)
                .append("]");
        return builder.toString();
    }

}
