package org.bytes.supermallpojo.pojo.cart;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类，表名：cart
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart implements Serializable {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private Date createdAt;
    private Date updatedAt;
}
