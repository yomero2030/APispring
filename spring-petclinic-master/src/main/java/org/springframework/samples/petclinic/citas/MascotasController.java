/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.citas;

import java.util.Collection;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author legad
 */
public class MascotasController {
    
    private final MascotasRepository mascotas;
    
    public MascotasController(MascotasRepository clinicService) {
        this.mascotas = clinicService;
    }
    
    public MascotasRepository getMascotas() {
        return mascotas;
    }
    
    @ModelAttribute("mascotas")
    public Collection<Mascotas> populateMascotas() {
        return this.mascotas.getMascotas();
    }
    
}
