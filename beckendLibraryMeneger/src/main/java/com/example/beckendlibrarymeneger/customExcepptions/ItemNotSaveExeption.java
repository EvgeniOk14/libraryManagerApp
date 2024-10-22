package com.example.beckendlibrarymeneger.customExcepptions;

/**
 * Исключение, которое возникает при неудачной попытке сохранить элемент
 * в базе данных.
 *
 * Данное исключение наследует {@link RuntimeException} и используется для
 * обозначения ошибок, связанных с сохранением данных о любом элементе
 * библиотеки.
 */
public class ItemNotSaveExeption  extends RuntimeException
{
    //region Constructor
    /**
     * Конструктор исключения, принимающий сообщение об ошибке.
     *
     * @param message сообщение, описывающее причину исключения.
     */
    public ItemNotSaveExeption(String message)
    {
        super(message); // Передаёт сообщение родительскому классу ItemNotSaveExeption
        System.out.println("сработал ItemNotSaveExeption: " + message); // Логирует сообщение на консоль
    }
    //endRegion
}
