package com.cunoc.edu.gt.ports.input;

/**
 * Generic get use case
 *
 * @param <ID>
 */
public interface ExistsUseCase <ID>{

    /**
     * Check if entity exists by id
     *
     * @param id id of the object to be retrieved
     * @return boolean
     */
    boolean existsById(ID id);
}
