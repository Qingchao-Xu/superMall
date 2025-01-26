package org.bytes.supermallpojo.pojo.payment;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体类，表名：payment
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {
    private Long id;
    private Long userId;
    private Long orderId;
    private Long transactionId;
    private BigDecimal amount;
    private Date payAt;
    private Date createdAt;
    private Date updatedAt;
}
