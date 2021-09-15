package com.web.uz.web.rest;

import static com.web.uz.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.web.uz.IntegrationTest;
import com.web.uz.domain.OrderV2;
import com.web.uz.repository.OrderV2Repository;
import com.web.uz.service.EntityManager;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link OrderV2Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient
@WithMockUser
class OrderV2ResourceIT {

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_DETAIL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/order-v-2-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderV2Repository orderV2Repository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private OrderV2 orderV2;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderV2 createEntity(EntityManager em) {
        OrderV2 orderV2 = new OrderV2().quantity(DEFAULT_QUANTITY).amount(DEFAULT_AMOUNT).detailName(DEFAULT_DETAIL_NAME);
        return orderV2;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderV2 createUpdatedEntity(EntityManager em) {
        OrderV2 orderV2 = new OrderV2().quantity(UPDATED_QUANTITY).amount(UPDATED_AMOUNT).detailName(UPDATED_DETAIL_NAME);
        return orderV2;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(OrderV2.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        orderV2 = createEntity(em);
    }

    @Test
    void getAllOrderV2sAsStream() {
        // Initialize the database
        orderV2Repository.save(orderV2).block();

        List<OrderV2> orderV2List = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(OrderV2.class)
            .getResponseBody()
            .filter(orderV2::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(orderV2List).isNotNull();
        assertThat(orderV2List).hasSize(1);
        OrderV2 testOrderV2 = orderV2List.get(0);
        assertThat(testOrderV2.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderV2.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testOrderV2.getDetailName()).isEqualTo(DEFAULT_DETAIL_NAME);
    }

    @Test
    void getAllOrderV2s() {
        // Initialize the database
        orderV2Repository.save(orderV2).block();

        // Get all the orderV2List
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(orderV2.getId().intValue()))
            .jsonPath("$.[*].quantity")
            .value(hasItem(DEFAULT_QUANTITY.intValue()))
            .jsonPath("$.[*].amount")
            .value(hasItem(sameNumber(DEFAULT_AMOUNT)))
            .jsonPath("$.[*].detailName")
            .value(hasItem(DEFAULT_DETAIL_NAME));
    }

    @Test
    void getOrderV2() {
        // Initialize the database
        orderV2Repository.save(orderV2).block();

        // Get the orderV2
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, orderV2.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(orderV2.getId().intValue()))
            .jsonPath("$.quantity")
            .value(is(DEFAULT_QUANTITY.intValue()))
            .jsonPath("$.amount")
            .value(is(sameNumber(DEFAULT_AMOUNT)))
            .jsonPath("$.detailName")
            .value(is(DEFAULT_DETAIL_NAME));
    }

    @Test
    void getNonExistingOrderV2() {
        // Get the orderV2
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }
}
