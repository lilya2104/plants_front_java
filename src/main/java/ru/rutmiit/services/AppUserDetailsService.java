package ru.rutmiit.services;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.rutmiit.models.entities.User;
import ru.rutmiit.repositories.UserRepository;

import java.util.stream.Collectors;

public class AppUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // Ищем пользователя в БД
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Пользователь " + username + " не найден!"));

        // Конвертируем в Spring Security User
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                // Конвертируем роли в GrantedAuthority
                user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                        .collect(Collectors.toList())
        );
    }
}