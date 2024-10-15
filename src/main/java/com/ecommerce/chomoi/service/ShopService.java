package com.ecommerce.chomoi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.chomoi.dto.auth.AuthUpgradeToShop;
import com.ecommerce.chomoi.entities.Account;
import com.ecommerce.chomoi.entities.Shop;
import com.ecommerce.chomoi.repository.AccountRepository;
import com.ecommerce.chomoi.repository.ShopRepository;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    public Optional<Shop> createShop(Shop shop, Account account) {
        shop.setAccount(account);
        return Optional.of(shopRepository.save(shop));
    }

    public Optional<Shop> getShopById(String shopId) {
        return shopRepository.findById(shopId);
    }
}
