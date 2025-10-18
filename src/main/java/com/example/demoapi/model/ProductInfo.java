package com.example.demoapi.model;

public class ProductInfo {
    private final boolean isDisplayBuyboxSub;
    private final boolean isEnableDeliveryTag;

    public ProductInfo(boolean isDisplayBuyboxSub, boolean isEnableDeliveryTag) {
        this.isDisplayBuyboxSub = isDisplayBuyboxSub;
        this.isEnableDeliveryTag = isEnableDeliveryTag;
    }

    public boolean isDisplayBuyboxSub() {
        return isDisplayBuyboxSub;
    }

    public boolean isEnableDeliveryTag() {
        return isEnableDeliveryTag;
    }
}
