package com.example.demoapi.model;

public class ProductInfo {
    private final boolean isDisplayBuyboxSub;
    private final boolean isEnableDeliveryTag;
    private final String productName;
    private final String point;
    private final String pointPrice;
    private final String pointRate;
    private final String deliveryDateText;

    public ProductInfo(boolean isDisplayBuyboxSub, boolean isEnableDeliveryTag, String productName, String point, String pointPrice, String pointRate,String deliveryDateText) {
        this.isDisplayBuyboxSub = isDisplayBuyboxSub;
        this.isEnableDeliveryTag = isEnableDeliveryTag;
        this.productName = productName;
        this.point = point;
        this.pointPrice = pointPrice;
        this.pointRate = pointRate;
        this.deliveryDateText = deliveryDateText;
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
    public String getDeliveryDateText() {return deliveryDateText;
    }
}
