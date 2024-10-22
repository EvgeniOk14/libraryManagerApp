package com.example.beckendlibrarymeneger.service;

import com.example.beckendlibrarymeneger.interfaces.TransactionRepository; // Импорт интерфейса для работы с транзакциями
import com.example.beckendlibrarymeneger.interfaces.UserRepository; // Импорт интерфейса для работы с пользователями
import com.example.beckendlibrarymeneger.models.Transaction; // Импорт модели транзакции
import org.springframework.stereotype.Service; // Импорт аннотации для определения сервиса в Spring
import java.util.Date; // Импорт класса для работы с датами
import java.util.List; // Импорт класса List для работы со списками

/**
 * Класс TransactionService отвечает за бизнес-логику транзакций в системе.
 */
@Service // Обозначает класс как сервисный компонент в Spring
public class TransactionService
{
    //region Fields
    private final TransactionRepository transactionRepository; // Репозиторий для работы с транзакциями
    private final UserRepository userRepository; // Репозиторий для работы с пользователями
    //endRegion

    //region Constructors
    /**
     * Конструктор для инициализации TransactionService с заданными репозиториями.
     *
     * @param transactionRepository репозиторий для транзакций
     * @param userRepository репозиторий для пользователей
     */
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository)
    {
        this.transactionRepository = transactionRepository; // Инициализация репозитория транзакций
        this.userRepository = userRepository; // Инициализация репозитория пользователей
    }
    //endregion

    /**
     * Метод addTransaction - для добавления новой транзакции в систему.
     *
     * @param newTransaction новая транзакция, которую нужно добавить
     * @return добавленная транзакция
     */
    public Transaction addTransaction(Transaction newTransaction)
    {
        return transactionRepository.save(newTransaction); // сохраняем новую транзакцию в репозитории
    }

    /**
     * Метод saveTransaction - для сохранения существующей транзакции.
     *
     * @param newTransaction транзакция, которую нужно сохранить
     * @return сохраненная транзакция
     */
    public Transaction saveTransaction(Transaction newTransaction)
    {
        return transactionRepository.save(newTransaction); // сохраняем транзакцию в репозитории
    }

    /**
     * Метод getAllTransactions - для получения всех транзакций в системе.
     *
     * @return список всех транзакций
     */
    public List<Transaction> getAllTransactions()
    {
        return transactionRepository.findAll(); // Получаем все транзакции из репозитория
    }

    /**
     * Метод findTransactionByItemIsbn -  для поиска транзакции по ISBN предмета.
     *
     * @param isbn ISBN предмета
     * @return найденная транзакция или null, если не найдена
     */
    public Transaction findTransactionByItemIsbn(String isbn)
    {
        System.out.println("находимся в findTransactionByItemIsbn(String isbn) TransactionService");
        return transactionRepository.findTransactionByItemISBN(isbn); // ищем транзакцию по ISBN предмета
    }

    /**
     * Метод для получения всех транзакций с просроченным возвратом.
     *
     * @return список транзакций с просроченным возвратом
     */
    public List<Transaction> getOverdueTransactions()
    {
        Date currentDate = new Date(); // получаем текущую дату
        return transactionRepository.findOverdueTransactions(currentDate); // возвращаем транзакции, у которых срок возврата просрочен
    }
}