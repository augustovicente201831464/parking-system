package com.cunoc.edu.gt.dmimpl.book;

import com.cunoc.edu.gt.data.request.books.BookRequest;
import com.cunoc.edu.gt.data.response.books.BookResponse;
import com.cunoc.edu.gt.dm.DomainMapper;
import com.cunoc.edu.gt.mapper.ModelMapperCustomized;
import com.cunoc.edu.gt.model.book.BookDTO;

public class BookDM implements DomainMapper<BookDTO, BookRequest, BookResponse> {

    /**
     * A method to map a request to a dto
     *
     * @param bookRequest the request to be mapped
     * @return the mapped dto
     */
    @Override
    public BookDTO requestToDto(BookRequest bookRequest) {
        return modelMapper.map(bookRequest, BookDTO.class);
    }

    /**
     * A method to map a dto to a response
     *
     * @param bookDTO the dto to be mapped
     * @return the mapped response
     */
    @Override
    public BookResponse dtoToResponse(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, BookResponse.class);
    }

    private BookDM() {
        this.modelMapper = ModelMapperCustomized.getInstance();
    }

    public static BookDM getInstance() {
        if (instance == null) {
            instance = new BookDM();
        }

        return instance;
    }

    private static BookDM instance;
    private final ModelMapperCustomized modelMapper;
}