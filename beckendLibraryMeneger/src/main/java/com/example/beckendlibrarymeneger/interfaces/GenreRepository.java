package com.example.beckendlibrarymeneger.interfaces;

import com.example.beckendlibrarymeneger.models.Genre; // Импорт класса Genre из пакета models
import org.springframework.data.jpa.repository.JpaRepository; // Импорт интерфейса JpaRepository из Spring Data JPA
import java.util.Optional; // Импорт класса Optional из пакета java.util

/**
 * Интерфейс репозитория для работы с сущностью Genre.
 *
 * Этот интерфейс наследует от JpaRepository, предоставляя стандартные операции
 * для работы с базой данных, такие как сохранение, обновление, удаление и поиск.
 */
public interface GenreRepository extends JpaRepository<Genre, Integer>
{

    /**
     * Метод findByName - Находит жанр по его имени.
     *
     * @param name имя жанра, по которому осуществляется поиск
     * @return объект Optional, содержащий найденный жанр, или пустой объект, если жанр не найден
     */
    Optional<Genre> findByName(String name); // Метод для поиска жанра по имени
}
