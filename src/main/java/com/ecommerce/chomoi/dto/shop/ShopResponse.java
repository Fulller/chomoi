package com.ecommerce.chomoi.dto.shop;


import com.ecommerce.chomoi.entities.*;
import com.ecommerce.chomoi.enums.ShopStatus;
import lombok.*;


@Data
public class ShopResponse {
    String id;

    String name;

    String avatar;

    String cover_image;

    Double rating;

    ShopStatus status;

    Address address;
}
