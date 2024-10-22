package com.example.beckendlibrarymeneger.interfaces;

import com.example.beckendlibrarymeneger.models.DVD; // импорт класса DVD
import org.springframework.data.jpa.repository.JpaRepository; // Импорт интерфейса JpaRepository
import org.springframework.stereotype.Repository; // Импорт аннотации Repository

/**
 * Интерфейс DVDRepository, который предоставляет методы для работы с
 * сущностями DVD в базе данных.
 * <p>
 * Это расширение интерфейса JpaRepository, которое предоставляет
 * базовые операции для работы с DVD, включая сохранение,
 * удаление и поиск фильмов.
 * </p>
 */
@Repository // аннотация указывает, что это интерфейс репозитория для доступа к данным
public interface DVDRepository extends JpaRepository<DVD, Integer>
{
    /**
     * Получает экземпляр класса DVD по его номеру ISBN.
     *
     * @param isbn строка, представляющая уникальный идентификатор DVD в формате ISBN
     * @return объект типа DVD, соответствующий указанному ISBN
     */
    DVD getDVDByISBN(String isbn); // получение экземпляра класса DVD по номеру isbn
}