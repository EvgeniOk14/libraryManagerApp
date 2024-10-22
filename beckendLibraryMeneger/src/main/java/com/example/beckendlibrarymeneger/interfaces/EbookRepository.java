package com.example.beckendlibrarymeneger.interfaces;

import com.example.beckendlibrarymeneger.models.Ebook; // импорт класса Ebook
import org.springframework.data.jpa.repository.JpaRepository; // Импорт интерфейса JpaRepository
import org.springframework.stereotype.Repository; // Импорт аннотации Repository

/**
 * Интерфейс EbookRepository, который предоставляет методы для работы с
 * сущностями Ebook в базе данных.
 * <p>
 * Это расширение интерфейса JpaRepository, которое предоставляет
 * базовые операции для работы с Ebook, включая сохранение,
 * удаление и поиск электронных книг.
 * </p>
 */
@Repository // аннотация указывает, что это интерфейс репозитория для доступа к данным
public interface EbookRepository extends JpaRepository<Ebook, Integer>
{
    /**
     * Метод getEbookByISBN - Получает электронную книгу по её ISBN.
     *
     * @param isbn строка, представляющая уникальный идентификатор книги в формате ISBN
     * @return объект типа Ebook, соответствующий указанному ISBN
     */
    Ebook getEbookByISBN(String isbn); // получение экземпляра класса Ebook по номеру isbn
}

