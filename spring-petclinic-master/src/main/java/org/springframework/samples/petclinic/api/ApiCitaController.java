/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.api;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.appointment.Appointment;
import org.springframework.samples.petclinic.appointment.AppointmentRepository;
import org.springframework.samples.petclinic.citas.CitasRepository;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Faabian
 */

@Controller
@RequestMapping(value = "/api")
public class ApiCitaController {
    
    //    /api/loginApp//citas/{id}"
    @Autowired
    private AppointmentRepository repoAppointment;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OwnerRepository ownerRepository;
    
    @Autowired
    private CitasRepository citasRepository;
    
    private Owner miOwner, tempOwner;
    
    private String USER = "";//Almacenar el usario. Necesario para obtener el id del owner
    
    private int ID_OWNER;//Almacenar id del owner, variable global.
    
    

    //@RequestParam(defaultValue = "test") String id
    @GetMapping(value = "/citas/{id}")
    @ResponseBody
    public Collection<Appointment> All(@PathVariable int id){//obtener citas del usuario
        //ownerId();
        //int ID = Integer.parseInt(id);
        return this.repoAppointment.getAppointments(id);
    }
    
    @GetMapping(value = "/loginApp/{user}")
    @ResponseBody
    public int loginApp(@PathVariable String user){//obtener citas del usuario
        USER = user;
//        ownerId();
//      //int ID = Integer.parseInt(id);
        
try{
        User temp = userRepository.findByEmail(USER);//temp almacena el usuario, si exite temp contendra todo los atributos de User. 
            if(temp.getEmail().equals(USER)){
                    tempOwner = this.ownerRepository.findByUserId(temp.getId());
                    miOwner = this.ownerRepository.findByUserId(temp.getId());
                    ID_OWNER = miOwner.getId();
                    System.out.println("Ver citas del owner ID: " + ID_OWNER);
                    System.out.println("Inf Owner: " + miOwner);
                    System.out.println("entra Usuario:  " + temp.getEmail());
                    return miOwner.getId();
            }else{
                System.out.println("no hay respuestas");
            }
                 }catch(Exception e){
            System.out.println("error "+e);
                }
      return 0;
    }
    
    @PostMapping(value = "/newCitas")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createCita( Appointment citas ){
        System.out.println("citas "+citas);
//        citas.setId(ID_OWNER);
//        citas.setOwner_id(ID_OWNER);
        //citas.setConfirmacion(0);
//        citas.setOwner(tempOwner);
        ////Preconditions.checkNotNull(citas
        System.out.println("New Cita Api owner id: "+ citas.getOwner_id());
        System.out.println("New Cita Api fecha: "+ citas.getFecha());
        System.out.println("New Cita Api hora: "+ citas.getHora());
        System.out.println("New Cita Api owner Mascota: "+ citas.getMascota());
        System.out.println("New Cita Api owner especialidad : "+ citas.getEspecialidad());
        System.out.println("New Cita Api owner confirmacion : "+ citas.getConfirmacion());
        
        this.repoAppointment.save(citas);
      //return citas;
    }
    
    @DeleteMapping(value="/deleteCitas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCita(@PathVariable int id){
        //int id = Integer.parseInt(ID);
        System.out.println("Cita eliminada: " + id);
        this.repoAppointment.delete(id);
    }

}







/*
@Controller
public class ApiCitaController {
    
    @RequestMapping("/url")
    public String page(Model model) {
        model.addAttribute("attribute", "value");
        return "view.name";
    }
    
}*/
