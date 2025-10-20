package com.example.demoapi.controller;

import com.example.demoapi.model.ProductInfo;
import com.example.demoapi.service.FetchService;
import com.example.demoapi.service.ProductCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FetchController {

    @Autowired
    private FetchService fetchService;

    @Autowired
    private ProductCheckService productCheckService;

    @GetMapping("/fetchData/{productId}")
    public ProductInfo fetchData(@PathVariable("productId") String productId) {
        return fetchService.fetchProductInfo(productId);
    }

    @GetMapping("/checkProduct/{productId}")
    public String checkProduct(@PathVariable("productId") String productId) throws IOException {
        productCheckService.checkAndNotify(productId, "tamuratayoshi@gmail.com");
        return "Check completed. If any flag is true, mail has been sent.";
    }

    @GetMapping("/me/checkProduct/{productId}")
    public String meCheckProduct(@PathVariable("productId") String productId) throws IOException {
        productCheckService.checkAndNotify(productId, "cuongchu12369@gmail.com");
        return "Check completed. If any flag is true, mail has been sent.";
    }
}
