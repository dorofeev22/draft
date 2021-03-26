package ru.dorofeev22.draft.core.crypt;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class HmacHelperTest {
    
    private static final String SECRET_KEY = "fiPZL3bh36am8mdbOQmm";
    private static final String SIGNING_DATA = "There is a top secret letter";

    @Autowired
    private HmacHelper hmacHelper;
    
    @Test
    public void calculateHmacSha256Test() throws UnsupportedEncodingException, InvalidKeyException {
        final String expectedSign = "3dade52b058ab1fd6306f7063cb0b160bc70d794c83a8bd24aae6ceb56e7fde8";
        Assert.assertEquals(expectedSign, hmacHelper.calculateHmacSha256(SECRET_KEY, SIGNING_DATA));
    }
}