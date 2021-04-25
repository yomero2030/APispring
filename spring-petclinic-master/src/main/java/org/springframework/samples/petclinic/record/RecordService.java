/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.record;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author katZ_
 */
@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    public RecordService(RecordRepository clinicService) {
        this.recordRepository = clinicService;
    }

    public void badCredentials(String username) {
        System.out.println("record de malas credenciales");
        Record record = new Record();
        //record.setDate(LocalDate.now());
        record.setDescription("Contraseña o usuario incorrectos");
        record.setType("failure");
        record.setUser_email(username);
        this.recordRepository.save(record);
    }

    public void userDisabled(String username) {
        System.out.println("record de usuario disabled");
        Record record = new Record();
        //record.setDate(LocalDate.now());
        record.setDescription("El usuario esta deshabilitado");
        record.setType("failure");
        record.setUser_email(username);
        this.recordRepository.save(record);
    }

    public void success(String username) {
        System.out.println("record de exito creado");
        Record record = new Record();
        //record.setDate(LocalDate.now());
        record.setDescription("Usuario ingreso al sistema sin problemas");
        record.setType("success");
        record.setUser_email(username);
        this.recordRepository.save(record);
    }
}
