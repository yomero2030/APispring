/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.record;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author katZ_
 */
public interface RecordRepository extends Repository<Record, Integer>{
    
    @Query("SELECT record FROM Record record")
    @Transactional(readOnly = true)
    ArrayList<Record> All();
    
    void save(Record record);
    
}
