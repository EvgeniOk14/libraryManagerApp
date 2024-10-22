package com.example.beckendlibrarymeneger.service;

import jakarta.mail.*; // Импорт класса для работы с электронной почтой
import jakarta.mail.internet.InternetAddress; // Импорт класса для представления адреса электронной почты
import jakarta.mail.internet.MimeMessage; // Импорт класса для создания MIME-сообщений
import org.springframework.stereotype.Service; // Импорт аннотации для обозначения класса как сервиса
import java.util.Properties; // Импорт класса для работы с наборами свойств

/**
 * Класс EmailService отвечает за отправку электронных писем с использованием протокола SMTP.
 */
@Service // обозначает, что данный класс является сервисом, управляемым Spring
public class EmailService
{
    /**
     * Метод для отправки email с указанным получателем, темой и телом письма.
     *
     * @param to      Адрес электронной почты получателя.
     * @param subject Тема письма.
     * @param body    Содержимое (текст) письма.
     */
    public void sendEmail(String to, String subject, String body)
    {
        // настройка свойств для подключения к SMTP серверу:

        Properties properties = new Properties(); // создание нового объекта Properties для хранения конфигураций

        // установка SMTP сервера (mail.ru) и порта для SSL и TLS:
        properties.put("mail.smtp.host", "smtp.mail.ru");  // адрес SMTP сервера
        properties.put("mail.smtp.port", "587");  // порт для TLS
        properties.put("mail.smtp.socketFactory.port", "465");  // порт для SSL
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  // класс для работы с SSL

        // включение поддержки TLS и SSL
        properties.put("mail.smtp.starttls.enable", "true");  // включить TLS
        properties.put("mail.smtp.ssl.enable", "true");  // включить SSL

        // Учетные данные для аутентификации:
        String username = "noreply.notify.message@mail.ru";  // адрес электронной почты отправителя
        String password = "ffSTg5sU82vXZe8tpsvT";  // пароль приложения для отправки писем

        // Создание сессии с аутентификацией:
        Session session = Session.getInstance(properties, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(username, password);  // аутентификация отправителя
            }
        });

        try
        {
            // создание email сообщения:
            Message message = new MimeMessage(session);  // создание нового сообщения
            message.setFrom(new InternetAddress(username));  // указание отправителя
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));  // указание получателя
            message.setSubject(subject);  // установка темы письма
            message.setText(body);  // установка тела письма

            // отправка email сообщения через SMTP сервер
            Transport.send(message);  // отправка сообщения

            System.out.println("Письмо успешно отправлено.");  // логирование успешной отправки
        }
        catch (MessagingException e)
        {
            e.printStackTrace();  // вывод стека исключений в случае ошибки
            System.out.println("Ошибка при отправке письма: " + e.getMessage());  // логирование ошибки
        }
    }
}
