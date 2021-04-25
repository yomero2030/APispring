/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.compra;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.owner.Owner;

/**
 *
 * @author Faabian
 */

@Entity
@Table(name = "compra")
public class Compra extends BaseEntity{

     
    @Column(name = "name")
    @NotEmpty
    private String name;

    @Column(name = "description")
    @NotEmpty
    private String description;

    @Column(name = "price")
    @NotNull
    @Positive
    private Double price;
    
    @Column(name = "formaPago")
    @NotNull
    private String formaPago;
    
    
    @Column(name = "fecha")
    @NotNull
    private String fecha;
    
    
    //relacion compra Usuario
    @OneToOne(fetch = FetchType.EAGER,
                cascade= CascadeType.ALL)
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
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    public String getFormaPago(){
        return formaPago;
    }
    
    public void setFormaPago(String formapago){
        this.formaPago = formaPago;
    }
    
    
    @Override
    public String toString(){
        return new ToStringCreator(this)
                .append("id", this.getId())
                .append("name", this.name)
                .append("formaPago", this.formaPago)
                .append("fecha", this.fecha)
                .append("precio", this.price)
                .append("descripcion", this.description)
                .toString();
                
    }


}
    

