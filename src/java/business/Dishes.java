package business;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Dishes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID
    private Long dishId;

    @Column(name = "id_category", nullable = false) // Lưu khóa ngoại dưới dạng ID
    private Long categoryId;

    private String name;
    private String description;
    private Double price;

    @Lob
    @Column(name = "image", columnDefinition = "bytea") // Lưu ảnh dưới dạng BYTEA
    private byte[] image;

    // Constructors
    public Dishes() {}

    public Dishes(Long categoryId, String name, String description, Double price, byte[] image) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    // Getters and Setters
    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
