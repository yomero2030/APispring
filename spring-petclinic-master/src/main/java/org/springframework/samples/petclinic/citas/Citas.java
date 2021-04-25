/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.citas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.owner.Owner;

/**
 *
 * @author legad
 */

@Entity
@Table(name = "citas")

public class Citas extends BaseEntity{
    @Column(name = "fecha")
    @NotEmpty
    private String fecha;
    
    @Column(name = "hora")
    @NotEmpty
    private String hora;
    
    @Column(name = "mascota")
    @NotEmpty
    private String mascota;
    
    @Column(name = "especialidad")
    @NotEmpty
    private String especialidad;
    
    @Column(name = "confirmacion")
    private Integer confirmacion;
    
    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Owner owner;
    
    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    
    public String getFecha(){
        return this.fecha;
    }
    
    public void setFecha(String fecha){
        this.fecha = fecha;
    }
    
    public String getHora(){
        return this.hora;
    }
    
    public void setHora(String hora){
        this.hora = hora;
    }
    
    public String getMascota(){
        return this.mascota;
    }
    
    public void setMascota(String mascota){
        this.mascota = mascota;
    }
    
    public String getEspecialidad(){
        return this.especialidad;
    }
    
    public void setEspecialidad(String especialidad){
        this.especialidad = especialidad;
    }
    
    public Integer getConfirmacion(){
        return this.confirmacion;
    }
    
    public void setConfirmacion(Integer confirmacion){
        this.confirmacion = confirmacion;
    }
    
    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId())
                //.append("owner_id",this.owner.getId())
                .append("new", this.isNew())
                .append("fecha", this.fecha)
                .append("hora", this.hora)
                .append("mascota",this.mascota)
                .append("especialidad",this.especialidad)
                .append("confirmacion",this.confirmacion)
                
                .toString();
    }
}
