/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.model.Person;

/**
 * Simple JavaBean domain object representing a veterinarian.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Arjen Poutsma
 */
@Entity
@Table(name = "vets")
public class Vet extends Person {

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"), inverseJoinColumns = @JoinColumn(name = "specialty_id"))
//    private Set<Specialty> specialties;
    
    @Column(name = "telephone")
    @NotEmpty
    private String telephone;
    
    @Column(name = "Schedule")
    @NotEmpty
    private String schedule;

    @Column(name = "specialty_id")
    @NotEmpty
    private String specialty;
    
    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
  

//    protected Set<Specialty> getSpecialtiesInternal() {
//        if (this.specialties == null) {
//            this.specialties = new HashSet<>();
//        }
//        return this.specialties;
//    }
//
//    protected void setSpecialtiesInternal(Set<Specialty> specialties) {
//        this.specialties = specialties;
//    }

//    @XmlElement
//    public List<Specialty> getSpecialties() {
//        List<Specialty> sortedSpecs = new ArrayList<>(getSpecialtiesInternal());
//        PropertyComparator.sort(sortedSpecs,
//                new MutableSortDefinition("name", true, true));
//        return Collections.unmodifiableList(sortedSpecs);
//    }
//
//    public int getNrOfSpecialties() {
//        return getSpecialtiesInternal().size();
//    }
//
//    public void addSpecialty(Specialty specialty) {
//        getSpecialtiesInternal().add(specialty);
//    }

    /**
     * @return the specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * @param specialty the specialty to set
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    
     @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("id", this.getId()).append("new", this.isNew())
                .append("lastName", this.getLastName())
                .append("firstName", this.getFirstName())
                .append("telephone",this.getTelephone())
                .append("schedule",this.getSchedule())
                .append("specialty",this.getSpecialty()).toString();
        
    }

    String getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
