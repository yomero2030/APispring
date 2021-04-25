/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.owner;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.samples.petclinic.model.NamedEntity;

@XmlRootElement
public class Pets {

    private List<Pet> vets;

    @XmlElement
    public List<Pet> getPetList() {
        if (vets == null) {
            vets = new ArrayList<>();
        }
        return vets;
    }

}
