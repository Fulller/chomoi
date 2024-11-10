package com.ecommerce.chomoi.mapper;

import com.ecommerce.chomoi.dto.review.ReviewResponse;
import com.ecommerce.chomoi.entities.Review;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    List<ReviewResponse> toListReviewResponse(List<Review> reviews);
}
