package ru.rutmiit.services;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.dto.ShowOrdersInfoDto;
import ru.rutmiit.dto.ShowProductInfoDto;
import ru.rutmiit.dto.UserRegistrationDto;
import ru.rutmiit.models.entities.Order;
import ru.rutmiit.models.entities.Product;
import ru.rutmiit.models.entities.User;
import ru.rutmiit.models.enums.UserRoles;
import ru.rutmiit.repositories.OrderRepository;
import ru.rutmiit.repositories.UserRepository;
import ru.rutmiit.repositories.UserRoleRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, OrderRepository orderRepository, PasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    @Transactional // Транзакция — это группа операций с БД, которые выполняются как единое целое.
    public void register(UserRegistrationDto registrationDTO) {
        // проверка совпадения паролей
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            throw new RuntimeException("Пароли не совпадают!");
        }
        // проверка уникальности email
        if (userRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email уже используется!");
        }
        // получаем роль USER
        var userRole = userRoleRepository.findRoleByName(UserRoles.USER)
                .orElseThrow(() -> new RuntimeException("Роль USER не найдена!"));
        // создаём пользователя
        User user = new User(
                registrationDTO.getUsername(),
                registrationDTO.getEmail(),
                registrationDTO.getShippingAddress(),
                passwordEncoder.encode(registrationDTO.getPassword()) // шифруем пароль!
        );
        // назначаем роль
        user.setRoles(List.of(userRole));
        // сохраняем
        userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " не найден!"));
    }
}
