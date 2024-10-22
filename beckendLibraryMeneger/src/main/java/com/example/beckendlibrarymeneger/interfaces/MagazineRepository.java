package com.example.beckendlibrarymeneger.interfaces;

import com.example.beckendlibrarymeneger.models.Magazine;
import org.springframework.data.jpa.repository.JpaRepository; // Импорт интерфейса JpaRepository
import org.springframework.stereotype.Repository; // Импорт аннотации Repository

/**
 * Интерфейс MagazineRepository, который предоставляет методы для работы с
 * сущностями Magazine в базе данных.
 * <p>
 * Это расширение интерфейса JpaRepository, которое предоставляет
 * базовые операции для работы с Magazine, включая сохранение,
 * удаление и поиск журналов.
 * </p>
 */
@Repository // аннотация указывает, что это интерфейс репозитория для доступа к данным
public interface MagazineRepository extends JpaRepository<Magazine, Integer>
{
    /**
     * Метод getMagazineByISBN - Получает экземпляр класса Magazine по его номеру ISBN.
     *
     * @param isbn строка, представляющая уникальный идентификатор журнала
     * @return экземпляр класса Magazine, соответствующий указанному ISBN
     */
    Magazine getMagazineByISBN(String isbn); // получение экземпляра класса Magazine по номеру ISBN
}