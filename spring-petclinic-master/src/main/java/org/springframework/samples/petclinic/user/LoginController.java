/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.user;

import java.nio.charset.StandardCharsets;
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
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author katZ_
 */
@Controller
public class LoginController {    

    private final UserRepository users;

    public LoginController(UserRepository clinicService) {
        this.users = clinicService;
    }

    @GetMapping("/Login")
    public ModelAndView Login(Map<String, Object> model) {
        User user = new User();
        ModelAndView modelAndView = new ModelAndView("user/login");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

}
