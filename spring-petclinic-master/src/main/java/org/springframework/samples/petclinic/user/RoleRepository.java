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
public interface RoleRepository extends Repository<Role, Long> {

    Role findByName(String name);

    void save(Role role);
}
