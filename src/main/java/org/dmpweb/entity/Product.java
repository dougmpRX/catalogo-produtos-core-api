package org.dmpweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dmpweb.dto.response.ProductResponse;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "category", length = 20, nullable = false)
    private String category;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    public ProductResponse toResponse() {
        return ProductResponse.builder()
                .id(id)
                .name(name)
                .description(description)
                .category(category)
                .price(price)
                .build();
    }
}
