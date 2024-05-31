package com.techstackgo.ecommerce.dto;

import com.techstackgo.ecommerce.model.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import java.util.HashSet;
import java.util.Set;
@Data
@Validated
public class CreateProductRequest {
    private String title;
    private String description;
    @Positive(message = "price null not allow")
    private int price;
    private int discountedPrice;
    private int discountPresent;
    private int quantity;
    private String brand;
    private String color;
    private Set<Size> size = new HashSet<>();
    private String imageUrl;
    private String topLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;

}
