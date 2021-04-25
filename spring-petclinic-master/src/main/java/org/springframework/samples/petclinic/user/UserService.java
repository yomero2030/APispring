/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author AugustoRuCle
 */
@Service
public class UserService {
    
    @Autowired
    RestTemplate restTemplate;
    
    String web_service = "http://api.geonames.org/postalCodeLookupJSON?country=MX&username=augustorucle&maxRows=2";
    
    public boolean exitsZipCode(String _zipcode, String city){
        Object request =  restTemplate.getForObject(this.web_service 
                +"&postalcode="+ _zipcode
                +"&placename="+city, Object.class);
        String data = request.toString();
        data = data.substring(data.indexOf("[")+1, data.length()-2);
        
        if(data.length() == 0){
            return true;
        }else{
            return false;
        }
    }
}