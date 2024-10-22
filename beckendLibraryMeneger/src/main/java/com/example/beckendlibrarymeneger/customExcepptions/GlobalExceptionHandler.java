package com.example.beckendlibrarymeneger.customExcepptions;

import com.google.gson.JsonSyntaxException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Класс GlobalExceptionHandler - глобальный обработчик исключений.
 * Позволяет перехватывать и обрабатывать исключения, возникающие в контроллерах приложения.
 */
@ControllerAdvice // Аннотация указывает, что класс является обработчиком исключений
public class GlobalExceptionHandler
{
    /**
     * Обрабатывает исключение ItemNotFoundException.
     *
     * @param ex исключение, которое содержит информацию об ошибке
     * @return ResponseEntity с сообщением об ошибке и статусом NOT_FOUND
     */
    @ExceptionHandler(ItemNotFoundException.class) // Аннотация указывает, что этот метод обрабатывает BookNotFoundException
    public ResponseEntity<String> handleBookNotFoundException(ItemNotFoundException ex)
    {
        System.out.println("Перехвачено исключение по обработке ItemNotFoundException: " + ex.getMessage()); // Логирует сообщение об ошибке
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Предмет не найден: " + ex.getMessage()); // Возвращает статус NOT_FOUND с сообщением
    }

    /**
     * Обрабатывает исключение UserNotFoundException.
     *
     * @param ex исключение, которое содержит информацию об ошибке
     * @return ResponseEntity с сообщением о том, что пользователь не найден и статусом NOT_FOUND
     */
    @ExceptionHandler(UserNotFoundException.class) // Аннотация указывает, что этот метод обрабатывает UserNotFoundException
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex)
    {
        System.out.println("Перехвачено исключение по обработке UserNotFoundException: " + ex.getMessage()); // Логирует сообщение об ошибке
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден: " + ex.getMessage()); // Возвращает статус NOT_FOUND с сообщением
    }

    /**
     * Обрабатывает исключение TransactionNotFoundException.
     *
     * @param ex исключение, которое содержит информацию об ошибке
     * @return ResponseEntity с сообщением о том, что транзакция не найдена и статусом NOT_FOUND
     */
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<String> handlerTransactionNotFoundException(TransactionNotFoundException ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Транзакция не найдена: " + ex.getMessage());
    }

    /**
     * Обрабатывает исключение IllegalArgumentException.
     *
     * @param ex исключение, которое содержит информацию об ошибке
     * @return ResponseEntity с сообщением об ошибке и статусом BAD_REQUEST
     */
    @ExceptionHandler(IllegalArgumentException.class) // Аннотация указывает, что этот метод обрабатывает IllegalArgumentException
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex)
    {
        System.out.println("Перехвачено исключение по обработке IllegalArgumentException: " + ex.getMessage()); // Логирует сообщение об ошибке
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Не верный аргумент: " + ex.getMessage()); // Возвращает статус BAD_REQUEST с сообщением
    }

    /**
     * Обрабатывает исключение JsonSyntaxException.
     *
     * @param ex исключение, которое содержит информацию об ошибке
     * @return ResponseEntity с сообщением о синтаксической ошибке JSON и статусом BAD_REQUEST
     */
    @ExceptionHandler(JsonSyntaxException.class) // Аннотация указывает, что этот метод обрабатывает JsonSyntaxException
    public ResponseEntity<String> handlerNotProcessingGson(JsonSyntaxException ex)
    {
        System.out.println("Перехвачено исключение по обработке Gson: " + ex.getMessage()); // Логирует сообщение об ошибке
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка синтаксиса JSON: " + ex.getMessage()); // Возвращает статус BAD_REQUEST с сообщением
    }

    /**
     * Обрабатывает исключение ItemNotReturnedException.
     *
     * @param ex исключение, которое содержит информацию об ошибке
     * @return ResponseEntity с сообщением о том, что возврат вещи не осуществлён и статусом OK
     */
    @ExceptionHandler(ItemNotReturnedException.class) // Аннотация указывает, что этот метод обрабатывает BookNotReturnedException
    public ResponseEntity<String> handlerBookNotReturnedException(ItemNotReturnedException ex)
    {
        System.out.println("Перехвачено исключение по обработке ItemNotReturnedException: " + ex.getMessage()); // Логирует сообщение об ошибке
        return ResponseEntity.status(HttpStatus.OK).body("Возврат вещи не осуществлён: " + ex.getMessage()); // Возвращает статус OK с сообщением
    }


    /**
     * Обрабатывает исключение ItemNotSaveExeption.
     *
     * @param ex исключение, которое содержит информацию об ошибке
     * @return ResponseEntity с сообщением о том, что сохранение вещи не осуществлёно и статусом OK
     */
    @ExceptionHandler(ItemNotSaveExeption.class) // Аннотация указывает, что этот метод обрабатывает ItemNotSaveExeption
    public ResponseEntity<String> handlerItemNotSaveExeption(ItemNotSaveExeption ex)
    {
        // Логирует сообщение об ошибке
        System.out.println("Перехвачено исключение по обработке ItemNotSaveExeption: " + ex.getMessage());

        // Возвращает статус OK с сообщением о том, что сохранение в базу данных вещи не осуществлёно
        return ResponseEntity.status(HttpStatus.OK).body("Сохранение в базу данных вещи Item не осуществлёно: " + ex.getMessage());
    }


    /**
     * Обрабатывает исключение BookNotSaveExeption.
     *
     * @param ex исключение, которое содержит информацию об ошибке
     * @return ResponseEntity с сообщением о том, что сохранение книги не осуществлёно и статусом OK
     */
    @ExceptionHandler(BookNotSaveExeption.class) // Аннотация указывает, что этот метод обрабатывает BookNotSaveExeption
    public ResponseEntity<String> handlerBookNotSaveExeption(BookNotSaveExeption ex)
    {
        // Логирует сообщение об ошибке
        System.out.println("Перехвачено исключение по обработке BookNotSaveExeption: " + ex.getMessage());

        // Возвращает статус OK с сообщением о том, что сохранение в базу данных книги не осуществлёно
        return ResponseEntity.status(HttpStatus.OK).body("Сохранение в базу данных книги не осуществлёно: " + ex.getMessage());
    }

    /**
     * Обрабатывает исключение DVDNotSaveExeption.
     *
     * @param ex исключение, которое содержит информацию об ошибке
     * @return ResponseEntity с сообщением о том, что сохранение DVD не осуществлёно и статусом OK
     */
    @ExceptionHandler(DVDNotSaveExeption.class) // Аннотация указывает, что этот метод обрабатывает DVDNotSaveExeption
    public ResponseEntity<String> handlerDVDNotSaveExeption(DVDNotSaveExeption ex)
    {
        // Логирует сообщение об ошибке
        System.out.println("Перехвачено исключение по обработке DVDNotSaveExeption: " + ex.getMessage());

        // Возвращает статус OK с сообщением о том, что сохранение в базу данных DVD не осуществлёно
        return ResponseEntity.status(HttpStatus.OK).body("Сохранение в базу данных DVD не осуществлёно: " + ex.getMessage());
    }


    /**
     * Обрабатывает исключение MagazineNotSaveExeption.
     *
     * @param ex исключение, которое содержит информацию об ошибке
     * @return ResponseEntity с сообщением о том, что сохранение журнала не осуществлёно и статусом OK
     */
    @ExceptionHandler(MagazineNotSaveExeption.class) // Аннотация указывает, что этот метод обрабатывает MagazineNotSaveExeption
    public ResponseEntity<String> handlerMagazineotSaveExeption(MagazineNotSaveExeption ex)
    {
        // Логирует сообщение об ошибке
        System.out.println("Перехвачено исключение по обработке MagazineNotSaveExeption: " + ex.getMessage());

        // Возвращает статус OK с сообщением о том, что сохранение в базу данных журнала не осуществлёно
        return ResponseEntity.status(HttpStatus.OK).body("Сохранение в базу данных Magazine не осуществлёно: " + ex.getMessage());
    }


    /**
     * Обрабатывает исключение EbookNotSaveExeption.
     *
     * @param ex исключение, которое содержит информацию об ошибке
     * @return ResponseEntity с сообщением о том, что сохранение электронной книги не осуществлёно и статусом OK
     */
    @ExceptionHandler(EbookNotSaveExeption.class) // Аннотация указывает, что этот метод обрабатывает EbookNotSaveExeption
    public ResponseEntity<String> handlerEbookNotSaveExeption(EbookNotSaveExeption ex)
    {
        // Логирует сообщение об ошибке
        System.out.println("Перехвачено исключение по обработке EbookNotSaveExeption: " + ex.getMessage());

        // Возвращает статус OK с сообщением о том, что сохранение в базу данных электронной книги не осуществлёно
        return ResponseEntity.status(HttpStatus.OK).body("Сохранение в базу данных Ebook не осуществлёно: " + ex.getMessage());
    }


    /**
     * Обрабатывает исключение TransactionNotSaveExeption.
     *
     * @param ex исключение, которое содержит информацию об ошибке
     * @return ResponseEntity с сообщением о том, что сохранение транзакции не осуществлёно и статусом OK
     */
    @ExceptionHandler(TransactionNotSaveExeption.class) // Аннотация указывает, что этот метод обрабатывает TransactionNotSaveExeption
    public ResponseEntity<String> handlerTransactionNotSaveExeption(TransactionNotSaveExeption ex)
    {
        // Логирует сообщение об ошибке
        System.out.println("Перехвачено исключение по обработке TransactionNotSaveExeption: " + ex.getMessage());

        // Возвращает статус OK с сообщением о том, что сохранение в базу данных транзакции не осуществлёно
        return ResponseEntity.status(HttpStatus.OK).body("Сохранение в базу данных транзакции Transaction не осуществлёно: " + ex.getMessage());
    }

}
