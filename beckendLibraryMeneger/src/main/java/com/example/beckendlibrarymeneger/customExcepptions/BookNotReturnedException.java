package com.example.beckendlibrarymeneger.customExcepptions;

/**
 * Исключение, выбрасываемое, когда книга не возвращена в систему.
 * Это исключение является подклассом {@link RuntimeException},
 * что означает, что оно может быть выброшено во время выполнения программы.
 */
public class BookNotReturnedException extends RuntimeException
{
    //region Constructor
    /**
     * Конструктор исключения, принимающий сообщение об ошибке.
     *
     * @param message сообщение, описывающее причину исключения.
     */
    public BookNotReturnedException(String message)
    {
        super(message); // Передаёт сообщение родительскому классу RuntimeException
        System.out.println("сработало исключение BookNotReturnedException " + message); // Логирует сообщение на консоль
    }
    //endRegion
}
