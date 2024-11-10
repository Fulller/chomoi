package com.ecommerce.chomoi.dto.review;

import com.ecommerce.chomoi.dto.cart_item.CartItemResponse;
import com.ecommerce.chomoi.entities.SKU;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewResponse {
    String id;
    SKU sku;
    CartItemResponse.ProductResponse product;
    AccountResponse buyer;
    Integer rating;
    String comment;
    String image;
    String video;
    String response;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class AccountResponse {
        String id;
        String displayName;
        String avatar;
    }
}
