package com.example.beckendlibrarymeneger.customExcepptions;

/**
 * Исключение, которое возникает при ошибках сохранения транзакции.
 *
 * Данное исключение наследует {@link RuntimeException} и используется для
 * обозначения ситуаций, когда транзакция не может быть сохранена в базу данных.
 */
public class TransactionNotSaveExeption  extends RuntimeException
{
    //region Constructor
    /**
     * Конструктор исключения, принимающий сообщение об ошибке.
     *
     * @param message сообщение, описывающее причину исключения.
     */
    public TransactionNotSaveExeption(String message)
    {
        super(message); // Передаёт сообщение родительскому классу TransactionNotSaveExeption
        System.out.println("сработал TransactionNotSaveExeption: " + message); // Логирует сообщение на консоль
    }
    //endRegion
}
