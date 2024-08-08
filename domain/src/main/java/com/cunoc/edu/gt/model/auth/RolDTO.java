package com.cunoc.edu.gt.model.auth;

import com.cunoc.edu.gt.enums.RolName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolDTO extends AuditAttributeAuthDTO{
    private Integer id;
    private RolName rolName;
}
