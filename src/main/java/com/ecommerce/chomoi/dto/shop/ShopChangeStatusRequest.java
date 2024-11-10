package com.ecommerce.chomoi.dto.shop;

import com.ecommerce.chomoi.enums.ShopStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShopChangeStatusRequest {
    @NotNull
    ShopStatus status;
}
