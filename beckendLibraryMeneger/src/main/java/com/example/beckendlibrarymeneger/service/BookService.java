package com.example.beckendlibrarymeneger.service;

import com.example.beckendlibrarymeneger.customExcepptions.ItemNotFoundException; // импорт кастомного исключения
import com.example.beckendlibrarymeneger.interfaces.BookRepository; // импорт интерфейса для репозитория книг
import com.example.beckendlibrarymeneger.models.*; // импорт всех моделей
import lombok.AllArgsConstructor; // импорт аннотации для создания конструктора со всеми параметрами
import org.springframework.beans.factory.annotation.Autowired; // импорт аннотации для автоматической инъекции зависимостей
import org.springframework.stereotype.Service; // импорт аннотации для обозначения класса как сервиса
import java.util.UUID; // импорт класса UUID для генерации уникальных идентификаторов


/**
 * Сервисный класс для управления книгами в библиотеке.
 *
 * Этот класс предоставляет методы работы книгами,
 * а также обработки исключений, связанных с книгами.
 *
 */
@AllArgsConstructor // конструктор со всеми параметрами для инициализации полей
@Service // обозначает, что данный класс является сервисом, управляемым Spring
public class BookService
{
    //region Fields
    @Autowired // автоматическая инъекция зависимости репозитория книг
    private final BookRepository bookRepository; // репозиторий для работы с книгами
    //endRegion

    /**
     * Метод generateISBN - генерирует случайный номер UUID для книги.
     *
     * @return возвращает случайный UUID как строку
     */
    public String generateISBN()
    {
        return UUID.randomUUID().toString(); // генерируем случайный UUID и возвращаем его в виде строки
    }


    /**
     * Метод getBookByISBS - получения предмета по его номеру (ISBN).
     *
     * @param isbn номер предмета (ISBN), который передаётся в качестве параметра
     * @return возврат объекта Book (возвращаемая книга)
     * @throws ItemNotFoundException если предмет книга с указанным ISBN не найдена
     * */
    public LibraryItem<?> getBookByISBS(String isbn)
    {
        try
        {
          Book book =  bookRepository.getBookByISBN(isbn); // получаем книгу из базы данных по её isbn
          if (book != null) // если книга найдена, то:
          {
              return book; // возвращаем книгу
          }
        }
        catch (ItemNotFoundException ex) // обрабатываем исключение об ошибке ItemNotFoundException
        {
            throw ex; // позволяем обработать исключение глобально
        }
        throw new ItemNotFoundException("Книга с указанным ISBN не найдена: " + isbn); // выбрасываем исключение, если книга не найдена
    }

}




