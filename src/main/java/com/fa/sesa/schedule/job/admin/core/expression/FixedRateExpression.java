package com.fa.sesa.schedule.job.admin.core.expression;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * description:
 *
 * @author: johnson Wang
 * @date: 2021/1/5 10:16
 * @copyright: 2021, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
public class FixedRateExpression {

    private final Field[] fields;

    private final String expression;

    private FixedRateExpression(Field seconds, Field minutes, Field hours, Field days, Field months, Field years,
                                String expression) {
        this.expression = expression;
        this.fields = new Field[]{seconds, minutes, hours, days, months, years};
    }

    public static boolean isValidExpression(String expression) {
        try {
            parse(expression);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static FixedRateExpression parse(String expression) {
        Assert.hasLength(expression, "Expression string must not be empty");

        String[] fields = StringUtils.tokenizeToStringArray(expression, " ");
        if (fields.length != 6) {
            throw new IllegalArgumentException(
                    String.format("Cron expression must consist of 6 fields (found %d in \"%s\")", fields.length,
                                  expression));
        }
        try {
            Field seconds = Field.parseSeconds(fields[0]);
            Field minutes = Field.parseMinutes(fields[1]);
            Field hours = Field.parseHours(fields[2]);
            Field days = Field.parseDays(fields[3]);
            Field months = Field.parseMonth(fields[4]);
            Field years = Field.parseYears(fields[5]);

            return new FixedRateExpression(seconds, minutes, hours, days, months, years, expression);
        } catch (IllegalArgumentException ex) {
            String msg = ex.getMessage() + " in fixedRate expression \"" + expression + "\"";
            throw new IllegalArgumentException(msg, ex);
        }
    }

    public Date next(Date startTime) {
        LocalDateTime nextTime = next(LocalDateTime.ofInstant(startTime.toInstant(), ZoneId.systemDefault()));
        return Date.from(nextTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public LocalDateTime next(LocalDateTime startTime) {
        for (Field field : fields) {
            startTime = startTime.plus(field.length, field.unit);
        }
        return startTime;
    }

    private static class Field {
        private final long length;

        private final ChronoUnit unit;

        Field(long length, ChronoUnit unit) {
            this.length = length;
            this.unit = unit;
        }

        private static Field parseSeconds(String value) {
            return new Field(Long.valueOf(value), ChronoUnit.SECONDS);
        }

        private static Field parseMinutes(String value) {
            return new Field(Long.valueOf(value), ChronoUnit.MINUTES);
        }

        public static Field parseHours(String value) {
            return new Field(Long.valueOf(value), ChronoUnit.HOURS);
        }

        public static Field parseDays(String value) {
            return new Field(Long.valueOf(value), ChronoUnit.DAYS);
        }

        public static Field parseMonth(String value) {
            return new Field(Long.valueOf(value), ChronoUnit.MONTHS);
        }

        public static Field parseYears(String value) {
            return new Field(Long.valueOf(value), ChronoUnit.YEARS);
        }

    }

}