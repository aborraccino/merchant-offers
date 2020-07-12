package com.merchant.store.offers.mapper;

import java.util.Optional;

/***
 * Mapper Functional Interface
 *
 * @param <S>		:	DTO Object
 * @param <T>		:	JPA Object
 */
@FunctionalInterface
public interface DtoToModelMapper<S, T> {

    /***
     * Maps from DTO to JPA
     * @param source     :   jpa object
     * @return           :   DTO object
     */
    Optional<T> map (S source);
}



