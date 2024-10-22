package com.example.beckendlibrarymeneger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;

/**
 * Класс WebSocketConfig конфигурирует параметры для работы с WebSocket и настройками CORS в приложении.
 *
 * Этот класс включает поддержку WebSocket с использованием STOMP-протокола и настраивает CORS-фильтр для разрешения запросов с других доменов.
 */
@Configuration // указывает что класс является конфигурационным
@EnableWebSocketMessageBroker // Включает поддержку WebSocket-сообщений с использованием брокера сообщений
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer
{
    /**
     * Метод configureMessageBroker -  Настраивает брокер сообщений для обработки входящих и исходящих сообщений.
     *
     * @param config объект MessageBrokerRegistry для настройки брокера сообщений.
     */
    @Override // указывает на переопределение метода
    public void configureMessageBroker(MessageBrokerRegistry config)
    {
        config.enableSimpleBroker("/topic"); // Включает простой брокер сообщений для отправки клиентам на /topic
        config.setApplicationDestinationPrefixes("/app"); // Устанавливает префикс для обработки сообщений от клиентов
    }

    /**
     * Метод registerStompEndpoints - Регистрирует STOMP-endpoint для подключения клиентов.
     *
     * @param registry объект StompEndpointRegistry для регистрации точки подключения.
     */
    @Override // указывает на переопределение метода
    public void registerStompEndpoints(StompEndpointRegistry registry)
    {
        registry.addEndpoint("/ws")  // Регистрирует точку подключения WebSocket по адресу /ws
                .setAllowedOrigins("*")  // Разрешает подключения с любых источников
                .withSockJS();  // Включает поддержку SockJS
    }

    /**
     * Метод corsFilter - Настраивает CORS-фильтр для разрешения запросов с любых доменов.
     *
     * @return объект CorsFilter, который настраивает правила CORS для приложения.
     */
    @Bean // Указывает, что этот метод создает и возвращает объект, который будет управляться контейнером Spring.
    public CorsFilter corsFilter()
    {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // Создаем источник конфигурации CORS, который будет использоваться для определения правил
        CorsConfiguration config = new CorsConfiguration(); // Создаем новую конфигурацию CORS
        config.setAllowCredentials(true);  // Разрешает отправку учетных данных в CORS-запросах
        config.addAllowedOriginPattern("*");  // Разрешает запросы с любых источников
        config.addAllowedHeader("*");  // Разрешает любые заголовки
        config.addAllowedMethod("*");  // Разрешает любые HTTP методы
        source.registerCorsConfiguration("/**", config);  // Применяет конфигурацию для всех маршрутов
        return new CorsFilter(source); // Возвращает новый CORS-фильтр с заданными конфигурациями
    }
}
