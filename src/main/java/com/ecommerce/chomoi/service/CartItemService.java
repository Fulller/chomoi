package com.ecommerce.chomoi.service;

import com.ecommerce.chomoi.dto.cart.CartResponse;
import com.ecommerce.chomoi.dto.cart_item.CartItemRequest;
import com.ecommerce.chomoi.dto.cart_item.CartItemResponse;
import com.ecommerce.chomoi.entities.Cart;
import com.ecommerce.chomoi.entities.CartItem;
import com.ecommerce.chomoi.entities.SKU;
import com.ecommerce.chomoi.entities.embeddedIds.CartItemId;
import com.ecommerce.chomoi.mapper.CartItemMapper;
import com.ecommerce.chomoi.repository.CartItemRepository;
import com.ecommerce.chomoi.repository.CartRepository;
import com.ecommerce.chomoi.repository.SKURepository;
import com.ecommerce.chomoi.security.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final SKURepository skuRepository;
    private final CartItemMapper cartItemMapper;
    private final SecurityUtil securityUtil;
    private final CartService cartService;
    public boolean checkCartItemExists(String cartId, String skuId) {
        CartItemId cartItemId = new CartItemId(cartId, skuId);
        return cartItemRepository.existsById(cartItemId);
    }

    public CartItemResponse addToCart(CartItemRequest request) {
        String idCart = cartService.get().getId();
        Cart cart = cartRepository.findById(idCart)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        SKU sku = skuRepository.findById(request.getSkuId())
                .orElseThrow(() -> new RuntimeException("SKU not found"));
        CartItemId cartItemId = new CartItemId(idCart, request.getSkuId());
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseGet(() ->
                CartItem.builder()
                        .id(cartItemId)
                        .cart(cart)
                        .sku(sku)
                        .quantity(request.getQuantity())
                        .build()
        );
        if (cartItemRepository.existsById(cartItemId)) {
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        }
        cartItemRepository.save(cartItem);
        return cartItemMapper.toCartItemResponse(cartItem);
    }
}
