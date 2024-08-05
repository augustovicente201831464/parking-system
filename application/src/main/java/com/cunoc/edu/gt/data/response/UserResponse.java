package com.cunoc.edu.gt.data.response;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse extends AuditAttributesResponse{
    private Integer id;
    private String name;
    private String lastName;
    private String username;
    private String email;
}