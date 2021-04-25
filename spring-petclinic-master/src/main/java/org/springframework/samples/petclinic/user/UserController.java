/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.user;

import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author AugustoRuCle
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleRepository roleRepository;

    private ModelAndView modelAndView;

    private final UserRepository users;
    private User auxUser;
        
    //private final OwnerRepository owners;
    
    public UserController(UserRepository clinicService) {
        this.users = clinicService;
        //this.owners = clinicService;
    }
    
    @GetMapping("/create")
    public ModelAndView Create(Map<String, Object> model) {
        User user = new User();
        modelAndView = new ModelAndView("user/create");
        modelAndView.addObject("user", user);
        modelAndView.addObject("exitsZipcode", false);
        modelAndView.addObject("exitsEmail", false);
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView Save(@Valid User user, BindingResult result) {
        modelAndView = new ModelAndView();
        String password = user.getPassword();

        if (result.hasErrors()) {
            return modelAndView;
        } else {
            User _user = this.users.findByEmail(user.getEmail());

            if (userService.exitsZipCode(user.getZipcode(), user.getCity())) {
                modelAndView.setViewName("user/create");
                modelAndView.addObject("exitsZipcode", true);
                return modelAndView;
            }

            if (_user == null) {
                //apartado para crear password --kevin                
                Map encoders = new HashMap<>();
                encoders.put("bcrypt", new BCryptPasswordEncoder());
                PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
                password = passwordEncoder.encode(password);
                user.setPassword(password);
                //////////
                
                ///////////////////////apartado para lo de admin
                Role adminRole = roleRepository.findByName("ROLE_ADMIN");
                user.setRoles(Arrays.asList(adminRole));
                ///////////////////////
                this.users.save(user);
                return this.ViewListUser("user/list");
            } else {
                modelAndView.setViewName("user/create");
                modelAndView.addObject("exitsEmail", true);
                return modelAndView;
        }
                 
        }
    }
   
    @GetMapping("/home")
    public ModelAndView List() {
        modelAndView = this.ViewListUser("user/list");
        return modelAndView;
    }
    
//    @GetMapping("/owners")
//    public ModelAndView ListOwners() {
//        modelAndView = this.ViewListOwners("user/list");
//        return modelAndView;
//    }
    
    @GetMapping("/reports")
    public ModelAndView Reports() {
        modelAndView = this.ViewListUser("user/reports");
        return modelAndView;
    }
   
   
    @GetMapping("/update/{id}")
    public ModelAndView update(@PathVariable("id") int id) {
        this.auxUser = users.findById(id);
        modelAndView = new ModelAndView("user/update_delete");
        modelAndView.addObject("user", this.auxUser);
        modelAndView.addObject("exitsError", false);
        return modelAndView;
    }
   
    
    @PostMapping("/update/{id}")
    public ModelAndView update(@Valid User user, BindingResult result, @PathVariable("id") int id) {
        int exitError = this.exitsError(user, result);
        if(exitError != 0){
            if( exitError == 3 && !this.auxUser.getEmail().equals(user.getEmail()) ){
                modelAndView = new ModelAndView("user/update_delete");
                modelAndView.addObject("user", users.findById(id));
                modelAndView.addObject("exitsError", true);
                //modelAndView.addObject("message", "1:"+exitError);
                return modelAndView;
            }else if(exitError == 1 || exitError == 2){
                modelAndView = new ModelAndView("user/update_delete");
                modelAndView.addObject("user", users.findById(id));
                modelAndView.addObject("exitsError", true);
                //modelAndView.addObject("message", "2:"+ exitError);               
                return modelAndView;               
            }
        }
        
        String password = user.getPassword();
        System.out.println("P:" + password);
        String auxPassword = password.substring(password.indexOf(",")+1);
        if(auxPassword.length() != 0)
            password = auxPassword;
        user.setId(id);
        System.out.println("PN:" + password );
        Map encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());               
        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
        password = passwordEncoder.encode(password);
        user.setPassword(password);
        System.out.println("Pn2:" + password );
        users.save(user);
        return this.ViewListUser("user/list");
    }
    
    @GetMapping("/delete/{id}")
    public ModelAndView Delete(@PathVariable("id") int id) {
        User user = users.findById(id);
        if(user.getActive().equals("1"))
            user.setActive("0");
        else
            user.setActive("1");
        users.save(user);
        return this.ViewListUser("user/list");
    }
    
    
    private ModelAndView ViewListUser(String view){
        ModelAndView _modelAndView = new ModelAndView(view);
        ArrayList<User> users = this.users.All();
        _modelAndView.addObject("users", users);
        return _modelAndView;
    }
    
//    private ModelAndView ViewListOwners(String view){
//        ModelAndView _modelAndView = new ModelAndView(view);
//        ArrayList<Owner> owners = this.owners.All();
//        //ArrayList<User> users = this.users.All();
//        _modelAndView.addObject("owners", owners);
//        return _modelAndView;
//    }

    public int exitsError(User user, BindingResult result){
        if (result.hasErrors()) {
            return 1;
        }else {
            
            if(userService.exitsZipCode(user.getZipcode(), user.getCity()))
                return 2;
            
            if(this.users.findByEmail(user.getEmail()) != null)
                return 3;
        }
        return 0;
    }    

}
