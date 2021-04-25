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
public interface EspecialidadesRepository extends Repository<Especialidades, Integer>{
    
    @Query("SELECT especialidades FROM Especialidades especialidades")
    @Transactional(readOnly = true)
    Collection<Especialidades> getEspecialidades();
    
}
