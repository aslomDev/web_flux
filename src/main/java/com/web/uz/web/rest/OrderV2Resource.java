package com.web.uz.web.rest;

import com.web.uz.domain.OrderV2;
import com.web.uz.domain.Product;
import com.web.uz.repository.OrderV2Repository;
import com.web.uz.service.OrderV2Service;

import java.util.List;

import com.web.uz.service.dto.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.web.uz.domain.OrderV2}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OrderV2Resource {

    private final Logger log = LoggerFactory.getLogger(OrderV2Resource.class);

    private final OrderV2Repository orderV2Repository;
    private final OrderV2Service orderV2Service;

    public OrderV2Resource(OrderV2Repository orderV2Repository, OrderV2Service orderV2Service) {
        this.orderV2Repository = orderV2Repository;
        this.orderV2Service = orderV2Service;
    }

    /**
     * {@code GET  /order-v-2-s} : get all the orderV2s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderV2s in body.
     */
    @GetMapping("/order-v-2-s")
    public Mono<List<OrderV2>> getAllOrderV2s() {
        log.debug("REST request to get all OrderV2s");
        return orderV2Repository.findAll().collectList();
    }

    /**
     * {@code GET  /order-v-2-s} : get all the orderV2s as a stream.
     * @return the {@link Flux} of orderV2s.
     */
    @GetMapping(value = "/order-v-2-s", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<OrderV2> getAllOrderV2sAsStream() {
        log.debug("REST request to get all OrderV2s as a stream");
        return orderV2Repository.findAll();
    }

    /**
     * {@code GET  /order-v-2-s/:id} : get the "id" orderV2.
     *
     * @param id the id of the orderV2 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderV2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-v-2-s/{id}")
    public Mono<ResponseEntity<OrderV2>> getOrderV2(@PathVariable Long id) {
        log.debug("REST request to get OrderV2 : {}", id);
        Mono<OrderV2> orderV2 = orderV2Repository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderV2);
    }

    @PostMapping("/order/create")
    public Mono<?> getOrderV2(@RequestBody OrderDto orderDto) {
        return orderV2Service.createProduct(orderDto);
    }

    @PostMapping("/order/create2")
    public Mono<?> getOrderV21(@RequestBody OrderDto orderDto) {
        return orderV2Service.createOrder(orderDto);
    }

    @GetMapping("/test/edocs")
    public Mono<?> getTest(){
        return orderV2Service.saveTest();
    }
}
