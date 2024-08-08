package com.cunoc.edu.gt.output.persistence.entity.book;

import com.cunoc.edu.gt.annotations.persistence.Column;
import com.cunoc.edu.gt.annotations.persistence.GeneratedValue;
import com.cunoc.edu.gt.annotations.persistence.GenerationType;
import com.cunoc.edu.gt.annotations.persistence.Id;
import com.cunoc.edu.gt.output.persistence.entity.AuditAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book extends AuditAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Integer id;
}