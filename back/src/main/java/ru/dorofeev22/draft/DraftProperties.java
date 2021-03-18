package ru.dorofeev22.draft;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
public class DraftProperties {

    private String paymentSecretKey;
    
    public String getPaymentSecretKey() {
        return paymentSecretKey;
    }
    
    public void setPaymentSecretKey(String paymentSecretKey) {
        this.paymentSecretKey = paymentSecretKey;
    }
}