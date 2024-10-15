package com.ecommerce.chomoi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.chomoi.dto.api.ApiResponse;
import com.ecommerce.chomoi.dto.auth.AuthUpgradeToShop;
import com.ecommerce.chomoi.entities.Account;
import com.ecommerce.chomoi.entities.Shop;
import com.ecommerce.chomoi.service.AccountService;
import com.ecommerce.chomoi.service.ShopService;
import com.ecommerce.chomoi.util.jwt.BaseJWTUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Shop> addShop(@RequestBody Shop shop, @RequestParam String accountId) {
        Account account = accountService.getAccountById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found."));

        Shop createdShop = shopService.createShop(shop, account)
                .orElseThrow(() -> new RuntimeException("Shop could not be created."));
        return ResponseEntity.ok(createdShop);
    }
    
    @PutMapping
    public ResponseEntity<Shop> updateShop(@RequestBody Shop shop) {
        Shop updatedShop = shopService.createShop(shop, shop.getAccount())
                .orElseThrow(() -> new RuntimeException("Shop could not be updated."));
        return ResponseEntity.ok(updatedShop);
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<Shop> getShop(@PathVariable String shopId) {
        Shop shop = shopService.getShopById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found."));
        return ResponseEntity.ok(shop);
    }
}