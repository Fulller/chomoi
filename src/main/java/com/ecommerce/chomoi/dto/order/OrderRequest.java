package com.ecommerce.chomoi.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {

    String note;

    @NotBlank(message = "Address is required")
    String addressId;

    @NotEmpty(message = "At least one order item is required")
    @Size(min = 1, message = "Min 1")
    List<@Valid OrderItemDTO> items;

    @NotBlank(message = "Shop is required")
    String shopId;

    @Data
    public static class OrderItemDTO {

        @NotBlank(message = "SKU is required")
        String skuId;

        @NotNull(message = "Quantity is required")
        @Min(value = 0, message = "Quantity must be a non-negative number")
        int quantity;
    }
}
