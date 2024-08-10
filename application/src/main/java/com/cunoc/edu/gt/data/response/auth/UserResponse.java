package com.cunoc.edu.gt.data.response.auth;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User response.
 *
 * @Author: Augusto Vicente
 */
@EqualsAndHashCode(callSuper = true)
//@ToString(callSuper = true)
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

    private List<RolResponse> rolResponses = new ArrayList<>();
    private List<AccessResponse> accessResponses = new ArrayList<>();

    @Override
    public String toString() {
        return "UserResponse{" +
                "\n id=" + id + "," +
                "\n name='" + name + '\'' + "," +
                "\n lastname='" + lastname + '\'' + "," +
                "\n username='" + username + '\'' + "," +
                "\n email='" + email + '\'' + "," +
                "\n phone='" + phone + '\'' + "," +
                "\n rolResponses=" + rolResponses + "," +
                "\n accessResponses=" + accessResponses + "," +
                "\n}";
    }
}