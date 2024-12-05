
package business;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Cart implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Idcart;
    
    @Column(name = "email", nullable = false) // Lưu khóa ngoại dưới dạng ID
    private String email;
    
    @Lob
    @Column(name = "image", columnDefinition = "bytea") // Lưu ảnh dưới dạng BYTEA
    private byte[] image;
    
    private String name;
    private Double price;
    private Long quantity;
    private Double total;
    
    public Cart (){
        
    }
    
    public Cart (String email, String name, Double price, Long quantity, Double total, byte[] image ){
        this.email = email;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.image = image;
    }
    
    
    public Long getIdcart() {
        return Idcart;
    }

    public void setIdcart(Long Idcart) {
        this.Idcart = Idcart;
    }
    
    // Getter và Setter cho customerId
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    // Getter và Setter cho name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter và Setter cho price
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // Getter và Setter cho quantity
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    // Getter và Setter cho total
    public Double getTotal() {
        // Add null checks
        if (quantity == null || price == null) {
            return 0.0;
        }
        return quantity * price;
    }
    

    public void setTotal(Double total) {
        this.total = total;
    }

    public long getIdCart() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
