package com.kira.shopping_cart.service.order;

import com.kira.shopping_cart.OrderStatus;
import com.kira.shopping_cart.dto.OrderDTO;
import com.kira.shopping_cart.exceptions.ResourceNotFoundException;
import com.kira.shopping_cart.mapper.OrderMapper;
import com.kira.shopping_cart.model.Cart;
import com.kira.shopping_cart.model.Order;
import com.kira.shopping_cart.model.OrderItem;
import com.kira.shopping_cart.model.Product;
import com.kira.shopping_cart.repo.OrderRepo;
import com.kira.shopping_cart.repo.ProductRepo;
import com.kira.shopping_cart.service.cart.ICartService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
@AllArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final ICartService cartService;
//    private final CartItemRepo cartItemRepo;
    private final OrderMapper orderMapper;

    @Override
    public OrderDTO placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(cart, order);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(getTotalPrice(orderItems));
        Order savedOrder = orderRepo.save(order);
        cartService.clearCart(cart.getId());
        return orderMapper.orderToOrderDTO(savedOrder);

    }


    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    @Transactional
    private List<OrderItem> createOrderItems(Cart cart, Order order) {

        List<Product> updatedProducts = new ArrayList<>();

        List<OrderItem> orderItems = cart.getItems()
                .stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();

                    if (product.getInventory() < cartItem.getQuantity()) {
                        throw new RuntimeException(
                                "Not enough stock for product: " + product.getName()
                        );
                    }

                    product.setInventory(
                            product.getInventory() - cartItem.getQuantity()
                    );

                    updatedProducts.add(product);

                    return new OrderItem(
                            order,
                            product,
                            cartItem.getQuantity(),
                            product.getPrice()
                    );
                })
                .toList();

        // Batch save (better performance)
        productRepo.saveAll(updatedProducts);

        return orderItems;
    }


    private BigDecimal getTotalPrice(List<OrderItem> orderItem) {
        return orderItem.stream()
                .map(item-> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDTO getOrderDetails(Long orderId, Long userId) {
        return null;
    }

    @Override
    public OrderDTO getOrder(Long orderId) {
        return orderRepo.findById(orderId)
                .map(orderMapper::orderToOrderDTO)
                .orElseThrow(()-> new ResourceNotFoundException("order not found"));
    }

    @Override
    public List<OrderDTO> getUserOrders(Long userId) {
        List<Order> orders = orderRepo.findByUserId(userId);
        return orderMapper.orderToOrderDTOList(orders);
    }
}
