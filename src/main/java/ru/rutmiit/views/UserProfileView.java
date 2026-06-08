package ru.rutmiit.views;

import ru.rutmiit.dto.ShowOrdersInfoDto;

import java.util.ArrayList;
import java.util.List;

public class UserProfileView {
    private String username;

    private String email;

    private String shippingAddress;

    private List<ShowOrdersInfoDto> ordersInfoDto;

    public UserProfileView(String username, String email, String shippingAddress, List<ShowOrdersInfoDto> ordersInfoDto) {
        this.username = username;
        this.email = email;
        this.shippingAddress = shippingAddress;
        this.ordersInfoDto = ordersInfoDto;
    }

    public UserProfileView(String username, String email, String shippingAddress) {
        this.username = username;
        this.email = email;
        this.shippingAddress = shippingAddress;
        this.ordersInfoDto = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<ShowOrdersInfoDto> getOrdersInfoDto() {
        return ordersInfoDto;
    }

    public void setOrdersInfoDto(List<ShowOrdersInfoDto> ordersInfoDto) {
        this.ordersInfoDto = ordersInfoDto;
    }
}
