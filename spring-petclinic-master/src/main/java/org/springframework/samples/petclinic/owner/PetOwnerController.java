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

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
//@RequestMapping("/owner")
class PetOwnerController {

    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM_OWNER = "pets/createOrUpdatePetFormOwner";
    private final PetRepository pets;
    private final OwnerRepository owners;

    @Autowired
    private UserRepository userRepository;

    public PetOwnerController(PetRepository pets, OwnerRepository owners) {
        this.pets = pets;
        this.owners = owners;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return this.pets.findPetTypes();
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @InitBinder("pet")
    public void initPetBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new PetValidator());
    }

    @GetMapping("/owner/pets")
    public ModelAndView showOwner() {
        Owner owner = getCurrentOwner();
        int ownerId = owner.getId();
        ModelAndView mav = new ModelAndView("owners/ownerDetailsOwner");
        mav.addObject(this.owners.findById(ownerId));
        return mav;
    }

    @GetMapping("/owner/pets/new")
    public String initCreationFormOwner(ModelMap model) {
        Pet pet = new Pet();
        Owner owner = getCurrentOwner();
        owner.addPet(pet);
        model.put("pet", pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM_OWNER;
    }

    @PostMapping("/owner/pets/new")
    public String processCreationFormOwner(@Valid Pet pet, BindingResult result, ModelMap model) {
        Owner owner = getCurrentOwner();
        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
            result.rejectValue("name", "duplicate", "already exists");
        }
        owner.addPet(pet);
        if (result.hasErrors()) {
            model.put("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM_OWNER;
        } else {
            this.pets.save(pet);
            return "redirect:/owner/pets/";
        }
    }

    @GetMapping("/owner/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable("petId") int petId, ModelMap model) {
        Pet pet = this.pets.findById(petId);
        model.put("pet", pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM_OWNER;
    }

    @PostMapping("/owner/pets/{petId}/edit")
    public String processUpdateForm(@Valid Pet pet, BindingResult result, ModelMap model) {
        Owner owner = getCurrentOwner();
        if (result.hasErrors()) {
            pet.setOwner(owner);
            model.put("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM_OWNER;
        } else {
            owner.addPet(pet);
            this.pets.save(pet);
            return "redirect:/owner/pets/";
        }
    }

    //Metodo para saber quien esta ahorita
    private Owner getCurrentOwner() {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        System.out.println("username : " + username);
        User temp = userRepository.findByEmail(username);
        System.out.println("id de este username " + temp.getId());
        Owner owner = this.owners.findByUserId(temp.getId());
        System.out.println("owner: " + owner);

        return owner;
    }

}
