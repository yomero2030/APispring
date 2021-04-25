package org.springframework.samples.petclinic.appointment;

import java.util.Collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SpecialtieRepository extends Repository<Specialties, Integer> {
   
    @Query("SELECT specialties FROM Specialties specialties")
    @Transactional(readOnly = true)
    Collection<Specialties> getSpecialties();
    
    
    @Query("SELECT specialties FROM Specialties specialties WHERE specialties.id =:id")
    @Transactional(readOnly = true)
    Specialties getSpecialtieById(@Param("id") Integer id);
}
