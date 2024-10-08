package com.cunoc.edu.gt.jpa.repository;

import com.cunoc.edu.gt.data.pagination.Page;
import com.cunoc.edu.gt.data.pagination.Pageable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author vicen
 * @param <ENTITY>
 * @param <ID>
 */
public interface JpaRepository<ENTITY, ID> {
    
    /**
     * Method to save an entity
     *
     * @param entity the entity to be saved
     * @return  the saved entity
     */
    ENTITY save(ENTITY entity);

    /**
     * Method to update an entity
     *
     * @param entity the entity to be updated
     * @return  the updated entity
     */
    ENTITY update(ENTITY entity);

    /**
     * Method to update an entity by id
     *
     * @param entity the entity to be updated
     * @param id the id of the entity to be updated
     * @return  the updated entity
     */
    ENTITY updateById(ENTITY entity, ID id);

    /**
     * Method to delete an entity
     *
     * @param entity the entity to be deleted
     */
    void delete(ENTITY entity);

    /**
     * Method to delete an entity by id
     *
     * @param id the id of the entity to be deleted
     */
    void deleteById(ID id);

    /**
     * Method to get an entity by id
     *
     * @param id the id of the entity to be retrieved
     * @return the entity with the given id
     */
    Optional<ENTITY> findById(ID id);

    /**
     * Method to get all entities
     *
     * @return all entities
     */
    List<ENTITY> getAll();

    /**
     * Method to get a page of entities
     *
     * @param pageable the page to be retrieved
     * @return a page of entities
     */
    Page<ENTITY> getPage(Pageable pageable);

    /**
     * Method to get a page of entities
     *
     * @return a page of entities
     */
    Optional<ENTITY> findByGenericMethod(String methodName, Class<?> repository, List<Object> values);

    /**
     * Method to evaluate if an entity exists by generic field
     *
     * @return true if the entity exists, false otherwise
     */
    boolean existsByGenericMethod(String methodName, Class<?> repository, List<Object> values);


    /**
     * Method to evaluate if an entity exists by id
     *
     * @param id the id of the entity to be evaluated
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(ID id);

    /**
     * Method to process a lazy fetch
     *
     * @param relatedField the related field
     * @param parentInstance the parent instance
     * @param entityIdField the entity id field
     */
    void processFetchLazy(Field relatedField, ENTITY parentInstance, Field entityIdField);
}