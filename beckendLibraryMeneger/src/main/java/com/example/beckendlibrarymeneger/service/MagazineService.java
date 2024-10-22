package com.example.beckendlibrarymeneger.service;

import com.example.beckendlibrarymeneger.customExcepptions.ItemNotFoundException;
import com.example.beckendlibrarymeneger.interfaces.MagazineRepository;
import com.example.beckendlibrarymeneger.models.LibraryItem;
import com.example.beckendlibrarymeneger.models.Magazine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервисный класс MagazineService предоставляет методы для работы с объектами Magazine.
 * Он обеспечивает получение информации о журналах из базы данных по их номеру ISBN.
 * Использует MagazineRepository для взаимодействия с базой данных.
 * Обрабатывает исключения, если журнал с указанным ISBN не найден.
 */
@Service
public class MagazineService
{
    @Autowired
    private MagazineRepository magazineRepository;
    /**
     * Метод получения журнала Magazine по его номеру (ISBN).
     *
     * @param isbn номер журнала Magazine (ISBN), который передаётся в качестве параметра
     * @return возврат объекта журнал (Magazine) (возвращаемый журнал)
     * @throws ItemNotFoundException если предмет журнал (Magazine) с указанным ISBN не найден
     * */
    public LibraryItem<?> getMagazineByISBS(String isbn)
    {
        try
        {
            Magazine magazine =  magazineRepository.getMagazineByISBN(isbn); // получени magazine из базы данных по isbn
            if (magazine != null) // если найден, то:
            {
                return magazine; // возвращаем magazine
            }
        }
        catch (ItemNotFoundException ex)
        {
            throw ex; // позволяем обработать исключение ItemNotFoundException глобально
        }
        throw new ItemNotFoundException("Журнал с указанным ISBN не найдена: " + isbn); // выбрасываем исключение, если книга не найдена
    }
}


