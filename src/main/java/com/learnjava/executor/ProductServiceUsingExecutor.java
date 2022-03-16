package com.learnjava.executor;

import com.learnjava.domain.Product;
import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.Review;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.learnjava.util.Logger.log;
import static com.learnjava.util.Timer.startTimer;
import static com.learnjava.util.Timer.timeTaken;

public class ProductServiceUsingExecutor {

    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    public ProductServiceUsingExecutor(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws ExecutionException, InterruptedException {
        startTimer();

        Future<ProductInfo> productInfoFuture = executorService.submit(() -> productInfoService.retrieveProductInfo(productId));
        Future<Review> reviewFuture = executorService.submit(() -> reviewService.retrieveReviews(productId));

        ProductInfo productInfo = productInfoFuture.get();
        Review review = reviewFuture.get();

        timeTaken();

        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingExecutor productService = new ProductServiceUsingExecutor(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);
        executorService.shutdown();
    }
}
