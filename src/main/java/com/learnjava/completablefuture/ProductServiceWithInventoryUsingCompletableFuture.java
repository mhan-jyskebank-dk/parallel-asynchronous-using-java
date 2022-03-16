package com.learnjava.completablefuture;

import com.learnjava.domain.Product;
import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.ProductOption;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import java.util.Collections;
import java.util.List;

import static com.learnjava.util.Logger.log;
import static com.learnjava.util.Timer.startTimer;
import static com.learnjava.util.Timer.timeTaken;

public class ProductServiceWithInventoryUsingCompletableFuture {
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;
    private final InventoryService inventoryService;

    public ProductServiceWithInventoryUsingCompletableFuture(
            ProductInfoService productInfoService,
            ReviewService reviewService,
            InventoryService inventoryService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;
    }

    public Product retrieveProductDetails(String productId) {
        startTimer();

        // your code goes here

        timeTaken();

        return new Product();
    }

    private List<ProductOption> updateInventory(ProductInfo productInfo) {

        // your code goes here
        // hint: combine streams and completable futures

        return Collections.emptyList();
    }

    public static void main(String[] args) {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        InventoryService inventoryService = new InventoryService();
        ProductServiceWithInventoryUsingCompletableFuture productService =
                new ProductServiceWithInventoryUsingCompletableFuture(productInfoService, reviewService, inventoryService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);
    }
}
