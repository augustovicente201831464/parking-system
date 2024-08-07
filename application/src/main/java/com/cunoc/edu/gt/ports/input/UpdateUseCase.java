package com.cunoc.edu.gt.ports.input;

/**
 * UpdateUseCase
 *
 * @param <Request> the object to be updated
 * @param <Response> the updated object
 * @param <ID> id of the object to be updated
 *
 * @author balamkiche
 */
public interface UpdateUseCase<Request, Response, ID> {

    /**
     *  Update object by id
     *
     * @param createRequest the object to be updated
     * @param id id of the object to be updated
     * @return QueryResponse the updated object
     */
    Response updateById(Request createRequest, ID id);
}
