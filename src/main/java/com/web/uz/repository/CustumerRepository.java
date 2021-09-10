package com.web.uz.repository;

import com.web.uz.domain.Custumer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Custumer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustumerRepository extends R2dbcRepository<Custumer, Long>, CustumerRepositoryInternal {
    // just to avoid having unambigous methods
    @Override
    Flux<Custumer> findAll();

    @Override
    Mono<Custumer> findById(Long id);

    @Override
    <S extends Custumer> Mono<S> save(S entity);
}

interface CustumerRepositoryInternal {
    <S extends Custumer> Mono<S> insert(S entity);
    <S extends Custumer> Mono<S> save(S entity);
    Mono<Integer> update(Custumer entity);

    Flux<Custumer> findAll();
    Mono<Custumer> findById(Long id);
    Flux<Custumer> findAllBy(Pageable pageable);
    Flux<Custumer> findAllBy(Pageable pageable, Criteria criteria);
}
