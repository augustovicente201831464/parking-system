package com.cunoc.edu.gt.data.response.auth;

import com.cunoc.edu.gt.enums.RolName;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolResponse extends AuditAttributesAuthResponse {
    private Integer id;
    private RolName rolName;
}