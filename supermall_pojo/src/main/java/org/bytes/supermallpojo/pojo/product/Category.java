package org.bytes.supermallpojo.pojo.product;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类，表名：category
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Date createdAt;
    private Date updatedAt;
}
