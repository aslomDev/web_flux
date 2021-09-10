package com.web.uz.web.rest;

import static com.web.uz.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.web.uz.IntegrationTest;
import com.web.uz.domain.DetailForOrder;
import com.web.uz.repository.DetailForOrderRepository;
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
 * Integration tests for the {@link DetailForOrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient
@WithMockUser
class DetailForOrderResourceIT {

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/detail-for-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetailForOrderRepository detailForOrderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private DetailForOrder detailForOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailForOrder createEntity(EntityManager em) {
        DetailForOrder detailForOrder = new DetailForOrder().quantity(DEFAULT_QUANTITY).amount(DEFAULT_AMOUNT);
        return detailForOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailForOrder createUpdatedEntity(EntityManager em) {
        DetailForOrder detailForOrder = new DetailForOrder().quantity(UPDATED_QUANTITY).amount(UPDATED_AMOUNT);
        return detailForOrder;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(DetailForOrder.class).block();
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
        detailForOrder = createEntity(em);
    }

    @Test
    void getAllDetailForOrdersAsStream() {
        // Initialize the database
        detailForOrderRepository.save(detailForOrder).block();

        List<DetailForOrder> detailForOrderList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(DetailForOrder.class)
            .getResponseBody()
            .filter(detailForOrder::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(detailForOrderList).isNotNull();
        assertThat(detailForOrderList).hasSize(1);
        DetailForOrder testDetailForOrder = detailForOrderList.get(0);
        assertThat(testDetailForOrder.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testDetailForOrder.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
    }

    @Test
    void getAllDetailForOrders() {
        // Initialize the database
        detailForOrderRepository.save(detailForOrder).block();

        // Get all the detailForOrderList
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
            .value(hasItem(detailForOrder.getId().intValue()))
            .jsonPath("$.[*].quantity")
            .value(hasItem(DEFAULT_QUANTITY.intValue()))
            .jsonPath("$.[*].amount")
            .value(hasItem(sameNumber(DEFAULT_AMOUNT)));
    }

    @Test
    void getDetailForOrder() {
        // Initialize the database
        detailForOrderRepository.save(detailForOrder).block();

        // Get the detailForOrder
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, detailForOrder.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(detailForOrder.getId().intValue()))
            .jsonPath("$.quantity")
            .value(is(DEFAULT_QUANTITY.intValue()))
            .jsonPath("$.amount")
            .value(is(sameNumber(DEFAULT_AMOUNT)));
    }

    @Test
    void getNonExistingDetailForOrder() {
        // Get the detailForOrder
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }
}
