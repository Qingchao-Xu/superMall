package org.bytes.supermallpojo.pojo.order;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类，表名：order
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    private Long id;
    private Long orderId;
    private Long userId;
    private String userCurrency;
    private String email;
    private String streetAddress;
    private String city;
    private String state;
    private String country;
    private Integer zipCode;
    private String orderState;
    private Date createdAt;
    private Date updatedAt;
}
