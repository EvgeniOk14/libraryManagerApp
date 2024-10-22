package com.example.beckendlibrarymeneger.interfaces;

import com.example.beckendlibrarymeneger.models.LibraryItem; // Импорт класса AbstractItem
import org.springframework.data.jpa.repository.JpaRepository; // Импорт интерфейса JpaRepository
import org.springframework.stereotype.Repository; // Импорт аннотации Repository

/**
 * Интерфейс AbstractRepository, который предоставляет методы для работы с
 * сущностями AbstractItem в базе данных.
 */
@Repository // Аннотация указывает, что это интерфейс репозитория для доступа к данным
public interface AbstractRepository extends JpaRepository<LibraryItem<?>, Integer>
{

}

