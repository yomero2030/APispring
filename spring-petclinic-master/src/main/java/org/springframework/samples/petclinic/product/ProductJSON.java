/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.product;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Faabian
 */
public class ProductJSON {
      private List<Product> products;

 
    public List<Product> getProductList() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }
}

