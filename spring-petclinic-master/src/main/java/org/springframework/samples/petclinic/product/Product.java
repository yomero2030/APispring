
package org.springframework.samples.petclinic.product;


import javax.persistence.Column;
import org.springframework.samples.petclinic.model.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Positive;
import org.springframework.core.style.ToStringCreator;



@Entity
@Table(name = "products")

public class Product extends BaseEntity{
    @Column(name = "name")
    @NotEmpty
    private String name;

    @Column(name = "description")
    @NotEmpty
    private String description;

    @Column(name = "price")
    @NotNull
    @Positive
    private Double price;
    
    @Column(name = "existence")
    @NotNull
    @PositiveOrZero
    private Integer existence;
    
    @Column(name = "photo")
    private String photo;
    

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getExistence() {
        return existence;
    }

    public void setExistence(Integer existence) {
        this.existence = existence;
    }



    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    
        @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("name" , this.getName())
                .append("description", this.getDescription())
                .append("price=" , this.getPrice())
                .append("existence=", this.getExistence())
                .append(" photo=", this.getPhoto())
                
                .toString();
    }
    
}
