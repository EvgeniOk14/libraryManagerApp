package com.example.beckendlibrarymeneger.customExcepptions;

/**
 * Исключение, возникающее при попытке сохранения пользователя в базу данных
 */
public class UserNotSaveException extends RuntimeException
{
    //region Constructor
    /**
     * Конструктор для создания исключения UserNotSaveException.
     *
     * @param message сообщение, которое будет передано в качестве причины исключения
     */
    public UserNotSaveException(String message)
    {
        super(message); // Передаёт сообщение родительскому классу RuntimeException
        System.out.println("сработал UserNotSaveException: " + message); // Логирует сообщение об исключении
    }
    //endRegion
}


