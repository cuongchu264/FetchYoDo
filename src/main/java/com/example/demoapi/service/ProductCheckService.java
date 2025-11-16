package com.example.demoapi.service;

import com.example.demoapi.model.ProductInfo;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProductCheckService {

    private final FetchService fetchService;
    private final MailService mailService;

    public ProductCheckService(FetchService fetchService, MailService mailService) {
        this.fetchService = fetchService;
        this.mailService = mailService;
    }

    public void checkAndNotify(final String productId, final String email) throws IOException {
        ProductInfo info = fetchService.fetchProductInfo(productId);

        if (info.isDisplayBuyboxSub() || info.isEnableDeliveryTag()) {
            String body = "Product SKU: " + productId + "\n"
                    + "Product name: " + info.getProductName() + "\n"
                    + "isDisplayBuyboxSub: " + info.isDisplayBuyboxSub() + "\n"
                    + "isEnableDeliveryTag: " + info.isEnableDeliveryTag() + "\n"
                    + "point: " + info.getPoint() + "\n"
                    + "point Price: " + info.getPointRate() + "\n"
                    + "point Rate: " + info.getPointRate() + "\n"
                    + "The product has been opened \n"
                    + "https://www.yodobashi.com/product/" + productId + "/";

            mailService.sendEmail(email, "Product " + info.getProductName() + " has opened", body);
        } else {
            System.out.println("No product available!");
        }
    }
}