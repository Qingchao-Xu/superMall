package org.bytes.supermallpojo.pojo.order;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体类，表名：order_item
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem implements Serializable {
    private Long id;
    private Long productId;
    private Long orderId;
    private Integer quantity;
    private BigDecimal cost;
    private Date createdAt;
    private Date updatedAt;
}
