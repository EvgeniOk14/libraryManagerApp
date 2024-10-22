package com.example.beckendlibrarymeneger.customExcepptions;

/**
 * Исключение, которое возникает при неудачной попытке сохранить экземпляр
 * журнала в базе данных.
 *
 * Данное исключение наследует {@link RuntimeException} и используется для
 * обозначения ошибок, связанных с сохранением информации о журнале.
 */
public class MagazineNotSaveExeption extends RuntimeException
{
    //region Constructor
    /**
     * Конструктор исключения, принимающий сообщение об ошибке.
     *
     * @param message сообщение, описывающее причину исключения.
     */
    public MagazineNotSaveExeption(String message)
    {
        super(message); // Передаёт сообщение родительскому классу RuntimeException
        System.out.println("сработал MagazineNotSaveException: " + message); // Логирует сообщение на консоль
    }
    //endRegion
}
