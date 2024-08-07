package com.cunoc.edu.gt.ports.input;

/**
 * @param <ID>
 *
 * @author balamkiche
 */
public interface DeleteUseCase<ID> {

    /**
     * Delete entity by id
     *
     * @param id the id of the entity to be deleted
     */
    void deleteById(ID id);
}
