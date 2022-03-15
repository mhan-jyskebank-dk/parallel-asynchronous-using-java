package com.learnjava.service;

import com.learnjava.domain.Review;

import static com.learnjava.util.Timer.delay;

public class ReviewService {

    public Review retrieveReviews(String productId) {
        delay(1000);
        return new Review(200, 4.5);
    }
}
