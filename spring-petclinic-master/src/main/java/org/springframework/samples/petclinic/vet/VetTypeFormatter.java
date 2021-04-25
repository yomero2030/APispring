/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.vet;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

/**
 *
 * @author WSGO
 */
public class VetTypeFormatter implements Formatter<Specialty> {
    private final VetRepository vets;


    @Autowired
    public VetTypeFormatter(VetRepository vets) {
        this.vets = vets;
    }
    
    @Override
    public String print(Specialty specialti, Locale locale) {
        return specialti.getName();
    }

    @Override
    public Specialty parse(String text, Locale locale) throws ParseException {
        Collection<Specialty> findSpecialtys = this.vets.findSpecialtys();
        for (Specialty type : findSpecialtys) {
            if (type.getName().equals(text)) {
                return type;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }
    
}
