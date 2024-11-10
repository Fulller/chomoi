package com.ecommerce.chomoi.controller;

import com.ecommerce.chomoi.dto.api.ApiResponse;
import com.ecommerce.chomoi.dto.review.ReviewFeedbackRequest;
import com.ecommerce.chomoi.dto.review.ReviewPendingResponse;
import com.ecommerce.chomoi.dto.review.ReviewRequest;
import com.ecommerce.chomoi.dto.review.ReviewResponse;
import com.ecommerce.chomoi.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<ReviewPendingResponse>>> getPending() {
        ApiResponse<List<ReviewPendingResponse>> responses = ApiResponse.<List<ReviewPendingResponse>>builder()
                .code("review-s-01")
                .message("get review successfully")
                .data(reviewService.getAllPendingByOrderBuyer())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @PostMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> create(@PathVariable String reviewId, @RequestBody @Valid ReviewRequest reviewRequest) {
        ApiResponse<ReviewResponse> responses = ApiResponse.<ReviewResponse>builder()
                .code("review-s-02")
                .message("Do review successfully")
                .data(reviewService.create(reviewId, reviewRequest))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/{productId}/product")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> create(@PathVariable String productId) {
        ApiResponse<List<ReviewResponse>> responses = ApiResponse.<List<ReviewResponse>>builder()
                .code("review-s-03")
                .message("Get all review by product id successfully")
                .data(reviewService.getAllByProductId(productId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PreAuthorize("hasRole('SHOP')")
    @GetMapping("/{reviewId}/response")
    public ResponseEntity<ApiResponse<ReviewResponse>> shopResponse(@PathVariable String reviewId, @RequestBody @Valid ReviewFeedbackRequest request) {
        ApiResponse<ReviewResponse> responses = ApiResponse.<ReviewResponse>builder()
                .code("review-s-04")
                .message("Shop responses review successfully")
                .data(reviewService.response(reviewId, request.getResponse()))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }


}
