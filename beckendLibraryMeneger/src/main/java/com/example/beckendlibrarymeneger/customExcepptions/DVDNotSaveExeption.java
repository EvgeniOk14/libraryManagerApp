package com.example.beckendlibrarymeneger.customExcepptions;

/**
 * Исключение, которое возникает, когда DVD не может быть сохранён в базе данных.
 *
 * Это исключение наследует {@link RuntimeException} и используется для
 * обозначения ошибок, связанных с процессом сохранения данных о
 * DVD в хранилище данных.
 */
public class DVDNotSaveExeption extends RuntimeException
{
    //region Constructor
    /**
     * Конструктор исключения, принимающий сообщение об ошибке.
     *
     * @param message сообщение, описывающее причину исключения.
     */
    public DVDNotSaveExeption(String message)
    {
        super(message); // Передаёт сообщение родительскому классу RuntimeException
        System.out.println("сработал DVDNotSaveException: " + message); // Логирует сообщение на консоль
    }
    //endRegion
}
