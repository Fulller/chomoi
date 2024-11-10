package com.ecommerce.chomoi.repository;

import com.ecommerce.chomoi.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    @Query("SELECT r FROM Review r WHERE r.order.id = :orderId AND r.status = com.ecommerce.chomoi.enums.ReviewStatus.PENDING")
    List<Review> findByBuyerAndStatus(String orderId);

    @Query("SELECT r FROM Review r WHERE r.order.buyer.id = :buyerId AND r.status = com.ecommerce.chomoi.enums.ReviewStatus.PENDING")
    List<Review> getAllPendingByOrderBuyerId(@Param("buyerId") String buyerId);

    List<Review> findByProductId(String productId);
}
