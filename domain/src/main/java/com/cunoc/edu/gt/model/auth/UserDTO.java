package com.cunoc.edu.gt.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO class for User
 *
 * @Author: Augusto Vicente
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO extends AuditAttributeAuthDTO{
    private Integer id;
    private String name;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String phone;

    private List<RolDTO> rolDTOs = new ArrayList<>();
    private List<AccessDTO> accessDTOs = new ArrayList<>();
}