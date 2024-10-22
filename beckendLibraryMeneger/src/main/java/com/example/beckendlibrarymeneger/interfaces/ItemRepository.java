package com.example.beckendlibrarymeneger.interfaces;

import com.example.beckendlibrarymeneger.models.AbstractItem;
import com.example.beckendlibrarymeneger.models.LibraryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с библиотечными элементами в базе данных.
 *
 * Этот интерфейс предоставляет методы для выполнения операций с элементами библиотеки,
 * такими как поиск по уникальному идентификатору ISBN.
 */
@Repository
public interface ItemRepository extends JpaRepository<LibraryItem<?>, Integer>
{
    /**
     * Метод getItemBy - Получает библиотечный элемент по его номеру ISBN.
     *
     * @param isbn строка, представляющая уникальный идентификатор элемента библиотеки
     * @return экземпляр класса AbstractItem, соответствующий указанному ISBN
     */
    @Query("SELECT i FROM LibraryItem i WHERE i.ISBN = :isbn") // запрос для выборки элемента библиотеки по ISBN
    AbstractItem<?> getItemBy(@Param("isbn") String isbn); // получение экземпляра класса AbstractItem по номеру ISBN
}

