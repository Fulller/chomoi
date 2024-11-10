package com.ecommerce.chomoi.service;

import com.ecommerce.chomoi.dto.review.ReviewPendingResponse;
import com.ecommerce.chomoi.dto.review.ReviewRequest;
import com.ecommerce.chomoi.dto.review.ReviewResponse;
import com.ecommerce.chomoi.entities.Review;
import com.ecommerce.chomoi.enums.ReviewStatus;
import com.ecommerce.chomoi.exception.AppException;
import com.ecommerce.chomoi.mapper.ReviewMapper;
import com.ecommerce.chomoi.repository.ReviewRepository;
import com.ecommerce.chomoi.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final SecurityUtil securityUtil;
    private final ReviewMapper reviewMapper;


    public List<ReviewPendingResponse> getAllPendingByOrderBuyer() {
        String buyerId = securityUtil.getAccountId();
        List<Review> reviews = reviewRepository.getAllPendingByOrderBuyerId(buyerId);
        return reviewMapper.toListReviewPendingResponse(reviews);
    }

    public ReviewResponse create(String reviewId, ReviewRequest request) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND, "Review not found", "review-e-01")
        );
        reviewMapper.toReview(review, request);
        review.setStatus(ReviewStatus.PUBLISHED);
        reviewRepository.save(review);
        return reviewMapper.toReviewResponse(review);
    }

    public List<ReviewResponse> getAllByProductId(String productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviewMapper.toListReviewResponse(reviews);
    }

    public ReviewResponse response(String reviewId, String responseString) {
        String shopId = securityUtil.getShopId();
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND, "Review not found", "review-e-01")
        );
        if (!shopId.equals(review.getProduct().getShop().getId())) {
            throw new AppException(HttpStatus.NOT_FOUND, "Not shop", "review-e-02");
        }
        review.setResponse(responseString);
        reviewRepository.save(review);
        return reviewMapper.toReviewResponse(review);
    }
}
