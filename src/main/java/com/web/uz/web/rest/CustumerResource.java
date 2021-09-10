package com.web.uz.web.rest;

import com.web.uz.domain.Custumer;
import com.web.uz.repository.CustumerRepository;
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
 * REST controller for managing {@link com.web.uz.domain.Custumer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustumerResource {

    private final Logger log = LoggerFactory.getLogger(CustumerResource.class);

    private final CustumerRepository custumerRepository;

    public CustumerResource(CustumerRepository custumerRepository) {
        this.custumerRepository = custumerRepository;
    }

    /**
     * {@code GET  /custumers} : get all the custumers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custumers in body.
     */
    @GetMapping("/custumers")
    public Mono<List<Custumer>> getAllCustumers() {
        log.debug("REST request to get all Custumers");
        return custumerRepository.findAll().collectList();
    }

    /**
     * {@code GET  /custumers} : get all the custumers as a stream.
     * @return the {@link Flux} of custumers.
     */
    @GetMapping(value = "/custumers", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Custumer> getAllCustumersAsStream() {
        log.debug("REST request to get all Custumers as a stream");
        return custumerRepository.findAll();
    }

    /**
     * {@code GET  /custumers/:id} : get the "id" custumer.
     *
     * @param id the id of the custumer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custumer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/custumers/{id}")
    public Mono<ResponseEntity<Custumer>> getCustumer(@PathVariable Long id) {
        log.debug("REST request to get Custumer : {}", id);
        Mono<Custumer> custumer = custumerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(custumer);
    }
}
