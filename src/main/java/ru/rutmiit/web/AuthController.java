//package ru.rutmiit.web;
//
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import ru.rutmiit.dto.ShowOrdersInfoDto;
//import ru.rutmiit.dto.UserRegistrationDto;
//import ru.rutmiit.models.entities.Order;
//import ru.rutmiit.models.entities.User;
//import ru.rutmiit.services.AuthService;
//import ru.rutmiit.services.OrderService;
//import ru.rutmiit.views.UserProfileView;
//
//import java.security.Principal;
//import java.util.List;
//
//@Slf4j
//@Controller
//@RequestMapping("/users")
//public class AuthController {
//    private final AuthService authService;
//    private final OrderService orderService;
//
//    public AuthController(AuthService authService, OrderService orderService) {
//        this.authService = authService;
//        this.orderService = orderService;
//        log.info("AuthController инициализирован");
//    }
//
//    @ModelAttribute("userRegistrationDto")
//    public UserRegistrationDto initForm() {
//        return new UserRegistrationDto();
//    }
//
//    @GetMapping("/register")
//    public String register() {
//        log.debug("Отображение страницы регистрации");
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String doRegister(@Valid UserRegistrationDto userRegistrationDto,
//                             BindingResult bindingResult,
//                             RedirectAttributes redirectAttributes) {
//        log.debug("Обработка регистрации пользователя: {}", userRegistrationDto.getUsername());
//
//        if (bindingResult.hasErrors()) {
//            log.warn("Ошибки валидации при регистрации: {}", bindingResult.getAllErrors());
//            redirectAttributes.addFlashAttribute("userRegistrationDto", userRegistrationDto);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDto", bindingResult);
//
//            return "redirect:/users/register";
//        }
//
//        this.authService.register(userRegistrationDto);
//        log.info("Пользователь успешно зарегистрирован: {}", userRegistrationDto.getUsername());
//
//        return "redirect:/users/login";
//    }
//
//    @GetMapping("/login")
//    public String login() {
//        log.debug("Отображение страницы входа");
//        return "login";
//    }
//
//    @PostMapping("/login-error")
//    public String onFailedLogin(
//            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
//            RedirectAttributes redirectAttributes) {
//
//        log.warn("Неудачная попытка входа для пользователя: {}", username);
//        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
//        redirectAttributes.addFlashAttribute("badCredentials", true);
//
//        return "redirect:/users/login";
//    }
//
//    @GetMapping("/profile")
//    // Principal principal - объект Spring Security с информацией о текущем залогиненом пользователе
//    public String profile(Principal principal, Model model) {
//        String username = principal.getName();
//        log.debug("Отображение профиля пользователя: {}", username);
//
//        User user = authService.getUser(username);
//
//        List<ShowOrdersInfoDto> orders = orderService.getOrdersByUsername(username);
//
//        UserProfileView userProfileView = new UserProfileView(
//                username,
//                user.getEmail(),
//                user.getShippingAddress(),
//                orders
//        );
//
//        model.addAttribute("user", userProfileView);
//        model.addAttribute("orders", orders);
//
//        return "profile";
//    }
//}
