package com.example.demoapi.service;

import com.example.demoapi.model.ProductInfo;
import org.springframework.stereotype.Service;

@Service
public class ProductCheckService {

    private final FetchService fetchService;
    private final MailService mailService;

    public ProductCheckService(FetchService fetchService, MailService mailService) {
        this.fetchService = fetchService;
        this.mailService = mailService;
    }

    public void checkAndNotify(final String productId, final String email) {
        ProductInfo info = fetchService.fetchProductInfo(productId);

        if (info.isDisplayBuyboxSub() || info.isEnableDeliveryTag()) {
            String body = "Product SKU: " + productId + "\n"
                    + "isDisplayBuyboxSub: " + info.isDisplayBuyboxSub() + "\n"
                    + "isEnableDeliveryTag: " + info.isEnableDeliveryTag() + "\n"
                    + "The product has been opened \n"
                    + "https://www.yodobashi.com/product/" + productId + "/";

            mailService.sendNotification(email, "Product Notification", body);
        }
    }
}