package ru.rutmiit.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для главной страницы.
 */
@Slf4j // добавляет логирование
@Controller // регистрирует класс как контроллер
public class HomeController {
    @GetMapping("/") // обрабатывает гетзапрос на корневой юрл
    public String homePage() {
        log.debug("Отображение главной страницы");
        return "index";
    }
}
