package ru.rutmiit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rutmiit.models.entities.Order;
import ru.rutmiit.models.entities.User;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUserUsername(String username);

}
