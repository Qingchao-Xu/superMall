package org.bytes.supermallpojo.pojo.product;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体类，表名：product
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String picture;
    private BigDecimal price;
    private Date createdAt;
    private Date updatedAt;
}
