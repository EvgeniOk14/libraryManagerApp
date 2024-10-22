package com.example.beckendlibrarymeneger.customExcepptions;

/**
 * Исключение, возникающее при попытке доступа к пользователю, который не найден.
 */
public class UserNotFoundException extends RuntimeException
{
     //region Constructor
    /**
     * Конструктор для создания исключения UserNotFoundException.
     *
     * @param message сообщение, которое будет передано в качестве причины исключения
     */
    public UserNotFoundException(String message)
    {
        super(message); // Передаёт сообщение родительскому классу RuntimeException
        System.out.println("сработал UserNotFoundException: " + message); // Логирует сообщение об исключении
    }
    //endRegion
}

