package com.example.beckendlibrarymeneger.customExcepptions;

/**
 * Исключение, которое возникает, когда элемент не найден в базе данных.
 *
 * Данное исключение наследует {@link RuntimeException} и используется для
 * обозначения ошибок, связанных с отсутствием элемента при попытке
 * его извлечения или использования в приложении.
 */
public class ItemNotFoundException extends RuntimeException
{
    //region Constructor
    /**
     * Конструктор исключения, принимающий сообщение об ошибке.
     *
     * @param message сообщение, описывающее причину исключения.
     */
    public ItemNotFoundException(String message)
    {
        super(message); // передаёт сообщение родительскому классу RuntimeException
        System.out.println("сработал ItemNotFoundException: " + message); // логирует сообщение на консоль
    }
    //endRegion
}
