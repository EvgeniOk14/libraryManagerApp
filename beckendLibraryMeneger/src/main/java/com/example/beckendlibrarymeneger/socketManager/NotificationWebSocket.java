package com.example.beckendlibrarymeneger.socketManager;

import jakarta.websocket.*; // импорт библиотек для работы с веб-сокетами
import jakarta.websocket.server.ServerEndpoint; // импорт аннотации для определения конечной точки веб-сокета

/**
 * Класс NotificationWebSocket обрабатывает веб-сокет соединения для уведомлений.
 */
@ServerEndpoint("/notifications") // определяем конечную точку для веб-сокета
public class NotificationWebSocket
{
    private static Session session; // храним последнюю активную сессию

    /**
     * Метод вызывается, когда устанавливается соединение с клиентом.
     *
     * @param session сессия веб-сокета, представляющая соединение с клиентом
     */
    @OnOpen
    public void onOpen(Session session)
    {
        NotificationWebSocket.session = session; // сохраняем сессию
        System.out.println("Соединение открыто: " + session.getId()); // печатаем ID открытой сессии
        session.getAsyncRemote().sendText("Соединение установлено. ID: " + session.getId()); // отправляем приветственное сообщение клиенту
    }

    /**
     * Метод вызывается при получении сообщения от клиента.
     *
     * @param message сообщение, отправленное клиентом
     * @param session сессия веб-сокета, представляющая соединение с клиентом
     */
    @OnMessage
    public void onMessage(String message, Session session)
    {
        System.out.println("Получено сообщение от клиента: " + message); // печатаем полученное сообщение
        session.getAsyncRemote().sendText("Сообщение получено: " + message); // отправляем ответ клиенту
    }

    /**
     * Метод вызывается при возникновении ошибки в сессии.
     *
     * @param session сессия веб-сокета, в которой произошла ошибка
     * @param throwable объект, представляющий возникшую ошибку
     */
    @OnError
    public void onError(Session session, Throwable throwable)
    {
        System.err.println("Ошибка в сессии " + session.getId() + ": " + throwable.getMessage()); // печатаем сообщение об ошибке
        session.getAsyncRemote().sendText("Ошибка: " + throwable.getMessage()); // уведомляем клиента об ошибке
    }

    /**
     * Метод вызывается, когда соединение закрывается.
     *
     * @param session сессия веб-сокета, которая закрывается
     * @param reason причина закрытия соединения
     */
    @OnClose
    public void onClose(Session session, CloseReason reason)
    {
        System.out.println("Соединение закрыто: " + session.getId() + " Причина: " + reason); // печатаем ID закрытой сессии и причину
    }

    // Статический метод для отправки сообщений из других классов
    public static void sendNotification(String message)
    {
        if (session != null && session.isOpen())
        {
            session.getAsyncRemote().sendText(message); // отправляем сообщение клиенту
        }
    }
}
