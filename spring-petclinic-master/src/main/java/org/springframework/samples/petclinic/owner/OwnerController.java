/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.user.Role;
import org.springframework.samples.petclinic.user.RoleRepository;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.samples.petclinic.citas.Citas;
import org.springframework.samples.petclinic.citas.CitasRepository;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
class OwnerController {

    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM_INITIAL = "owners/createOrUpdateOwnerFormInicial";
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "src//main//resources//static//resources//images//";

    private final OwnerRepository owners;

    ////////kevin
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PetRepository pets;

    private ModelAndView modelAndView;

    ////////////////
    public OwnerController(OwnerRepository clinicService) {
        this.owners = clinicService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
    
    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return this.pets.findPetTypes();
    }

    @GetMapping("/owner_signup")
    public String initCreationForm(Map<String, Object> model) {
        Pet pet = new Pet();
        model.put("pet", pet);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM_INITIAL;
    }

    @PostMapping("/owner_signup")
    public String processCreationForm(@Valid Pet pet, BindingResult result,
            @RequestParam("file") MultipartFile file) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM_INITIAL;
        } else {
            Owner owner = pet.getOwner();
            //Hablar con edgar porque esto es temporal
            Map encoders = new HashMap<>();
            encoders.put("bcrypt", new BCryptPasswordEncoder());
            PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);

            Role ownerRole = roleRepository.findByName("ROLE_OWNER");
            User user = new User();
            user.setFirstName(owner.getFirstName());
            user.setLastName(owner.getLastName());
            user.setPassword(passwordEncoder.encode(owner.getUser().getPassword()));
            user.setEmail(owner.getUser().getEmail());
            user.setRoles(Arrays.asList(ownerRole));
            user.setActive("0");
            user.setCity(owner.getCity());
            user.setTelephone(owner.getTelephone());
            user.setZipcode("29049");

            userRepository.save(user);
            owner.setUser(user);
            /////////////////
            ///
            String relativePath = "";
            try {

                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                String prefijo = RandomStringUtils.randomAlphanumeric(10);
                String imageName = prefijo + file.getOriginalFilename();
                if (file.isEmpty()) {
                    relativePath = "/resources/images/placeholder.png";
                } else {
                    relativePath = "/resources/images/" + imageName;
                }
                Path path = Paths.get(UPLOADED_FOLDER + imageName);
                Files.write(path, bytes);
                System.out.println("El path donde se guardo" + path.toString());
                System.out.println("Nombre del archivo" + imageName);
                System.out.println("Lo que en la bd estara" + relativePath);

                System.out.println("Esperemos que la imagen se guarde compa");

            } catch (IOException e) {
                e.printStackTrace();
            }
            owner.setImagen(relativePath);
            this.owners.save(owner);
            this.pets.save(pet);
            return "redirect:/login";
        }
    }

    @GetMapping("/admin/owners/find")
    public String initFindForm(Map<String, Object> model) {
        model.put("owner", new Owner());
        return "owners/findOwners";
    }

    @GetMapping("/admin/owners")
    public String processFindForm(Owner owner, BindingResult result, Map<String, Object> model) {

        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Collection<Owner> results = this.owners.findByLastName(owner.getLastName());
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else {
            // multiple owners found
            model.put("selections", results);
            return "owners/ownersList";
        }
    }

    @GetMapping("/admin/owners/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
        Owner owner = this.owners.findById(ownerId);
        model.addAttribute(owner);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/admin/owners/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, 
            @PathVariable("ownerId") int ownerId, @RequestParam("file") MultipartFile file) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            //obtener owner                        
            Owner owner_temp = this.owners.findById(ownerId);
            String temp_pass = owner_temp.getUser().getPassword();
            String temp_username = owner_temp.getUser().getEmail();

            owner.setId(owner_temp.getId());
  
            //Hablar con edgar porque esto es temporal
            Map encoders = new HashMap<>();
            encoders.put("bcrypt", new BCryptPasswordEncoder());
            PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);

            Role ownerRole = roleRepository.findByName("ROLE_OWNER");
            User user = owner_temp.getUser();

            user.setFirstName(owner.getFirstName());
            user.setLastName(owner.getLastName());

            if (owner.getUser().getPassword().contains("{bcrypt}") || owner.getUser().getPassword().compareTo("") == 0) {
                user.setPassword(owner_temp.getUser().getPassword());
            } else {
                user.setPassword(passwordEncoder.encode(owner.getUser().getPassword()));
            }
            user.setEmail(owner.getUser().getEmail());
            user.setCity(owner.getCity());
            user.setTelephone(owner.getTelephone());
            user.setZipcode("29049");

            userRepository.save(user);
            owner.setUser(user);
            //owner.setUser(user);
            /////////////////        
            ///
            String relativePath = "";
            try {

                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                String prefijo = RandomStringUtils.randomAlphanumeric(10);
                String imageName = prefijo + file.getOriginalFilename();
                if (file.isEmpty()) {
                    relativePath = owner_temp.getImagen();
                } else {
                    relativePath = "/resources/images/" + imageName;
                }
                Path path = Paths.get(UPLOADED_FOLDER + imageName);
                Files.write(path, bytes);


            } catch (IOException e) {
                e.printStackTrace();
            }
            owner.setImagen(relativePath);
            this.owners.save(owner);
            
            return "redirect:/admin/owners/{ownerId}";
        }
    }

    @GetMapping("owner/my_profile")
    public String initUpdateOwnerForm(Model model) {
        String username = "";
        String id = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        System.out.println("username : " + username);
        //System.out.print("id: "+ ((UserDetails) principal).get);
        User temp = userRepository.findByEmail(username);
        System.out.println("id de este username " + temp.getId());
        Owner owner = this.owners.findByUserId(temp.getId());
        System.out.println("owner: " + owner);
        model.addAttribute(owner);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("owner/my_profile")
    public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result,
            @RequestParam("file") MultipartFile file) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            //obtener owner
            String username = "";
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
            User temp = userRepository.findByEmail(username);
            Owner owner_temp = this.owners.findByUserId(temp.getId());
            String temp_pass = owner_temp.getUser().getPassword();
            String temp_username = owner_temp.getUser().getEmail();

            System.out.println("owner encontrado: " + owner_temp);
            System.out.println("id del owner encontrado: " + owner_temp.getId());

            owner.setId(owner_temp.getId());
            System.out.println("id del que estoy editandos, seniors homeros: " + owner.getId());
            System.out.println("owner con datos nuevos: " + owner);
            //Hablar con edgar porque esto es temporal
            Map encoders = new HashMap<>();
            encoders.put("bcrypt", new BCryptPasswordEncoder());
            PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);

            Role ownerRole = roleRepository.findByName("ROLE_OWNER");
            User user = owner_temp.getUser();
            System.out.println("El user del owner que estoy editando: " + user);
            System.out.println("El id user del owner que estoy editando: " + user.getId());
            user.setFirstName(owner.getFirstName());
            user.setLastName(owner.getLastName());
            System.out.println("contra adentro de owner mandado" + owner.getUser().getPassword());
            if (owner.getUser().getPassword().contains("{bcrypt}") || owner.getUser().getPassword().compareTo("") == 0) {
                user.setPassword(owner_temp.getUser().getPassword());
            } else {
                user.setPassword(passwordEncoder.encode(owner.getUser().getPassword()));
            }
            user.setEmail(owner.getUser().getEmail());
            user.setCity(owner.getCity());
            user.setTelephone(owner.getTelephone());
            user.setZipcode("29049");
            System.out.println("Hasta aca llego");
            userRepository.save(user);
            owner.setUser(user);
            //owner.setUser(user);
            /////////////////        
            ///
            String relativePath = "";
            try {

                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                String prefijo = RandomStringUtils.randomAlphanumeric(10);
                String imageName = prefijo + file.getOriginalFilename();
                if (file.isEmpty()) {
                    relativePath = owner_temp.getImagen();
                } else {
                    relativePath = "/resources/images/" + imageName;
                }
                Path path = Paths.get(UPLOADED_FOLDER + imageName);
                Files.write(path, bytes);
                System.out.println("El path donde se guardo" + path.toString());
                System.out.println("Nombre del archivo" + imageName);
                System.out.println("Lo que en la bd estara" + relativePath);

                System.out.println("Esperemos que la imagen se guarde compa");

            } catch (IOException e) {
                e.printStackTrace();
            }
            owner.setImagen(relativePath);
            this.owners.save(owner);
            if (temp_pass.compareTo(owner.getUser().getPassword()) != 0) {
                System.out.println("Las contras son diferentes");
                System.out.println(temp_pass);
                System.out.println(owner.getUser().getPassword());
                return "redirect:/login?confirmation";
            }
            if (temp_username.compareTo(owner.getUser().getEmail()) != 0) {
                System.out.println("Las emails son diferentes");
                System.out.println(temp_username);
                System.out.println(owner.getUser().getEmail());
                return "redirect:/login?confirmation";
            }
            /////////
            return "redirect:/owner/my_profile";
        }
    }

    /**
     * Custom handler for displaying an owner.
     *
     * @param ownerId the ID of the owner to display
     * @return a ModelMap with the model attributes for the view
     */
    @GetMapping("/admin/owners/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject(this.owners.findById(ownerId));
        return mav;
    }

    //kevin
    @GetMapping("/admin/user/owners")
    public ModelAndView ListOwners() {
        modelAndView = this.ViewListOwners("user/list_owner");
        return modelAndView;
    }

    private ModelAndView ViewListOwners(String view) {
        ModelAndView _modelAndView = new ModelAndView(view);
        ArrayList<Owner> owners = this.owners.All();
        System.out.println("Estyo hay en owner: " + owners.toString());
        ArrayList<User> users = new ArrayList<>();
        for (Owner owner : owners) {
            users.add(owner.getUser());
        }
        System.out.println("Esto hay en users: " + users.toString());
        //ArrayList<User> users = this.users.All();
        _modelAndView.addObject("users", users);
        return _modelAndView;
    }
    //
}
