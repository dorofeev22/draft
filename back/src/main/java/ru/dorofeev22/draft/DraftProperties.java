package ru.dorofeev22.draft;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
public class DraftProperties {

    private String paymentSecretKey;
    private String orderPublicKeyPath;
    
    public String getPaymentSecretKey() {
        return paymentSecretKey;
    }
    
    public void setPaymentSecretKey(String paymentSecretKey) {
        this.paymentSecretKey = paymentSecretKey;
    }
    
    public String getOrderPublicKeyPath() {
        return orderPublicKeyPath;
    }
    
    public void setOrderPublicKeyPath(String orderPublicKeyPath) {
        this.orderPublicKeyPath = orderPublicKeyPath;
    }
}