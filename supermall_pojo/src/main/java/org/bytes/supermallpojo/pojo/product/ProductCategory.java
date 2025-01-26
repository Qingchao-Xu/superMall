package org.bytes.supermallpojo.pojo.product;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类，表名：product_category
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory implements Serializable {
    private Long id;
    private Long productId;
    private Long categoryId;
    private Date createdAt;
    private Date updatedAt;
}
