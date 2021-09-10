package com.web.uz.repository;

import com.web.uz.domain.DetailForOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the DetailForOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailForOrderRepository extends R2dbcRepository<DetailForOrder, Long>, DetailForOrderRepositoryInternal {
    @Query("SELECT * FROM detail_for_order entity WHERE entity.order_id = :id")
    Flux<DetailForOrder> findByOrder(Long id);

    @Query("SELECT * FROM detail_for_order entity WHERE entity.order_id IS NULL")
    Flux<DetailForOrder> findAllWhereOrderIsNull();

    @Query("SELECT * FROM detail_for_order entity WHERE entity.product_id = :id")
    Flux<DetailForOrder> findByProduct(Long id);

    @Query("SELECT * FROM detail_for_order entity WHERE entity.product_id IS NULL")
    Flux<DetailForOrder> findAllWhereProductIsNull();

    // just to avoid having unambigous methods
    @Override
    Flux<DetailForOrder> findAll();

    @Override
    Mono<DetailForOrder> findById(Long id);

    @Override
    <S extends DetailForOrder> Mono<S> save(S entity);
}

interface DetailForOrderRepositoryInternal {
    <S extends DetailForOrder> Mono<S> insert(S entity);
    <S extends DetailForOrder> Mono<S> save(S entity);
    Mono<Integer> update(DetailForOrder entity);

    Flux<DetailForOrder> findAll();
    Mono<DetailForOrder> findById(Long id);
    Flux<DetailForOrder> findAllBy(Pageable pageable);
    Flux<DetailForOrder> findAllBy(Pageable pageable, Criteria criteria);
}
