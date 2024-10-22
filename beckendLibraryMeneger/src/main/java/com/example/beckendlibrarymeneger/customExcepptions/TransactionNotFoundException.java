package com.example.beckendlibrarymeneger.customExcepptions;

/**
 * Исключение, которое возникает при попытке найти транзакцию, которая не существует.
 *
 * Данное исключение наследует {@link RuntimeException} и используется для
 * обозначения ситуаций, когда запрашиваемая транзакция не может быть найдена
 * в базе данных.
 */
public class TransactionNotFoundException extends RuntimeException
{
    //region Constructor
    /**
     * Конструктор исключения, принимающий сообщение об ошибке.
     *
     * @param message сообщение, описывающее причину исключения.
     */
    public TransactionNotFoundException(String message)
    {
        super(message); // Передаёт сообщение родительскому классу TransactionNotFoundExeption
    }
    //endRegion
}
