package com.ecommerce.chomoi.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRequest {
    @NotNull(message = "Rating must be not blank")
    @Min(1)
    @Max(5)
    Integer rating;

    @NotBlank(message = "Comment must be not blank")
    String comment;

    String image;

    String video;
}
