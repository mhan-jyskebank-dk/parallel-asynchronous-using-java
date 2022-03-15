package com.learnjava.service;

import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.ProductOption;

import java.util.Arrays;
import java.util.List;

import static com.learnjava.util.Timer.delay;

public class ProductInfoService {

    public ProductInfo retrieveProductInfo(String productId) {
        delay(1000);
        List<ProductOption> productOptions = Arrays.asList(
                new ProductOption(1, "64GB", "Black", 699.99),
                new ProductOption(2, "128GB", "Black", 749.99)
        );
        return ProductInfo.builder()
                .productId(productId)
                .productOptions(productOptions)
                .build();
    }
}
