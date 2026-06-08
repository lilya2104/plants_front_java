package ru.rutmiit.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.dto.AddProductDto;
import ru.rutmiit.dto.ShowOrdersInfoDto;
import ru.rutmiit.dto.ShowProductInfoDto;
import ru.rutmiit.models.entities.Order;
import ru.rutmiit.models.entities.Product;
import ru.rutmiit.models.entities.Properties;
import ru.rutmiit.models.entities.User;
import ru.rutmiit.models.exceptions.ProductNotFoundException;
import ru.rutmiit.repositories.OrderRepository;
import ru.rutmiit.repositories.ProductRepository;
import ru.rutmiit.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true) // все методы по умолчанию только читают данные
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper; // ModelMapper — автоматически маппит Entity → DTO и DTO → Entity

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, ModelMapper mapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
        log.info("OrderServiceImpl инициализирован");
    }

    @Override
    @Transactional // если все действия метода успешны - добавляет, если даже что-то одно не выполнилось - не добавляет
    // Транзакция — это группа операций с БД, которые выполняются как единое целое.
    @CacheEvict(cacheNames = "orders", allEntries = true) // очищение кэша (после успешного выполнения метода)
    public void createOrder(String userName, String productName, int count) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        Product product = productRepository.findByName(productName)
                .orElseThrow(() -> new ProductNotFoundException(productName));

        Order order = new Order(user, product, count);

        orderRepository.save(order);
    }

    @Override
    public List<ShowOrdersInfoDto> getOrdersByUsername(String userName) {
        log.debug("Получение заказов пользователя {}", userName);
        List<Order> orders = orderRepository.findByUserUsername(userName);
        List<ShowOrdersInfoDto> results = orders.stream()
                .map(order -> mapper.map(order, ShowOrdersInfoDto.class))
                .collect(Collectors.toList());
        log.info("Найдено заказов: {}", orders.size());
        return results;
    }
}
