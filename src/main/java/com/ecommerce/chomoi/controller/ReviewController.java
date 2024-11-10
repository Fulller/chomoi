package com.ecommerce.chomoi.controller;

import com.ecommerce.chomoi.dto.api.ApiResponse;
import com.ecommerce.chomoi.dto.review.ReviewResponse;
import com.ecommerce.chomoi.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private  final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/getPending")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getPending(){
        ApiResponse<List<ReviewResponse>> responses = ApiResponse.<List<ReviewResponse>>builder()
                .code("review-s-01")
                .message("get review successfully")
                .data(reviewService.getAllByBuyer())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
