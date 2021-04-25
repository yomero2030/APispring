/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.citas;

import java.util.Collection;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author legad
 */
public interface MascotasRepository extends Repository<Mascotas, Integer>{
    @Query("SELECT mascotas FROM Mascotas mascotas")
    @Transactional(readOnly = true)
    Collection<Mascotas> getMascotas();
    
    @Query("SELECT mascotas FROM Mascotas mascotas WHERE mascotas.id <=:id")
    @Transactional(readOnly = true)
    Collection<Mascotas> getOnlyMascotas(@Param("id") Integer id);
}
