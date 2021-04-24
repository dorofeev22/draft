package ru.dorofeev22.draft.core.utils;

import java.math.BigDecimal;

public class UrlParam {

    private final String stringValue;
    private final Integer integerValue;
    private final Long longValue;
    private final BigDecimal bigDecimalValue;

    public UrlParam(String value) {
        this(value, null, null, null);
    }

    public UrlParam(Integer value) {
        this(null, value, null, null);
    }

    public UrlParam(Long value) {
        this(null, null, value, null);
    }

    public UrlParam(BigDecimal value) {
        this(null, null, null, value);
    }

    public UrlParam(String stringValue, Integer integerValue, Long longValue, BigDecimal bigDecimalValue) {
        this.stringValue = stringValue;
        this.integerValue = integerValue;
        this.longValue = longValue;
        this.bigDecimalValue = bigDecimalValue;
    }

    public String getValue() {
        return bigDecimalValue != null ? bigDecimalValue.toPlainString()
                : longValue != null ? longValue.toString()
                : integerValue != null ? integerValue.toString()
                : stringValue;
    }

}