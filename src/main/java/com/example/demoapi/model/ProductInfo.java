package com.example.demoapi.model;

public class ProductInfo {
    private final boolean isDisplayBuyboxSub;
    private final boolean isEnableDeliveryTag;
    private final String productName;
    private final String point;
    private final String pointPrice;
    private final String pointRate;

    public ProductInfo(boolean isDisplayBuyboxSub, boolean isEnableDeliveryTag, String productName, String point, String pointPrice, String pointRate) {
        this.isDisplayBuyboxSub = isDisplayBuyboxSub;
        this.isEnableDeliveryTag = isEnableDeliveryTag;
        this.productName = productName;
        this.point = point;
        this.pointPrice = pointPrice;
        this.pointRate = pointRate;
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

    public String getPoint() {
        return point;
    }

    public String getPointPrice() {
        return pointPrice;
    }

    public String getPointRate() {
        return pointRate;
    }
}
