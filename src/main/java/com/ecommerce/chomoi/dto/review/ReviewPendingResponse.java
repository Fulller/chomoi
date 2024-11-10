package com.ecommerce.chomoi.dto.review;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewPendingResponse {
    String id;
    String orderId;
    String skuId;
}
