package com.example.beckendlibrarymeneger.service;

import com.example.beckendlibrarymeneger.interfaces.ItemRepository; // Импорт интерфейса для репозитория предметов
import com.example.beckendlibrarymeneger.models.*; // импорт всех классов директории models
import lombok.AllArgsConstructor; // Импорт аннотации для создания конструктора со всеми параметрами
import org.springframework.beans.factory.annotation.Autowired; // Импорт аннотации для автоматической инъекции зависимостей
import org.springframework.stereotype.Service; // Импорт аннотации для обозначения класса как сервиса
import java.util.ArrayList; // Импорт класса ArrayList для работы с коллекциями
import java.util.List; // Импорт класса List для работы с коллекциями
import java.util.UUID; // Импорт класса UUID для генерации уникальных идентификаторов

/**
 * Сервисный класс для управления предметами в библиотеке.
 * Этот класс предоставляет методы для получения, добавления и управления предметами,
 * а также обработки исключений, связанных с предметами.
 */
@AllArgsConstructor // конструктор со всеми параметрами для инициализации полей
@Service // обозначает, что данный класс является сервисом, управляемым Spring
public class AbstractItemService
{
    //region Fields
    @Autowired // автоматическая инъекция зависимости репозитория предметов
    private final ItemRepository itemRepository; // репозиторий для работы с предметами
    //endRegion

    /**
     * Метод addItem - добавления предмета в базу данных.
     *
     * @param item предмет, который нужно добавить
     * @return возврат сохранённого объекта предмета
     */
    public LibraryItem<?> addItem(LibraryItem item)
    {
        List<Transaction> newListOfTransactions = new ArrayList<>(); // создаём новый список транзакций
        Transaction transaction = new Transaction(); // создаём новую транзакцию
        transaction.setIs_returned(true); // устанавливаем статус, что предмет возвращен

        newListOfTransactions.add(transaction); // добавляем транзакцию в список
        item.setTransactions(newListOfTransactions); // устанавливаем список транзакций для предмета
        return itemRepository.save(item); // сохраняем предмет и возвращаем его
    }

    /**
     * Метод получения всех предметов.
     *
     * @return возвращает список всех предметов
     */
    public List<LibraryItem<?>> getAllItems()
    {
        return itemRepository.findAll(); // возвращаем все предметы из репозитория
    }

    /**
     * Метод генерирует случайный номер ISBN для предмета.
     *
     * @return возвращает случайный ISBN как строку
     */
    public String generateISBN()
    {
        return UUID.randomUUID().toString(); // генерируем случайный UUID и возвращаем его в виде строки
    }

}

