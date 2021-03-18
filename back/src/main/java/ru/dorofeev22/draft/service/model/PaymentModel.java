package ru.dorofeev22.draft.service.model;

import ru.dorofeev22.draft.core.utils.CalculateSignature;

import javax.validation.constraints.NotNull;

public class PaymentModel {
    
    @NotNull
    @CalculateSignature(order = 0)
    private String orderId;
    
    @NotNull
    @CalculateSignature(order = 1)
    private String amount;
    
    @NotNull
    @CalculateSignature(order = 2)
    private String info;
    
    private String signature;
    
    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public String getAmount() {
        return amount;
    }
    
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    public String getInfo() {
        return info;
    }
    
    public void setInfo(String info) {
        this.info = info;
    }
    
    public String getSignature() {
        return signature;
    }
    
    public void setSignature(String signature) {
        this.signature = signature;
    }

}