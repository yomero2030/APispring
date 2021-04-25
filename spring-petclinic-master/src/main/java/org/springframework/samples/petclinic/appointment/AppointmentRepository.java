package org.springframework.samples.petclinic.appointment;

import java.util.Collection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AppointmentRepository extends Repository<Appointment, Integer> {

    /*
    select appointment.id, user_type, date, time, pet, name specialtie, confirmation from
(select * from appointment where id_specialtie=3) as appointment
inner join specialties on appointment.id_specialtie=specialties.id;
     */

    @Query("SELECT appointment FROM Appointment appointment")
    @Transactional(readOnly = true)
    Collection<Appointment> getAppointments();

    @Query("SELECT appointment FROM Appointment appointment "
            + "WHERE appointment.id=:id")   //verificar a donde hacer la peticion  if id or 
    @Transactional(readOnly = true)
    Collection<Appointment> getAppointments(@Param("id") Integer id);
    @Query("SELECT appointment FROM Appointment appointment "
            + "WHERE appointment.confirmacion =:id")
    @Transactional(readOnly = true)
    Collection<Appointment> getAppointmentsByConfirmation(@Param("id") Integer id);

    @Query("SELECT appointment FROM Appointment appointment WHERE appointment.id =:id")
    @Transactional(readOnly = true)
    Appointment findById(@Param("id") Integer id);
    
    @Modifying
    @Transactional
    @Query(value = "update citas u set u.confirmacion = ? where u.id = ?", 
    nativeQuery = true)
    int updateUserSetStatusForNameNative(String confirmacion, Integer id);
    
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Citas citas where citas.id = :id")
    void delete(@Param("id")  Integer id);
    
    //void delete(Appointment appoinment);
    void save(Appointment appoinment);
}
