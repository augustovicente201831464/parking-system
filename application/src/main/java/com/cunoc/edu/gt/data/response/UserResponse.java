package com.cunoc.edu.gt.data.response;

import lombok.*;

/**
 * User response.
 *
 * @Author: Augusto Vicente
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse extends AuditAttributesAuthResponse {
    private Integer id;
    private String name;
    private String lastname;
    private String username;
    private String email;
    private String phone;
}