/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.product;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.samples.petclinic.vet.Vet;

/**
 *
 * @author Faabian
 */
@XmlRootElement
public class Products {
     private List<Vet> products;

    @XmlElement
    public List<Vet> getVetList() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }
}
