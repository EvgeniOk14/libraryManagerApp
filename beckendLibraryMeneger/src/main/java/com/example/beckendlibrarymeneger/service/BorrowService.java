package com.example.beckendlibrarymeneger.service;

import com.example.beckendlibrarymeneger.customExcepptions.BookNotFoundException; // Импорт исключения для случая, когда предмет не найден
import com.example.beckendlibrarymeneger.models.LibraryItem; // Импорт абстрактного предмета
import com.example.beckendlibrarymeneger.models.Transaction; // Импорт класса транзакций
import lombok.AllArgsConstructor; // Импорт аннотации для создания конструктора со всеми параметрами
import org.springframework.beans.factory.annotation.Autowired; // Импорт аннотации для автоматической инъекции зависимостей
import org.springframework.stereotype.Service; // Импорт аннотации для обозначения класса как сервиса
import java.util.Date; // Импорт класса Date для работы с датами
import java.util.List; // импорт List


/**
 * Сервисный класс для управления операциями займа предметов, таких как книги и DVD и т.д..
 * Этот класс предоставляет методы для получения предметов, возврата предметов и обработки штрафов за просрочку.
 */
@AllArgsConstructor // конструктор со всеми параметрами для инициализации полей
@Service // обозначает, что данный класс является сервисом, управляемым Spring
public class BorrowService
{
    //region Fields
    @Autowired // автоматическая инъекция зависимости сервиса книг
    private BookService bookService; // сервис для работы с книгами

    @Autowired
    private DVDService dvdService; // сервис для работы с DVD

    @Autowired
    private MagazineService magazineService; // сервис для работы с MagazineService

    @Autowired
    private EbookService ebookService; // сервис для работы с EbookService

    @Autowired // Автоматическая инъекция зависимости сервиса транзакций
    private TransactionService transactionService; // Сервис для работы с транзакциями
    //endregion

    /**
     * Метод returnItem - для возврата предмета на основе транзакции.
     *
     * @param returnTransaction транзакция, содержащая информацию о возврате предмета
     * @return сообщение о результате возврата предмета
     * @throws BookNotFoundException если предмет не найден
     */
    public String returnItem(Transaction returnTransaction)
    {
        Date due_date = returnTransaction.getDueDate(); // Получаем дату возврата
        Date currentDate = new Date(); // Получаем текущую дату

        // Получаем предмет из транзакции
        LibraryItem<?> item = returnTransaction.getItem(); // Получаем предмет из транзакции
        if (item != null) // Проверяем, что предмет существует
        {
            if (due_date.after(currentDate) || due_date.equals(currentDate)) // Проверяем, не просрочен ли срок возврата
            {
                // Обновляем данные возвратной транзакции
                returnTransaction.setIs_returned(true); // Устанавливаем статус - возвращён (true)
                returnTransaction.setTransactionDate(new Date()); // Устанавливаем текущую дату как дату транзакции
                returnTransaction.setItem(item); // Назначаем возвращаемую вещь
                returnTransaction.setBorrowDate(null); // Очищаем дату займа
                returnTransaction.setDueDate(null); // Очищаем дату возврата
                returnTransaction.setUser(null); // Очищаем пользователя, связанного с транзакцией

                // Сохраняем обновленную транзакцию
                transactionService.saveTransaction(returnTransaction); // Сохраняем транзакцию в сервисе транзакций
                String successMessage = "Предмет успешно возвращён!"; // Сообщение об успешном возврате
                return successMessage; // Возврат успешен
            }
            else // Если срок возврата просрочен, то:
            {
                // Логика расчета штрафа:
                Integer fineForOneDay = 100; // Штраф за один день просрочки
                long daysOverdue = (currentDate.getTime() - due_date.getTime()) / (1000 * 60 * 60 * 24); // Расчёт количества дней просрочки
                double fine = daysOverdue * fineForOneDay; // Расчёт общего штрафа
                String fineMessage = "Срок сдачи просрочен на " + daysOverdue + " дней. Штраф: " + fine + " рублей."; // Сообщение о штрафе
                System.out.println("Ваш штраф за " + daysOverdue + " дня просрочки составляет " + fine + " рублей"); // Выводим информацию о штрафе
                return fineMessage; // Возвращаем сообщение о штрафе
            }
        }
        else // Если предмет не найден, то:
        {
            throw new BookNotFoundException("Предмет не найден! "); // Выбрасываем исключение о ненахождении предмета
        }
    }


    /**
     * Метод getItem - для получения предмета (книги или DVD и т.д.) на основе типа и JSON-объекта.
     *
     * @param isbn, содержащий данные о предмете (его номер)
     * @param type тип предмета (например, "Book" или "DVD")
     * @return объект AbstractItem, полученный на основе переданного типа
     * @throws IllegalArgumentException если передан неверный тип предмета
     */
    public LibraryItem<?> getItem(String isbn, String type, Transaction updatedTransaction)
    {
        LibraryItem<?> item; // инициализируем переменную item (Предмет)

        switch (type) // если тип предмета равен:
        {
            case "Book": // если тип предмета - книга
            {
                item = bookService.getBookByISBS(isbn); // получаем книгу по ISBN
                List<Transaction> transactions = item.getTransactions(); // получаем список транзакций у предмета
                transactions.add(updatedTransaction); // добавляем в список транзакций обновлённую транзакцию
                item.setTransactions((transactions)); // устанавливаем предмету список транзакций

                System.out.println("Это Book: " + item); // выводим для проверки в консоль
                return item; // возвращаем найденный предмет книга
            }
            case "DVD": // Если тип - DVD
            {
                System.out.println("зашли в блок case DVD:");
                item = dvdService.getDVDByISBN(isbn); // получаем фильм по ISBN
                List<Transaction> transactions = item.getTransactions(); // получаем список транзакций у предмета
                transactions.add(updatedTransaction); // добавляем в список транзакций обновлённую транзакцию
                item.setTransactions((transactions)); // устанавливаем предмету список транзакций

                System.out.println("Это DVD: "); // Выводим сообщение о том, что это DVD
                return item; // возвращаем найденный предмет фильм
            }
            case "Magazine":
            {
                item = magazineService.getMagazineByISBS(isbn); // получаем журнал по ISBN
                List<Transaction> transactions = item.getTransactions(); // получаем список транзакций у предмета
                transactions.add(updatedTransaction); // добавляем в список транзакций обновлённую транзакцию
                item.setTransactions((transactions)); // устанавливаем предмету список транзакций

                System.out.println("Это Magazine: "); // выводим сообщение о том, что это DVD
                return item; // возвращаем найденный предмет журнал
            }
            case "Ebook":
            {
                item = ebookService.getEbookByISBS(isbn); // получаем электронную книгу по ISBN
                List<Transaction> transactions = item.getTransactions(); // получаем список транзакций у предмета
                transactions.add(updatedTransaction); // добавляем в список транзакций обновлённую транзакцию
                item.setTransactions((transactions)); // устанавливаем предмету список транзакций

                System.out.println("Это Ebook: "); // выводим сообщение о том, что это DVD
               return item; // возвращаем найденный предмет электронная книга
            }
            default: // если тип не распознан, то:
            {
                throw new IllegalArgumentException("неверно отправлен параметр " + type); // Выбрасываем исключение о неверном типе
            }
        }
    }
}





