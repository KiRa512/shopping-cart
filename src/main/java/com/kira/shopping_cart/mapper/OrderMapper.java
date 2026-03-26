package com.kira.shopping_cart.mapper;

import com.kira.shopping_cart.dto.OrderDTO;
import com.kira.shopping_cart.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderStatus", target = "status")
    @Mapping(source = "orderItems", target = "items")
    @Mapping(target = "message", ignore = true)
    OrderDTO orderToOrderDTO(Order order);

    List<OrderDTO> orderToOrderDTOList(List<Order> orders);
}
