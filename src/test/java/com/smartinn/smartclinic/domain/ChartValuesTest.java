package com.smartinn.smartclinic.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.smartinn.smartclinic.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChartValuesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChartValues.class);
        ChartValues chartValues1 = new ChartValues();
        chartValues1.setId(1L);
        ChartValues chartValues2 = new ChartValues();
        chartValues2.setId(chartValues1.getId());
        assertThat(chartValues1).isEqualTo(chartValues2);
        chartValues2.setId(2L);
        assertThat(chartValues1).isNotEqualTo(chartValues2);
        chartValues1.setId(null);
        assertThat(chartValues1).isNotEqualTo(chartValues2);
    }
}
