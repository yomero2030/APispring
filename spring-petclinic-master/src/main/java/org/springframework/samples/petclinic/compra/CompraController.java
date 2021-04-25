/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.compra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.product.Product;
import org.springframework.samples.petclinic.product.ProductRepository;
import org.springframework.samples.petclinic.user.RoleRepository;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Faabian
 */

public class CompraController {

    private final CompraRepository compra;
    private static final String VIEW_PRODUCTO_OWNER = "producto/productOwner";
     private static final String  COMPRAaPPOIMENT = "compra/appointmentCompra";
     @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private OwnerRepository owners;
    
    @Autowired
    private ProductRepository product;
    
    
    
    public  CompraController(CompraRepository compra){
        this.compra = compra;
        this.product = product;
    }
    
    @GetMapping(value = "owner/compra/{productId}")
   // @ResponseBody
    public String  Compra (@PathVariable("productId") int productId, Map<String, Object> model){
        
      ArrayList<Product> nombres = new ArrayList();
     Collection<Product> especialidades = product.GetIDProducts();
    int products = productId;
        Compra compraOwner = new Compra();
        
        Product temps = this.product.findById(products);
        
        String productName = "";
       
        System.out.println("Mal diego");
        System.out.println("Producto: "+temps.getName());
     
        String username = "";
        int pd = 0;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
           
            
        } else {
            username = principal.toString();
            productName = this.product.toString();
            
        }
        
        User temp = this.userRepository.findByEmail(username);
        Owner owner_temp = this.owners.findByUserId(temp.getId());
        
        
        Owner owner = this.owners.findById(owner_temp.getId());
        System.out.println("idOwners: "+ this.owners.findByLastName(owner_temp.getFirstName()));
        model.put("idowner", this.owners.findByLastName(owner_temp.getFirstName()));
        
       // model.put("especialidades",especialidades );
        System.out.println(temps.toString());
            nombres.add(temps);
           
        model.put("especialidades",especialidades );
        
        System.out.println("Temps: "+temps);
        
        System.out.println("precio: "+ temps.getPrice());
        System.out.println("iduser: "+ temp.getId());
         System.out.println("idOwner: "+ owner_temp.getId());
         
        return COMPRAaPPOIMENT;
    }
    
   
    @PostMapping("owner/compra/{productId}")
    public String processMakeCita(@Valid Compra compra, BindingResult result,@RequestParam int product ){
     System.out.println(result);
     if ( result.hasErrors()){
     System.out.println("creacion compra mal");
     System.out.print("mal");
            return COMPRAaPPOIMENT;
                
     
     } else {
            
        
         String username = "";
         String producto = "";
          Object principal;
         principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
          if(principal instanceof UserDetails){
              username = ((UserDetails)principal).getUsername();
              
          } else {
              username = principal.toString();
          }
          
          User temp = userRepository.findByEmail(username);
          Owner owner_temp = this.owners.findByUserId(temp.getId());
           System.out.println("usuario: " + temp);
            System.out.println("numero jaja: " + temp.getId());
            System.out.println("owner encontrado: " + owner_temp);
            System.out.println("id del owner encontrado: " + owner_temp.getId());
            
            
            compra.setOwner(owner_temp);
            this.compra.save(compra);
            System.out.print("error pero bien");
          return COMPRAaPPOIMENT;
     }
    
    }
    
    //APi MOvil Guardar Productos
    
       @GetMapping("/productSell")
    public String initFindForm(Map<String, Object> model) {
        Collection<Product> allProducts = this.product.getAllProducts();
        model.put("allProducts", allProducts);
        //porque no usaron la variable de VIEW_PRODUCT_HOME en el return? -Claudio
        return "productSell/productSell";
    }

    @PostMapping(value = { "/API/sellJSON/"  },
        consumes = MediaType.APPLICATION_JSON_VALUE
        )

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Compra createSell(@RequestBody Compra compra) {
        this.compra.save(compra);
        return compra;
    }
    
    
   
    
}
