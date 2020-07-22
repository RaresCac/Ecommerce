package rares.web.ecommece.model;


import javax.validation.constraints.*;

public class ProductDTO {

    @NotBlank(message = "Must have a name")
    private String name;

    @NotBlank(message = "Must have a description")
    private String description;

    @Min(value = 0L, message = "Price must be positive")
    @Max(value = 9999L, message = "Value exceeds maximum of 9999")
    private double price;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
