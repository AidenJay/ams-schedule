package com.fa.sesa.schedule.job.admin.core.expression;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class FixedRateExpressionTest {

    @Test
    void testExpression() {
        FixedRateExpression fixedRateExpression = FixedRateExpression.parse("0 0 1 0 0 0");
        LocalDateTime time = LocalDateTime.now();
        Assertions.assertThat(fixedRateExpression.next(time)).isEqualTo(time.plusHours(1));
    }

}