package com.learnjava.blocking;

import com.learnjava.domain.Inventory;
import com.learnjava.domain.Product;
import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.ProductOption;
import com.learnjava.domain.Review;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.Logger.log;
import static com.learnjava.util.Timer.startTimer;
import static com.learnjava.util.Timer.timeTaken;

public class ProductServiceWithInventoryBlocking {
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;
    private final InventoryService inventoryService;

    public ProductServiceWithInventoryBlocking(ProductInfoService productInfoService, ReviewService reviewService, InventoryService inventoryService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;
    }

    public Product retrieveProductDetails(String productId) {
        startTimer();

        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
        Review review = reviewService.retrieveReviews(productId); // blocking call
        productInfo.setProductOptions(updateInventory(productInfo)); // blocking call

        timeTaken();

        return new Product(productId, productInfo, review);
    }

    private List<ProductOption> updateInventory(ProductInfo productInfo) {
        return productInfo.getProductOptions()
                .stream()
                .map(productOption -> {
                    Inventory inventory = inventoryService.retrieveInventory(productOption);
                    productOption.setInventory(inventory);
                    return productOption;
                })
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        InventoryService inventoryService = new InventoryService();
        ProductServiceWithInventoryBlocking productService =
                new ProductServiceWithInventoryBlocking(productInfoService, reviewService, inventoryService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);
    }
}
