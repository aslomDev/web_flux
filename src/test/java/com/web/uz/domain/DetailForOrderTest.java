package com.web.uz.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.web.uz.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailForOrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailForOrder.class);
        DetailForOrder detailForOrder1 = new DetailForOrder();
        detailForOrder1.setId(1L);
        DetailForOrder detailForOrder2 = new DetailForOrder();
        detailForOrder2.setId(detailForOrder1.getId());
        assertThat(detailForOrder1).isEqualTo(detailForOrder2);
        detailForOrder2.setId(2L);
        assertThat(detailForOrder1).isNotEqualTo(detailForOrder2);
        detailForOrder1.setId(null);
        assertThat(detailForOrder1).isNotEqualTo(detailForOrder2);
    }
}
