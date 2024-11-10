package com.ecommerce.chomoi.service;

import com.ecommerce.chomoi.dto.review.ReviewResponse;
import com.ecommerce.chomoi.entities.Order;
import com.ecommerce.chomoi.entities.Review;
import com.ecommerce.chomoi.mapper.ReviewMapper;
import com.ecommerce.chomoi.repository.OrderRepository;
import com.ecommerce.chomoi.repository.ReviewRepository;
import com.ecommerce.chomoi.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final SecurityUtil securityUtil;
    private  final ReviewMapper reviewMapper;

    public List<ReviewResponse> getAllByBuyer() {
        String idBuyer = securityUtil.getAccountId();  // Lấy ID người mua
        List<Order> orders = orderRepository.findByBuyerId(idBuyer);  // Lấy danh sách đơn hàng của người mua
        List<Review> allReviews = new ArrayList<>();  // Khởi tạo danh sách chứa tất cả đánh giá

        // Duyệt qua từng đơn hàng để lấy các đánh giá cho mỗi đơn hàng
        for (Order order : orders) {
            String orderId = order.getId();  // Lấy ID đơn hàng
            // Tìm các review cho người mua và trạng thái tương ứng
            List<Review> reviews = reviewRepository.findByBuyerAndStatus(orderId);  // Truyền trạng thái là "PENDING"
            allReviews.addAll(reviews);  // Thêm các review vào danh sách chung
        }

        // Chuyển danh sách Review thành ReviewResponse (nếu cần)
        return reviewMapper.toListReviewResponse(allReviews);  // Chuyển đổi và trả về kết quả
    }
}
