package com.example.beckendlibrarymeneger.service;

import com.example.beckendlibrarymeneger.models.Transaction; // Импорт модели транзакции
import com.example.beckendlibrarymeneger.models.User; // Импорт модели пользователя
import org.springframework.scheduling.annotation.Scheduled; // Импорт аннотации для планирования задач
import org.springframework.stereotype.Component; // Импорт аннотации для определения компонента Spring
import java.util.List; // Импорт класса List для работы со списками

/**
 * Класс OverdueNotificationScheduler отвечает за планирование задач по проверке просроченных транзакций
 * и отправке уведомлений пользователям, которые не вернули вещи в положенный срок.
 */
@Component
public class OverdueNotificationScheduler
{
    //region Fields
    private final TransactionService transactionService;  // сервис для работы с транзакциями
    private final EmailService emailService;  // сервис для отправки электронных писем
    //endRegion

    //region Constructor
    /**
     * Конструктор, инициализирующий зависимости для работы с транзакциями и отправкой писем.
     *
     * @param transactionService Сервис для получения и работы с транзакциями.
     * @param emailService Сервис для отправки уведомлений по электронной почте.
     */
    public OverdueNotificationScheduler(TransactionService transactionService, EmailService emailService)
    {
        this.transactionService = transactionService;  // инициализация сервиса транзакций
        this.emailService = emailService;  // инициализация сервиса email
    }
    //endRegion


    /**
     * Метод для проверки просроченных транзакций и отправки уведомлений пользователям.
     * Планируется на ежедневное выполнение в полночь с помощью аннотации @Scheduled.
     * Можно настроить для запуска каждую минуту, раскомментировав другую аннотацию @Scheduled.
     *
     * @Scheduled(cron = "0 0 0 * * ?") - задача запускается ежедневно в полночь
     * @Scheduled(cron = "0 * * * * ?") - задача запускается каждую минуту (если раскомментировать)
     */
    @Scheduled(cron = "0 0 0 * * ?")  // планирование задачи на полночь каждый день
    // @Scheduled(cron = "0 * * * * ?")  // планирование задачи каждую минуту (альтернативный вариант)
    public void checkForOverdueTransactions()
    {
        System.out.println("Задача по проверке просроченных транзакций запущена.");  // логирование начала задачи

        // получение всех просроченных транзакций из сервиса
        List<Transaction> overdueTransactions = transactionService.getOverdueTransactions();

        // проход по каждой просроченной транзакции и отправка уведомления пользователю
        for (Transaction transaction : overdueTransactions)
        {
            User user = transaction.getUser();  // получение пользователя, связанного с транзакцией
            String message = "Уважаемый " + user.getName() + ", вы просрочили возврат вещи: " +
                    transaction.getItem().getISBN() + ". Пожалуйста, верните вещь.";  // создание сообщения для пользователя

            // отправка электронного письма пользователю с уведомлением о просрочке
            emailService.sendEmail(user.getContactInfo(), "Напоминание о возврате вещи", message);

            // логирование отправки уведомления
            System.out.println("Уведомление отправлено пользователю: " + user.getContactInfo());
        }
    }
}
