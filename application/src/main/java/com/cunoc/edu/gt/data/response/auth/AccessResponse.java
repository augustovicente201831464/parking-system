package com.cunoc.edu.gt.data.response.auth;

import com.cunoc.edu.gt.enums.AccessName;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccessResponse extends AuditAttributesAuthResponse {
    private Integer id;
    private AccessName accessName;
}