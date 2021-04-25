/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.user;

import org.springframework.data.repository.Repository;

/**
 *
 * @author katZ_
 */
public interface PrivilegeRepository extends Repository<Privilege, Integer> {

    Privilege findByName(String name);

    void save(Privilege privilege);
}
