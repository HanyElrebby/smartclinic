package com.smartinn.smartclinic.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.smartinn.smartclinic.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClinicTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clinic.class);
        Clinic clinic1 = new Clinic();
        clinic1.setId(1L);
        Clinic clinic2 = new Clinic();
        clinic2.setId(clinic1.getId());
        assertThat(clinic1).isEqualTo(clinic2);
        clinic2.setId(2L);
        assertThat(clinic1).isNotEqualTo(clinic2);
        clinic1.setId(null);
        assertThat(clinic1).isNotEqualTo(clinic2);
    }
}
