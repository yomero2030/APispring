///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.springframework.samples.petclinic.user;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// *
// * @author katZ_
// */
//@Component
//public class InitialDataLoader implements
//  ApplicationListener<ContextRefreshedEvent> {
// 
//    boolean alreadySetup = false;
// 
//    @Autowired
//    private UserRepository userRepository;
//  
//    @Autowired
//    private RoleRepository roleRepository;
//  
//    @Autowired
//    private PrivilegeRepository privilegeRepository;    
//  
//    @Override
//    @Transactional
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//  
//        if (alreadySetup)
//            return;
//        
//        Map encoders = new HashMap<>();
//        encoders.put("bcrypt", new BCryptPasswordEncoder());               
//        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
//        
//        Privilege readPrivilege
//          = createPrivilegeIfNotFound("READ_PRIVILEGE");
//        Privilege writePrivilege
//          = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
//  
//        List<Privilege> adminPrivileges = Arrays.asList(
//          readPrivilege, writePrivilege);        
//        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
//        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
// 
//        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
//        User user = new User();
//        user.setFirstName("Test");
//        user.setLastName("Test");
//        user.setPassword(passwordEncoder.encode("test"));
//        user.setEmail("test@test.com");
//        user.setRoles(Arrays.asList(adminRole));
//        user.setActive("1");
//        user.setCity("Tuxtla Gutierrez");
//        user.setTelephone("9612570599");
//        user.setZipcode("29049");
//        
//        userRepository.save(user);
// 
//        alreadySetup = true;
//    }
// 
//    @Transactional
//    private Privilege createPrivilegeIfNotFound(String name) {
//  
//        Privilege privilege = privilegeRepository.findByName(name);
//        if (privilege == null) {
//            privilege = new Privilege(name);
//            privilegeRepository.save(privilege);
//        }
//        return privilege;
//    }
// 
//    @Transactional
//    private Role createRoleIfNotFound(
//      String name, Collection<Privilege> privileges) {
//  
//        Role role = roleRepository.findByName(name);
//        if (role == null) {
//            role = new Role(name);
//            role.setPrivileges(privileges);
//            roleRepository.save(role);
//        }
//        return role;
//    }
//}