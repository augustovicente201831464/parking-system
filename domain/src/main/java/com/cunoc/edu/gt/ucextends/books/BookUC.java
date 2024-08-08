package com.cunoc.edu.gt.ucextends.books;

import com.cunoc.edu.gt.annotations.auth.PreAuthorize;
import com.cunoc.edu.gt.annotations.persistence.Transactional;
import com.cunoc.edu.gt.annotations.validation.Valid;
import com.cunoc.edu.gt.data.request.books.BookRequest;
import com.cunoc.edu.gt.data.response.books.BookResponse;
import com.cunoc.edu.gt.ports.input.UseCase;
import com.cunoc.edu.gt.service.AuditAttributeService;

/**
 * Use case to manage books
 *
 * @Author: Augusto Vicente
 */
public interface BookUC extends UseCase<BookRequest, BookResponse, Integer> {

    /**
     * Method to enroll a book in the system
     *
     * @param request the book to enroll
     * @return BookResponse the book enrolled
     */
    @Valid
    @Transactional
    @PreAuthorize("hasRole('EMPLOYEE') or hasAccess('CREDIT')")
    BookResponse enrollBook(BookRequest request);

    /**
     * Audit attribute service setter
     *
     * @param auditAttributeService the audit attribute service
     * @Author: Augusto Vicente
     */
    void setAuditAttributeService(AuditAttributeService auditAttributeService);
}