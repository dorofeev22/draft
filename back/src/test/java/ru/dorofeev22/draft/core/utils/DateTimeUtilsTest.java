package ru.dorofeev22.draft.core.utils;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnit4.class)
public class DateTimeUtilsTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Test
    @Ignore
    public void getNowAsInstantTest() {
        String result = DateTimeUtils.getNowAsInstant();
        log.info("now is " + result);
    }

}