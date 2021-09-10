package com.web.uz.web.rest;

import com.web.uz.domain.DetailForOrder;
import com.web.uz.repository.DetailForOrderRepository;
import com.web.uz.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.web.uz.domain.DetailForOrder}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DetailForOrderResource {

    private final Logger log = LoggerFactory.getLogger(DetailForOrderResource.class);

    private final DetailForOrderRepository detailForOrderRepository;

    public DetailForOrderResource(DetailForOrderRepository detailForOrderRepository) {
        this.detailForOrderRepository = detailForOrderRepository;
    }

    /**
     * {@code GET  /detail-for-orders} : get all the detailForOrders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailForOrders in body.
     */
    @GetMapping("/detail-for-orders")
    public Mono<List<DetailForOrder>> getAllDetailForOrders() {
        log.debug("REST request to get all DetailForOrders");
        return detailForOrderRepository.findAll().collectList();
    }

    /**
     * {@code GET  /detail-for-orders} : get all the detailForOrders as a stream.
     * @return the {@link Flux} of detailForOrders.
     */
    @GetMapping(value = "/detail-for-orders", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<DetailForOrder> getAllDetailForOrdersAsStream() {
        log.debug("REST request to get all DetailForOrders as a stream");
        return detailForOrderRepository.findAll();
    }

    /**
     * {@code GET  /detail-for-orders/:id} : get the "id" detailForOrder.
     *
     * @param id the id of the detailForOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailForOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detail-for-orders/{id}")
    public Mono<ResponseEntity<DetailForOrder>> getDetailForOrder(@PathVariable Long id) {
        log.debug("REST request to get DetailForOrder : {}", id);
        Mono<DetailForOrder> detailForOrder = detailForOrderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(detailForOrder);
    }
}
