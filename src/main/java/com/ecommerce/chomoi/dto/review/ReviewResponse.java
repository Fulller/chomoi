package com.ecommerce.chomoi.dto.review;

import com.ecommerce.chomoi.entities.Order;
import com.ecommerce.chomoi.entities.SKU;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewResponse {
    String id;
    SKU sku;
    Order order;
    String rating;
    String comment;
    String image;
    String video;
}
