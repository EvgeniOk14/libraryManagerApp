package com.example.beckendlibrarymeneger.customExcepptions;

/**
 * Исключение, которое возникает, когда книга не может быть сохранена в базе данных.
 *
 * Это исключение наследует {@link RuntimeException} и используется для
 * обозначения ошибок, связанных с процессом сохранения данных о
 * книге в хранилище данных.
 */
public class BookNotSaveExeption extends RuntimeException
{
    //region Constructor
    /**
     * Конструктор исключения, принимающий сообщение об ошибке.
     *
     * @param message сообщение, описывающее причину исключения.
     */
    public BookNotSaveExeption(String message)
    {
        super(message); // Передаёт сообщение родительскому классу RuntimeException
        System.out.println("сработал BookNotSaveException: " + message); // Логирует сообщение на консоль
    }
    //endRegion
}
