/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.citas;

import java.util.Collection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import javax.validation.Valid;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author legad
 */
public class EspecialidadesController {
    
    private final EspecialidadesRepository especialidades;
    
    public EspecialidadesController(EspecialidadesRepository clinicService) {
        this.especialidades = clinicService;
    }
    
    public EspecialidadesRepository getVets() {
        return especialidades;
    }
    
    @ModelAttribute("especialidades")
    public Collection<Especialidades> populateEspecialidades() {
        return this.especialidades.getEspecialidades();
    }
}
