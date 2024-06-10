package org.dmpweb.dto.request;

import lombok.Builder;
import lombok.Data;
import org.dmpweb.entity.Product;

import java.math.BigDecimal;

@Data
@Builder
public class ProductRequest {

    private String name;
    private String description;
    private String category;
    private BigDecimal price;

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .description(description)
                .category(category)
                .price(price)
                .build();
    }
}
