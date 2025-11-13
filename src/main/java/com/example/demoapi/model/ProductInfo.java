package com.example.demoapi.model;

public class ProductInfo {
    private final boolean isDisplayBuyboxSub;
    private final boolean isEnableDeliveryTag;
    private final String productName;

    public ProductInfo(boolean isDisplayBuyboxSub, boolean isEnableDeliveryTag, String productName) {
        this.isDisplayBuyboxSub = isDisplayBuyboxSub;
        this.isEnableDeliveryTag = isEnableDeliveryTag;
        this.productName = productName;
    }

    public boolean isDisplayBuyboxSub() {
        return isDisplayBuyboxSub;
    }

    public boolean isEnableDeliveryTag() {
        return isEnableDeliveryTag;
    }

    public String getProductName() {
        return productName;
    }
}
