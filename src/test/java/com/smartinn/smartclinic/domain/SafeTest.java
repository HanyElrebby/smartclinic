package com.smartinn.smartclinic.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.smartinn.smartclinic.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SafeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Safe.class);
        Safe safe1 = new Safe();
        safe1.setId(1L);
        Safe safe2 = new Safe();
        safe2.setId(safe1.getId());
        assertThat(safe1).isEqualTo(safe2);
        safe2.setId(2L);
        assertThat(safe1).isNotEqualTo(safe2);
        safe1.setId(null);
        assertThat(safe1).isNotEqualTo(safe2);
    }
}
