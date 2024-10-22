package com.example.beckendlibrarymeneger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Класс SchedulerConfig конфигурирует расписание выполнения задач в приложении.
 *
 * Этот класс служит для включения поддержки планировщика задач с использованием аннотации @EnableScheduling.
 * Дополнительные настройки можно добавить при необходимости.
 */
@Configuration
@EnableScheduling
public class SchedulerConfig
{
    // Здесь можно добавить другие настройки, если нужно,
    // но для запуска задач по расписанию этот класс достаточно оставить пустым.
}

