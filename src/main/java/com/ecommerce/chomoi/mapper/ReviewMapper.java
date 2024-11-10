package com.ecommerce.chomoi.mapper;

import com.ecommerce.chomoi.dto.review.ReviewPendingResponse;
import com.ecommerce.chomoi.dto.review.ReviewRequest;
import com.ecommerce.chomoi.dto.review.ReviewResponse;
import com.ecommerce.chomoi.entities.Account;
import com.ecommerce.chomoi.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "skuId", source = "sku.id")
    ReviewPendingResponse toReviewPendingResponse(Review review);

    List<ReviewPendingResponse> toListReviewPendingResponse(List<Review> reviews);

    void toReview(@MappingTarget Review review, ReviewRequest reviewRequest);

    ReviewResponse.AccountResponse toAccountResponse(Account account);

    @Mapping(target = "buyer", source = "order.buyer")
    ReviewResponse toReviewResponse(Review review);

    List<ReviewResponse> toListReviewResponse(List<Review> reviews);
}
