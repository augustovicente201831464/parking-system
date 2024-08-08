package com.cunoc.edu.gt.data.response.books;

import com.cunoc.edu.gt.data.response.AuditAttributeResponse;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookResponse extends AuditAttributeResponse {
    private Integer id;
}