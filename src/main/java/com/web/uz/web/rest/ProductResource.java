package com.web.uz.web.rest;

import com.web.uz.domain.Product;
import com.web.uz.repository.ProductRepository;
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
 * REST controller for managing {@link com.web.uz.domain.Product}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private final ProductRepository productRepository;

    public ProductResource(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * {@code GET  /products} : get all the products.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of products in body.
     */
    @GetMapping("/products")
    public Mono<List<Product>> getAllProducts() {
        log.debug("REST request to get all Products");
        return productRepository.findAll().collectList();
    }

    /**
     * {@code GET  /products} : get all the products as a stream.
     * @return the {@link Flux} of products.
     */
    @GetMapping(value = "/products", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Product> getAllProductsAsStream() {
        log.debug("REST request to get all Products as a stream");
        return productRepository.findAll();
    }

    /**
     * {@code GET  /products/:id} : get the "id" product.
     *
     * @param id the id of the product to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the product, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products/{id}")
    public Mono<ResponseEntity<Product>> getProduct(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        Mono<Product> product = productRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(product);
    }
}
