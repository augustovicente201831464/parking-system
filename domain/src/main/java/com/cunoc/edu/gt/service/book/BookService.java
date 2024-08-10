package com.cunoc.edu.gt.service.book;

import com.cunoc.edu.gt.data.pagination.Page;
import com.cunoc.edu.gt.data.pagination.Pageable;
import com.cunoc.edu.gt.data.request.books.BookRequest;
import com.cunoc.edu.gt.data.response.books.BookResponse;
import com.cunoc.edu.gt.dmimpl.book.BookDM;
import com.cunoc.edu.gt.exception.InvalidDataException;
import com.cunoc.edu.gt.model.book.BookDTO;
import com.cunoc.edu.gt.service.AuditAttributeService;
import com.cunoc.edu.gt.ucextends.books.BookUC;
import lombok.Data;

import java.util.logging.Logger;

@Data
public class BookService implements BookUC {

    /**
     * Method to enroll a book in the system
     *
     * @param request the book to enroll
     * @return BookResponse the book enrolled
     */
    @Override
    public BookResponse enrollBook(BookRequest request) {

        if (request == null) {
            throw new InvalidDataException("Book request is null");
        }

        BookResponse response = save(request);

        Logger.getLogger("BookService").info("Book enrolled: " + response.toString());

        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Save entity
     *
     * @param request the entity to save
     * @return QueryResponse the saved entity
     */
    @Override
    public BookResponse save(BookRequest request) {
        BookDTO dto = domainMapper.requestToDto(request);
        dto = (BookDTO) auditAttributeService.getAuditAttributeForNew(dto);
        Logger.getLogger("BookService").info("Saving book: " + dto.toString());

        return domainMapper.dtoToResponse(dto);
    }

    /**
     * Delete entity by id
     *
     * @param integer the id of the entity to be deleted
     */
    @Override
    public void deleteById(Integer integer) {

    }

    /**
     * Check if entity exists by id
     *
     * @param integer id of the object to be retrieved
     * @return boolean
     */
    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    /**
     * Get all objects
     *
     * @param ID id of the object to be retrieved
     * @return Response
     */
    @Override
    public BookResponse getById(Integer ID) {
        return null;
    }

    /**
     * Get all objects
     *
     * @param pageable the pagination information
     * @return Page<Response> the page of objects
     */
    @Override
    public Page<BookResponse> getPage(Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Update object by id
     *
     * @param createRequest the object to be updated
     * @param integer       id of the object to be updated
     * @return QueryResponse the updated object
     */
    @Override
    public BookResponse updateById(BookRequest createRequest, Integer integer) {
        return null;
    }

    //Singleton
    private BookService() {
        this.domainMapper = BookDM.getInstance();
    }

    public static BookService getInstance() {
        if (instance == null) {
            instance = new BookService();
        }
        return instance;
    }

    private static BookService instance;
    private final BookDM domainMapper;

    private AuditAttributeService auditAttributeService;
}