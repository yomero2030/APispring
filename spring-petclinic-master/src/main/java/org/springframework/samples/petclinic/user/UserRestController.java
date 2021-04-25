/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.user;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Faabian
 */
@RestController
public class UserRestController {
    //API para MOvil
    @PostMapping("/userLogins")
    public UserDTO login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
        String token = getJWTToken(username);
        UserDTO user = new UserDTO();
        user.setUser(username);
        user.setToken(token);
        return user;

    }

    //Método para /user
    private String getJWTToken(String username) {
        String secretKey = "secreto";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
            .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
            .builder()
            .setId("petJWT")
            .setSubject(username)
            .claim("authorities",
                grantedAuthorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()))
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 6000000))
            .signWith(SignatureAlgorithm.HS512,
                secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
