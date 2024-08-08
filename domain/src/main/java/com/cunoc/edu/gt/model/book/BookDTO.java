package com.cunoc.edu.gt.model.book;

import com.cunoc.edu.gt.model.AuditAttributeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO extends AuditAttributeDTO {
    private Integer id;
}