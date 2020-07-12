package com.merchant.store.offers.mapper;

import java.util.Optional;

/***
 * Mapper Functional Interface
 *
 * @param <S>		:	JPA Object
 * @param <T>		:	DTO Object
 */
@FunctionalInterface
public interface ModelToDtoMapper<S, T> {


    /***
     * Maps from jpa to DTO
     * @param source     :   jpa object
     * @return           :   DTO object
     */
    Optional<T> map (S source);
}



