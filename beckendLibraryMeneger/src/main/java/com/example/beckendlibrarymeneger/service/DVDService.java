package com.example.beckendlibrarymeneger.service;

import com.example.beckendlibrarymeneger.customExcepptions.ItemNotFoundException;
import com.example.beckendlibrarymeneger.interfaces.DVDRepository;
import com.example.beckendlibrarymeneger.models.DVD;
import com.example.beckendlibrarymeneger.models.LibraryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервисный класс DVDService предоставляет методы для работы с объектами DVD.
 * Он обеспечивает получение информации о фильмах DVD из базы данных по их номеру ISBN.
 * Использует DVDRepository для взаимодействия с базой данных.
 * Обрабатывает исключения, если DVD с указанным ISBN не найден.
 */
@Service
public class DVDService
{
    //region Fields
    @Autowired
    private DVDRepository dvdRepository; // репозиторий для работы с DVD
    //endRegion

    /**
     * Метод получения фильма DVD по его номеру (ISBN).
     *
     * @param isbn номер фильма DVD (ISBN), который передаётся в качестве параметра
     * @return возврат объекта фильма DVD (возвращаемый фильм)
     * @throws ItemNotFoundException если предмет фильм DVD с указанным ISBN не найден
     * */
    public LibraryItem<?> getDVDByISBN(String isbn)
    {
        try
        {
            DVD dvd =  dvdRepository.getDVDByISBN(isbn); // получаем DVD из базы данных по isbn
            if (dvd != null) // если DVD найдено, то:
            {
                return dvd; // возвращаем dvd
            }
        }
        catch (ItemNotFoundException ex)
        {
            throw ex; // позволяем обработать исключение ItemNotFoundException глобально
        }
        throw new ItemNotFoundException("Фильм с указанным ISBN не найден: " + isbn); // выбрасываем исключение, если книга не найдена
    }
}

