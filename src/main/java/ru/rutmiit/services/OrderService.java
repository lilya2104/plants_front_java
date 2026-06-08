package ru.rutmiit.services;

import ru.rutmiit.dto.ShowOrdersInfoDto;

import java.util.List;

public interface OrderService {
    void createOrder(String userName, String productName, int count);
    List<ShowOrdersInfoDto> getOrdersByUsername(String username);
}
