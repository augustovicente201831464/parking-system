package com.cunoc.edu.gt.ports.input;

import com.cunoc.edu.gt.annotations.persistence.Transactional;

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
}
