package com.example.beckendlibrarymeneger.service;

import com.example.beckendlibrarymeneger.customExcepptions.ItemNotFoundException;
import com.example.beckendlibrarymeneger.interfaces.EbookRepository;
import com.example.beckendlibrarymeneger.models.Ebook;
import com.example.beckendlibrarymeneger.models.LibraryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервисный класс EbookService предоставляет методы для работы с объектами Ebook.
 * Он обеспечивает получение информации об электронных книгах из базы данных по их номеру ISBN.
 * Использует EbookRepository для взаимодействия с базой данных.
 * Обрабатывает исключения, если электронная книга с указанным ISBN не найдена.
 */
@Service
public class EbookService
{
    @Autowired
    private EbookRepository ebookRepository;
    /**
     * Метод получения электронной книги по её номеру (ISBN).
     *
     * @param isbn номер электронной книги (ISBN), который передаётся в качестве параметра
     * @return возврат объекта Ebook (возвращаемая электронная книга)
     * @throws ItemNotFoundException если предмет электронная книга с указанным ISBN не найдена
     * */
    public LibraryItem<?> getEbookByISBS(String isbn)
    {
        try
        {
            Ebook ebook =  ebookRepository.getEbookByISBN(isbn); // получаем Ebook  из базы данных
            if (ebook != null) // если он найден, то:
            {
                return ebook; //  возращаем ebook
            }
        }
        catch (ItemNotFoundException ex)
        {
            throw ex; // позволяем обработать исключение ItemNotFoundException глобально
        }
        throw new ItemNotFoundException("Электронная книга с указанным ISBN не найдена: " + isbn); // выбрасываем исключение, если книга не найдена
    }
}



