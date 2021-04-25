package org.springframework.samples.petclinic.appointment;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;
import javax.persistence.Column;
import org.springframework.samples.petclinic.model.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.samples.petclinic.owner.Owner;

@Entity
@Table(name = "citas")

public class Appointment extends BaseEntity {

    @Column(name = "owner_id")
    @NotEmpty
    private Integer owner_id;

    @Column(name = "fecha")
    @NotEmpty
    private Date fecha;

    @Column(name = "hora")
    @NotEmpty
    private Time hora;

    @Column(name = "mascota")
    @NotEmpty
    private Integer mascota;

    @Column(name = "especialidad")
    @NotEmpty
    private Integer especialidad;

    @Column(name = "confirmacion")
    @NotEmpty
    private Integer confirmacion;

    public Integer getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date date) {
        this.fecha = date;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public Time getHora() {
        return hora;
    }

    public Integer getMascota() {
        return mascota;
    }

    public void setMascota(Integer pet) {
        this.mascota = pet;
    }

    public Integer getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Integer especialidad) {
        this.especialidad = especialidad;
    }

    public Integer getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(Integer confirmacion) {
        this.confirmacion = confirmacion;
    }

}
