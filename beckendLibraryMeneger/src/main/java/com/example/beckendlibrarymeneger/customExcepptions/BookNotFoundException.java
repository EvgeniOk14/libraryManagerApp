package com.example.beckendlibrarymeneger.customExcepptions;

/**
 * Исключение, выбрасываемое при отсутствии книги в системе.
 * Это исключение является подклассом {@link RuntimeException},
 * что означает, что оно может быть выброшено во время выполнения программы.
 */
public class BookNotFoundException extends RuntimeException
{
    //region Constructor
    /**
     * Конструктор исключения, принимающий сообщение об ошибке.
     *
     * @param message сообщение, описывающее причину исключения.
     */
    public BookNotFoundException(String message)
    {
        super(message); // Передаёт сообщение родительскому классу RuntimeException
        System.out.println("сработал BookNotFoundException: " + message); // Логирует сообщение на консоль
    }
    //endRegion
}
