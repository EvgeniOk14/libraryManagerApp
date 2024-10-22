package com.example.beckendlibrarymeneger.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping; // Импорт аннотации для обработки сообщений от WebSocket клиентов
import org.springframework.messaging.handler.annotation.SendTo; // Импорт аннотации для отправки сообщений подписчикам
import org.springframework.messaging.simp.SimpMessagingTemplate; // Импорт класса для работы с WebSocket
import org.springframework.stereotype.Controller; // Импорт аннотации, указывающей, что класс является контроллером
import org.springframework.web.bind.annotation.PostMapping; // Импорт аннотации для обработки HTTP POST запросов
import org.springframework.web.bind.annotation.RequestBody; // Импорт аннотации для получения тела запроса

/**
 * Контроллер для отправки уведомлений через WebSocket и обработки сообщений от клиентов.
 * Отправляет уведомления подписчикам по теме "/topic/notifications".
 */
@Controller // указывает, что этот класс является контроллером Spring
public class NotificationController
{
    //region Fields
    private final SimpMessagingTemplate messagingTemplate; // объект для отправки сообщений через WebSocket
    //endRegion

    //region Constructor
    /**
     * Конструктор, использующий DI (Dependency Injection) для внедрения SimpMessagingTemplate.
     *
     * @param messagingTemplate объект, используемый для отправки сообщений через WebSocket.
     */
    public NotificationController(SimpMessagingTemplate messagingTemplate) // конструктор класса
    {
        this.messagingTemplate = messagingTemplate; // инициализация поля messagingTemplate
    }
    //endRegion

    /**
     * Метод sendNotification - для отправки уведомления всем подписчикам WebSocket.
     * Обрабатывает HTTP POST запросы по пути "/notify".
     *
     * @param message Сообщение, полученное в теле запроса, которое будет отправлено подписчикам.
     */
    @PostMapping("/notify") // обрабатывает HTTP POST запросы по указанному пути
    public void sendNotification(@RequestBody String message) // метод для отправки уведомления
    {
        // отправка сообщения на тему "/topic/notifications"
        messagingTemplate.convertAndSend("/topic/notifications", message); // отправляет сообщение подписчикам
    }

    /**
     * Метод notify - Обрабатывает сообщения от WebSocket клиентов, поступающие по пути "/app/notify".
     * Сообщения рассылаются всем подписчикам темы "/topic/notifications".
     *
     * @param message Сообщение от клиента.
     * @return Уведомление с добавленным префиксом "New Notification: ".
     */
    @MessageMapping("/notify") // указывает путь для обработки сообщений от WebSocket клиентов
    @SendTo("/topic/notifications") // указывает, куда отправлять ответы
    public String notify(String message) // метод для обработки уведомлений
    {
        return "New Notification: " + message; // возвращает уведомление с префиксом
    }
}
