package com.web.uz.repository;

import com.web.uz.domain.OrderV2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the OrderV2 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderV2Repository extends R2dbcRepository<OrderV2, Long>, OrderV2RepositoryInternal {
    @Query("SELECT * FROM orderv2 entity WHERE entity.order_id = :id")
    Flux<OrderV2> findByOrder(Long id);

    @Query("SELECT * FROM orderv2 entity WHERE entity.order_id IS NULL")
    Flux<OrderV2> findAllWhereOrderIsNull();

    @Query("SELECT * FROM orderv2 entity WHERE entity.product_id = :id")
    Flux<OrderV2> findByProduct(Long id);

    @Query("SELECT * FROM orderv2 entity WHERE entity.product_id IS NULL")
    Flux<OrderV2> findAllWhereProductIsNull();

    // just to avoid having unambigous methods
    @Override
    Flux<OrderV2> findAll();

    @Override
    Mono<OrderV2> findById(Long id);

    @Override
    <S extends OrderV2> Mono<S> save(S entity);
}

interface OrderV2RepositoryInternal {
    <S extends OrderV2> Mono<S> insert(S entity);
    <S extends OrderV2> Mono<S> save(S entity);
    Mono<Integer> update(OrderV2 entity);

    Flux<OrderV2> findAll();
    Mono<OrderV2> findById(Long id);
    Flux<OrderV2> findAllBy(Pageable pageable);
    Flux<OrderV2> findAllBy(Pageable pageable, Criteria criteria);
}
