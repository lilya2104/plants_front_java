package ru.rutmiit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import ru.rutmiit.models.enums.UserRoles;
import ru.rutmiit.repositories.UserRepository;
import ru.rutmiit.services.AppUserDetailsService;

@Slf4j
@Configuration
public class AppSecurityConfiguration {
    private final UserRepository userRepository;

    public AppSecurityConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
        log.info("AppSecurityConfiguration инициализирована");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityContextRepository securityContextRepository) throws Exception {
        http
                // разрешаем все запросы без авторизации
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .logout(logout -> logout.disable())
                .securityContext(securityContext -> securityContext
                        .securityContextRepository(securityContextRepository)
                );

//                // настройка доступа к юрл
//                .authorizeHttpRequests(authorize -> authorize
//                        // ресурсы и ошибки
//                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                        .requestMatchers("/favicon.ico", "/error").permitAll()
//                        // публичные страницы
//                        .requestMatchers("/", "/users/login", "/users/register", "/users/login-error").permitAll()
//                        .requestMatchers("/articles/all", "/articles/article-details/**").permitAll()
//                        .requestMatchers("/products/**").permitAll()
//                        // информация о системе
//                        .requestMatchers("/actuator/**").permitAll() // в реальном проекте нужно ограничить доступ к health и info, остальное только для админа
//                        // аутентификация пользователя
//                        .requestMatchers("/users/profile").authenticated()
////                       // модератор и админ
//                        .requestMatchers("/articles/add")
//                        .hasAnyRole(UserRoles.MODERATOR.name(), UserRoles.ADMIN.name())
//                        // только админ
//                        .requestMatchers("/products/add", "/products/product-delete/*", "/articles/article-delete/*")
//                        .hasRole(UserRoles.ADMIN.name())
//                        .anyRequest().authenticated() // другое требует аутентификации
//                )
//                // настройка формы входа
//                .formLogin(form -> form
//                        .loginPage("/users/login")
//                        .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
//                        .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
//                        .defaultSuccessUrl("/", true)
//                        .failureForwardUrl("/users/login-error")
//                        .permitAll()
//                )
//                // запомнить меня
//                .rememberMe(remember -> remember
//                        .key("uniqueAndSecret") // в реальном проекте ключ нужно использовать секрет из конфигурации
//                        .tokenValiditySeconds(86400 * 7) // 7 дней
//                        .userDetailsService(userDetailsService())
//                        .rememberMeParameter("remember-me")
//                )
//                // настройка выхода
//                .logout(logout -> logout
//                        .logoutUrl("/users/logout")
//                        .logoutSuccessUrl("/")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID", "remember-me")
//                        .permitAll()
//                )
//                // репозиторий контекста безопасности
//                .securityContext(securityContext -> securityContext
//                        .securityContextRepository(securityContextRepository)
//                )
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/actuator/**")
//                );

        log.info("SecurityFilterChain настроен");
        return http.build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsService(userRepository);
    }


//    .requestMatchers("/actuator/health", "/actuator/info").permitAll() // только статус БД и конфигурация приложения публично
//                        .requestMatchers("/actuator/**").hasRole(UserRoles.ADMIN.name()) // остальное только для админа
}
