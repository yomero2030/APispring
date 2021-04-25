/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.record;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;

/**
 *
 * @author katZ_
 */
@Entity
@Table(name = "records")
public class Record extends BaseEntity {

    @Column(name = "type")
    @NotEmpty
    private String type;

    @Column(name = "description")
    @NotEmpty
    private String description;

    @Column(name = "user_email")
    @NotEmpty
    private String user_email;

    @Column(name = "record_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    private Timestamp record_date;

    public Record() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.record_date = timestamp;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the user_email
     */
    public String getUser_email() {
        return user_email;
    }

    /**
     * @param user_email the user_email to set
     */
    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    /**
     *
     * /
     *
     **
     * @param record_date the record_date to set
     */
    public void setRecord_date(Timestamp record_date) {                
        this.record_date = record_date;
    }

    /**
     * @return the record_date
     */
    public Timestamp getRecord_date() {
        return record_date;
    }

}
