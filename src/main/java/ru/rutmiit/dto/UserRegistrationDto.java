package ru.rutmiit.dto;

import jakarta.validation.constraints.*;
import ru.rutmiit.utils.validation.UniqueEmail;
import ru.rutmiit.utils.validation.UniqueUsername;

public class UserRegistrationDto {
    @UniqueUsername
    private String username;
    @UniqueEmail
    private String email;
    private String shippingAddress;
    private String password;
    private String confirmPassword;

    public UserRegistrationDto() {}

    @NotEmpty(message = "Имя пользователя не должно быть пустым!")
    @Size(min = 5, max = 20, message = "Имя пользователя должно быть от 5 до 20 символов!")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$",
            message = "Имя пользователя должно содержать только латинские буквы, цифры и символ подчеркивания")
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = "Email не должен быть пустым!")
    @Email(message = "Введите корректный email!")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Адрес не должен быть пустым!")
    public String getShippingAddress() {
        return shippingAddress;
    }
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @NotEmpty(message = "Пароль не должен быть пустым!")
    @Size(min = 5, max = 20, message = "Пароль должен быть от 5 до 20 символов!")
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "Подтверждение пароля не должно быть пустым!")
    @Size(min = 5, max = 20, message = "Подтверждение пароля должно быть от 5 до 20 символов!")
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", shippingAddress=" + shippingAddress +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
