package business;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String customerName;

    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    private String address;

    private String city;

    // Constructors
    public Customer() {
        customerName = "";
        email = "";
        password = "";
        phone = "";
        address = "";
        city ="";
    }
    
    public Customer (String email, String password){
        this.email = email;
        this.password = password;
    }

    public Customer(String customerName, String password, String email, String phone, String address, String city) {
        this.customerName = customerName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
    }

    // Getters and Setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
