package com.example.demoapi.service;

import com.example.demoapi.model.ProductInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FetchService {

    private final CloseableHttpClient httpClient;
    private final BasicCookieStore cookieStore;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FetchService() {
        cookieStore = new BasicCookieStore();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10_000, java.util.concurrent.TimeUnit.MILLISECONDS)
                .setResponseTimeout(15_000, java.util.concurrent.TimeUnit.MILLISECONDS)
                .build();

        httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    public void refreshCookie() {
        HttpGet get = new HttpGet("https://www.yodobashi.com/ws/api/ec/cookieTemplate");
        get.addHeader("Accept", "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01");
        get.addHeader("Accept-Language", "en-US,en;q=0.9");
        get.addHeader("Connection", "keep-alive");
        get.addHeader("Content-Type", "application/x-www-form-urlencoded");
        get.addHeader("Sec-Fetch-Dest", "empty");
        get.addHeader("Sec-Fetch-Mode", "cors");
        get.addHeader("Sec-Fetch-Site", "same-origin");
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/141.0.0.0 Safari/537.36");
        get.addHeader("X-Requested-With","XMLHttpRequest");
        get.addHeader("contentFilterLevel","");
        get.addHeader("sec-ch-ua","\"Microsoft Edge\";v=\"141\", \"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"141\"");
        get.addHeader("sec-ch-ua-mobile","?0");
        get.addHeader("sec-ch-ua-platform","\"Windows\"");
        get.addHeader("Referer", "https://www.yodobashi.com/");

        try (CloseableHttpResponse resp = httpClient.execute(get)) {
            EntityUtils.consumeQuietly(resp.getEntity());
            System.out.println("Cookies refreshed: " + cookieStore.getCookies());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ProductInfo fetchProductInfo(final String productId) {
        refreshCookie();
        try {
            var request = new org.apache.hc.client5.http.classic.methods.HttpGet(
                    "https://www.yodobashi.com/ws/api/ec/vari/nouki"
                            + "?sku=" + productId + "&from=1&to=20&cnt=1760772684466"
                            + "&callback=jQuery1710010926910289582925_1760772681971"
                            + "&_=1760772684467"
            );
            request.addHeader("Accept", "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01");
            request.addHeader("Accept-Language", "en-US,en;q=0.9");
            request.addHeader("Connection", "keep-alive");
            request.addHeader("Content-Type", "application/x-www-form-urlencoded");
            request.addHeader("Referer", "https://www.yodobashi.com/product/" + productId +"/");
            request.addHeader("Sec-Fetch-Dest", "empty");
            request.addHeader("Sec-Fetch-Mode", "cors");
            request.addHeader("Sec-Fetch-Site", "same-origin");
            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/141.0.0.0 Safari/537.36");
            request.addHeader("X-Requested-With","XMLHttpRequest");
            request.addHeader("contentFilterLevel","");
            request.addHeader("sec-ch-ua","\"Microsoft Edge\";v=\"141\", \"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"141\"");
            request.addHeader("sec-ch-ua-mobile","?0");
            request.addHeader("sec-ch-ua-platform","\"Windows\"");

            var response = httpClient.execute(request);

            if (response == null) return new ProductInfo(false, false);;

            String body = EntityUtils.toString(response.getEntity());
            Pattern pattern = Pattern.compile("\\{.*\\}", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(body);
            if (!matcher.find()) return new ProductInfo(false, false);
            String json = matcher.group(0);

            JsonNode root = objectMapper.readTree(json);
            JsonNode products = root.path("products");
            if (products.isArray()) {
                for (JsonNode product : products) {
                    String sku = product.path("sku").asText();
                    if (productId.equals(product.path("sku").asText())) {
                        boolean displayBuyboxSub = product.path("isDisplayBuyboxSub").asBoolean(false);
                        boolean enableDeliveryTag = product.path("isEnableDeliveryTag").asBoolean(false);
                        return new ProductInfo(displayBuyboxSub, enableDeliveryTag);
                    }
                }
            }

            return new ProductInfo(false, false);
        } catch (Exception e) {
            e.printStackTrace();
            return new ProductInfo(false, false);
        }
    }
}
