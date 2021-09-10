package com.web.uz.web.rest;

import com.web.uz.domain.DetailForOrder;
import com.web.uz.domain.Order;
import com.web.uz.repository.OrderRepository;
import com.web.uz.service.OrderV2Service;
import com.web.uz.service.dto.OrderDto;

import java.math.BigDecimal;
import java.util.List;

import liquibase.pro.packaged.E;
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
 * REST controller for managing {@link com.web.uz.domain.Order}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OrderResource {

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    private final OrderRepository orderRepository;
    private final OrderV2Service orderV2Service;

    public OrderResource(OrderRepository orderRepository, OrderV2Service orderV2Service) {
        this.orderRepository = orderRepository;
        this.orderV2Service = orderV2Service;
    }

    /**
     * {@code GET  /orders} : get all the orders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orders in body.
     */
    @GetMapping("/orders")
    public Mono<List<Order>> getAllOrders() {
        log.debug("REST request to get all Orders");
        return orderRepository.findAll().collectList();
    }

    /**
     * {@code GET  /orders} : get all the orders as a stream.
     * @return the {@link Flux} of orders.
     */
    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Order> getAllOrdersAsStream() {
        log.debug("REST request to get all Orders as a stream");
        return orderRepository.findAll();
    }

    /**
     * {@code GET  /orders/:id} : get the "id" order.
     *
     * @param id the id of the order to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the order, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/orders/{id}")
    public Mono<ResponseEntity<Order>> getOrder(@PathVariable Long id) {
        log.debug("REST request to get Order : {}", id);
        Mono<Order> order = orderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(order);
    }

    @PostMapping("/order/create")
    public Mono<?> createOrder(@RequestBody OrderDto orderDto){

        return orderV2Service.createOrder(orderDto);
//        Mono<DetailForOrder> order = orderV2Service.createOrder(orderDto);
//        return ResponseEntity.ok(order.switchIfEmpty(Mono.error(new Exception("error by Aslom")))
//            .flatMap(Mono::just));
//        return order.flatMap(Mono::just)
//            .onErrorResume(ex -> Mono.error(new Exception(ex)));
    }
}
