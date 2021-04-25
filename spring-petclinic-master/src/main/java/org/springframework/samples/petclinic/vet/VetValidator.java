/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.vet;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author WSGO
 */
public class VetValidator implements Validator {
    private static final String REQUIRED = "required";

    @Override
    public void validate(Object obj, Errors errors) {
        Vet vet = (Vet) obj;
        String name = vet.getFirstName();
        // name validation
        if (!StringUtils.hasLength(name)) {
            errors.rejectValue("name", REQUIRED, REQUIRED);
        }

//        // type validation
//        if (pet.isNew() && pet.getType() == null) {
//            errors.rejectValue("type", REQUIRED, REQUIRED);
//        }
//
//        // birth date validation
//        if (pet.getBirthDate() == null) {
//            errors.rejectValue("birthDate", REQUIRED, REQUIRED);
//        }
    }

    /**
     * This Validator validates *just* Pet instances
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Vet.class.isAssignableFrom(clazz);
    }

}
