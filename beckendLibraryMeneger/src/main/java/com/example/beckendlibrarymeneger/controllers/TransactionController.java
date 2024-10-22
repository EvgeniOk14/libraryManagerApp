package com.example.beckendlibrarymeneger.controllers;

import com.example.beckendlibrarymeneger.models.Transaction; // импорт Transaction
import com.example.beckendlibrarymeneger.service.TransactionService; // импорт TransactionService
import org.springframework.http.ResponseEntity; // Импорт класса для формирования HTTP-ответов
import org.springframework.web.bind.annotation.*; // Импорт аннотаций для работы с контроллерами
import java.util.List; // Импорт списка для работы с коллекцией транзакций


/**
 * Контроллер для работы с транзакциями в библиотечной системе.
 * Обрабатывает HTTP-запросы для добавления и получения транзакций.
 */
@RestController // Аннотация указывает, что класс является контроллером REST
@RequestMapping("/library/transactions") // Определяет базовый путь для всех методов в этом контроллере
@CrossOrigin(origins = "http://localhost:5173") // Разрешает CORS для указанного источника
public class TransactionController
{
    //region Fields
    private final TransactionService transactionService; // Сервис для обработки транзакций
    //endRegion

    //region Constructor
    /**
     * Конструктор контроллера, который использует TransactionService для работы с транзакциями.
     *
     * @param transactionService сервис для обработки операций с транзакциями.
     */
    public TransactionController(TransactionService transactionService)
    {
        this.transactionService = transactionService; // Инициализация сервиса
    }
    //endRegion


    /**
     * Добавляет новую транзакцию в систему.
     * Обрабатывает HTTP POST запросы по пути "/library/transactions/add".
     *
     * @param newTransaction объект новой транзакции, переданный в теле запроса.
     * @return ResponseEntity с добавленной транзакцией и статусом 200 OK.
     */
    @CrossOrigin(origins = "http://localhost:5173") // Разрешает CORS для данного метода
    @PostMapping("/add") // Аннотация для обработки POST-запроса по указанному пути
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction newTransaction)
    {
        Transaction transaction = transactionService.addTransaction(newTransaction); // Добавление транзакции через сервис
        return ResponseEntity.ok(transaction); // Возврат успешного ответа с добавленной транзакцией
    }


    /**
     * Возвращает список всех транзакций в системе.
     * Обрабатывает HTTP GET запросы по пути "/library/transactions/all".
     *
     * @return ResponseEntity с списком всех транзакций и статусом 200 OK.
     */
    @CrossOrigin(origins = "http://localhost:5173") // Разрешает CORS для данного метода
    @GetMapping("/all") // Аннотация для обработки GET-запроса по указанному пути
    public ResponseEntity<List<Transaction>> getAllTransactions()
    {
        List<Transaction> transactions = transactionService.getAllTransactions(); // Получение всех транзакций через сервис
        return ResponseEntity.ok(transactions); // Возврат успешного ответа со списком транзакций
    }
}
