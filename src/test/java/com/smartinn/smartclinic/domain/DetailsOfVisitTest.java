package com.smartinn.smartclinic.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.smartinn.smartclinic.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailsOfVisitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailsOfVisit.class);
        DetailsOfVisit detailsOfVisit1 = new DetailsOfVisit();
        detailsOfVisit1.setId(1L);
        DetailsOfVisit detailsOfVisit2 = new DetailsOfVisit();
        detailsOfVisit2.setId(detailsOfVisit1.getId());
        assertThat(detailsOfVisit1).isEqualTo(detailsOfVisit2);
        detailsOfVisit2.setId(2L);
        assertThat(detailsOfVisit1).isNotEqualTo(detailsOfVisit2);
        detailsOfVisit1.setId(null);
        assertThat(detailsOfVisit1).isNotEqualTo(detailsOfVisit2);
    }
}
