package org.bytes.supermallpojo.pojo.user;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类，表名：user
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String passwordHashed;
    private Date createdAt;
    private Date updatedAt;
}
