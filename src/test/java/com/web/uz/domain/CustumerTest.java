package com.web.uz.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.web.uz.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustumerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Custumer.class);
        Custumer custumer1 = new Custumer();
        custumer1.setId(1L);
        Custumer custumer2 = new Custumer();
        custumer2.setId(custumer1.getId());
        assertThat(custumer1).isEqualTo(custumer2);
        custumer2.setId(2L);
        assertThat(custumer1).isNotEqualTo(custumer2);
        custumer1.setId(null);
        assertThat(custumer1).isNotEqualTo(custumer2);
    }
}
