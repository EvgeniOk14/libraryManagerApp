package com.example.beckendlibrarymeneger.customExcepptions;

/**
 * Исключение, которое возникает, когда электронная книга не может быть сохранена в базе данных.
 *
 * Данное исключение наследует {@link RuntimeException} и используется для
 * обозначения ошибок, связанных с процессом сохранения данных о
 * электронной книге в хранилище данных.
 */
public class EbookNotSaveExeption extends RuntimeException
{
    //region Constructor
    /**
     * Конструктор исключения, принимающий сообщение об ошибке.
     *
     * @param message сообщение, описывающее причину исключения.
     */
    public EbookNotSaveExeption(String message)
    {
        super(message); // Передаёт сообщение родительскому классу RuntimeException
        System.out.println("сработал EbookNotSaveExeption: " + message); // Логирует сообщение на консоль
    }
    //endRegion
}
