package rares.web.ecommece.model;

import javax.validation.constraints.*;

//Represents a single "Add to cart" request for a product
public class AddToCartDTO {

    @PositiveOrZero(message = "Must be greater than or equal to 0")
    private long id;

    @Min(value = 1, message = "Please add more than 0 products")
    @Max(value = 10, message = "Maximum quantity of 10 per product")
    private int quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
