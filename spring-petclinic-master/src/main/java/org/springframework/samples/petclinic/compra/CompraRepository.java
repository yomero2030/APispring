/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.compra;

import java.util.Collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.citas.Citas;
import org.springframework.samples.petclinic.citas.Mascotas;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Faabian
 */
public interface CompraRepository extends Repository<Compra, Long> {
    @Query("SELECT compra FROM Compra compra WHERE compra.id =:id")
    @Transactional(readOnly = true)
    Citas findById(@Param("id") Integer id);
    
    
    void save(Compra compra);  
}
