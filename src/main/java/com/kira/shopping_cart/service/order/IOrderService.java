package com.kira.shopping_cart.service.order;

import com.kira.shopping_cart.dto.OrderDTO;
import com.kira.shopping_cart.model.Order;

import java.util.List;

public interface IOrderService {
        OrderDTO placeOrder(Long userId);
        OrderDTO getOrderDetails(Long orderId, Long userId);
        OrderDTO getOrder(Long orderId);
        List<OrderDTO> getUserOrders(Long userId);
//        void cancelOrder(Long cartId, Long userId);

}
