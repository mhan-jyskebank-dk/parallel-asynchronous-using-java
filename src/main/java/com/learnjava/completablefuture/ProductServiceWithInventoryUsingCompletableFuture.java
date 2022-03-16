package com.learnjava.completablefuture;

import com.learnjava.domain.Product;
import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.ProductOption;
import com.learnjava.domain.Review;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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

        CompletableFuture<ProductInfo> productInfoCompletableFuture =
                CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                        .thenApply(productInfo -> {
                            updateInventory(productInfo);
                            return productInfo;
                        });
        CompletableFuture<Review> reviewCompletableFuture =
                CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = productInfoCompletableFuture
                .thenCombine((reviewCompletableFuture), (productInfo, review) -> new Product(productId, productInfo, review))
                .join();

        timeTaken();

        return product;
    }

    private void updateInventory(ProductInfo productInfo) {
        List<CompletableFuture<ProductOption>> productOptionCfs = productInfo.getProductOptions()
                .stream()
                .map(productOption -> {
                    return CompletableFuture.supplyAsync(() -> inventoryService.retrieveInventory(productOption))
                            .thenApply(inventory -> {
                                productOption.setInventory(inventory);
                                return productOption;
                            });
                })
                .collect(Collectors.toList());

        productOptionCfs
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
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
