/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.citas;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author legad
 */
public interface CitasRepository extends Repository<Citas, Long>{
    
    @Query("SELECT cita FROM Citas cita WHERE cita.id =:id")
    @Transactional(readOnly = true)
    Citas findById(@Param("id") Integer id);
    
    void save(Citas citas);   
}
