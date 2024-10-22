package com.example.beckendlibrarymeneger.interfaces;

import com.example.beckendlibrarymeneger.models.Transaction; // Импорт класса Transaction
import org.springframework.data.jpa.repository.JpaRepository; // Импорт интерфейса JpaRepository
import org.springframework.data.jpa.repository.Query; // Импорт аннотации Query
import org.springframework.data.repository.query.Param; // Импорт аннотации Param
import org.springframework.stereotype.Repository; // Импорт аннотации Repository
import java.util.Date; // Импорт класса Date
import java.util.List; // Импорт класса List

/**
 * Интерфейс TransactionRepository, который предоставляет методы для работы с
 * сущностями Transaction в базе данных.
 * <p>
 * Это расширение интерфейса JpaRepository, которое предоставляет
 * базовые операции для работы с Transaction, включая сохранение,
 * удаление и поиск транзакций.
 * </p>
 */
@Repository // аннотация указывает, что это интерфейс репозитория для доступа к данным
public interface TransactionRepository extends JpaRepository<Transaction, Integer>
{
    /**
     * Метод findTransactionByItemISBN - Находит транзакцию по ISBN предмета.
     *
     * @param isbn ISBN предмета, по которому необходимо найти транзакцию.
     * @return Транзакция, связанная с указанным ISBN предмета, или null, если не найдена.
     */
    @Query("SELECT t FROM Transaction t JOIN t.item l WHERE l.ISBN = :isbn") // SQL запрос к базе данных
    Transaction findTransactionByItemISBN(@Param("isbn") String isbn); // метод для поиска транзакций по isbn



    /**
     * Метод findOverdueTransactions - Находит все транзакции с просроченным сроком возврата.
     *
     * @param currentDate Текущая дата для сравнения с датой возврата.
     * @return Список транзакций, у которых истек срок возврата и книги не возвращены.
     */
    @Query("SELECT t FROM Transaction t WHERE t.dueDate < :currentDate AND t.is_returned = false") // SQL запрос к базе данных
    List<Transaction> findOverdueTransactions(@Param("currentDate") Date currentDate); // метод для поиска просроченных транзакций
}
