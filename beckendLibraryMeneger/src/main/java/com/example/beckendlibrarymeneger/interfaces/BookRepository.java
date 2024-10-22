package com.example.beckendlibrarymeneger.interfaces;

import com.example.beckendlibrarymeneger.models.Book; // Импорт класса Book
import org.springframework.data.jpa.repository.JpaRepository; // Импорт интерфейса JpaRepository
import org.springframework.stereotype.Repository; // Импорт аннотации Repository

/**
 * Интерфейс BookRepository, который предоставляет методы для работы с
 * сущностями Book в базе данных.
 * <p>
 * Это расширение интерфейса JpaRepository, которое предоставляет
 * базовые операции для работы с Book, включая сохранение,
 * удаление и поиск книг.
 * </p>
 */
@Repository // аннотация указывает, что это интерфейс репозитория для доступа к данным
public interface BookRepository extends JpaRepository<Book, Integer>
{
    /**
     * Получает экземпляр класса Book по его номеру ISBN.
     *
     * @param isbn строка, представляющая уникальный идентификатор книги в формате ISBN
     * @return объект типа Book, соответствующий указанному ISBN
     */
    Book getBookByISBN(String isbn); // получение экземпляра класса Book по номеру ISBN
}

