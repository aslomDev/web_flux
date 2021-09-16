package com.web.uz.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.web.uz.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderV2Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderV2.class);
        OrderV2 orderV21 = new OrderV2();
        orderV21.setId(1L);
        OrderV2 orderV22 = new OrderV2();
        orderV22.setId(orderV21.getId());
        assertThat(orderV21).isEqualTo(orderV22);
        orderV22.setId(2L);
        assertThat(orderV21).isNotEqualTo(orderV22);
        orderV21.setId(null);
        assertThat(orderV21).isNotEqualTo(orderV22);
    }
}
