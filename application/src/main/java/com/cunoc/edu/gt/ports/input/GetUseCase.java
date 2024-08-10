package com.cunoc.edu.gt.ports.input;

import com.cunoc.edu.gt.data.pagination.Page;
import com.cunoc.edu.gt.data.pagination.Pageable;

/**
 * Generic get use case
 *
 * @author Augusto Vicente
 * @param <Response>
 * @param <ID>
 */
public interface GetUseCase<Response, ID> {

    /**
     * Get all objects
     *
     * @param ID id of the object to be retrieved
     * @return Response
     */
    Response getById(ID ID);

    /**
     * Get all objects
     *
     * @param pageable Pageable object
     * @return Page<Response>
     */
    Page<Response> getPage(Pageable pageable);
}