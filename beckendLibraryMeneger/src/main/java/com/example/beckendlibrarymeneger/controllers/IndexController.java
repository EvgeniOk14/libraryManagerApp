package com.example.beckendlibrarymeneger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Класс IndexController обрабатывает HTTP-запросы к корневому маршруту и возвращает соответствующее представление.
 * Создан для проверки работы приложения при первом запуске (проверяет отображение представления index.html по адресу http://localhost:8080)
 */
@Controller
public class IndexController
{
    /**
     * Обрабатывает GET-запросы на корневой маршрут "/" и возвращает имя представления.
     *
     * @return строка "index", указывающая на представление index.html.
     */
    @GetMapping("/")
    public String index()
    {
        return "index";  // Возвращает представление index.html при обращении к корневому маршруту
    }
}
